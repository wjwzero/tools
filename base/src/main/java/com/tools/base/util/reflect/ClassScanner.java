/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2019/1/13    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2019/1/13 15:20
 */
public final class ClassScanner {
    private static Logger LOGGER = LoggerFactory.getLogger(ClassScanner.class);
    private String defaultClassPath = ClassScanner.class.getResource("/").getPath();
    private ClassLoader classLoader;
    private String rootPackage;
    private File rootPath;
    private static final String CLASS_SUBFIX = ".class";
    private static final String JAR_SUBFIX = ".jar";
    private Set<Class<?>> classes = new HashSet<>(0);

    public ClassScanner(Package pack) {
        this(pack.getName(), null);
    }

    public ClassScanner(Class<?> clazz) {
        this(clazz.getPackage().getName(), clazz.getClassLoader());
    }

    public ClassScanner(String packageName) {
        this(packageName, null);
    }

    private ClassScanner(String rootPackage, ClassLoader classLoader) {
        this.rootPackage = rootPackage;
        if (classLoader == null) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        this.classLoader = classLoader;
        URL rootURL = classLoader.getResource(rootPackage.replace(".", "/"));

        if (rootURL == null) {
            return;
        }
        try {
            URI rootURI = rootURL.toURI();
            LOGGER.info(defaultClassPath);
            LOGGER.info(rootURL.toURI().toString());
            if ("file".equals(rootURI.getScheme())) {
                final ClassLoader classLoader1 = classLoader;
                Files.walk(Paths.get(rootURL.toURI()), FileVisitOption.FOLLOW_LINKS).forEach(path -> {
                    LOGGER.info(path.toString());
                    if (isDirectory(path)) {
                        return;
                    }

                    if (isClassFile(path)) {
                        String className = path.toAbsolutePath().toString()
                                .replace(path.getFileSystem().getSeparator(), ".");
                        className = className.substring(className.indexOf(rootPackage));
                        className = deleteExt(className);
                        Class<?> clazz = loadClass(className);
                        if (clazz != null) {
                            classes.add(clazz);
                        }
                    } else if (isJarFile(path)) {
                        try {
                            scanJar(path.toFile(), classLoader1);
                        } catch (IOException e) {
                        }
                    }
                });
            } else if ("jar".equals(rootURI.getScheme())) {
                String filepath = rootURI.toString();
//                scanJar(rootURI, classLoader);
            }

        } catch (URISyntaxException | IOException e) {
        }
    }

    public Stream<Class<?>> stream() {
        return classes.stream();
    }

    private boolean isClassFile(Path path) {
        return path.getFileName().toString().endsWith(CLASS_SUBFIX);
    }

    private boolean isClassFile(String path) {
        return path.endsWith(CLASS_SUBFIX);
    }

    private boolean isJarFile(Path path) {
        return path.getFileName().toString().endsWith(JAR_SUBFIX);
    }

    private boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    private void scanJar(File file, ClassLoader classloader) throws IOException {
        JarFile jarFile;
        try {
            jarFile = new JarFile(file);
        } catch (IOException e) {
            // Not a jar file
            return;
        }
        try {
            //得到该jar文件下面的类实体
            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
            while (jarEntryEnumeration.hasMoreElements()) {
                JarEntry entry = jarEntryEnumeration.nextElement();
                String jarEntryName = entry.getName();
                //这里我们需要过滤不是class文件和不在basePack包名下的类
                if (isClassFile(jarEntryName)) {
                    String className =
                            jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace(File.separator, ".");
//                        Class cls = Class.forName(className);
//                        className = className.substring(className.indexOf(rootPackage));
//                        className = deleteExt(className);
                    Class<?> clazz = loadClass(className);
                    if (clazz != null) {
                        classes.add(clazz);
                    }
                }
            }
        } finally {
            try {
                jarFile.close();
            } catch (IOException ignored) {
            }
        }
    }

    private Class<?> loadClass(String className) {
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String deleteExt(String name) {
        return name.substring(0, name.length() - CLASS_SUBFIX.length());
    }
}
