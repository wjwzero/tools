/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
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
 * 2018年9月4日    yaojianping         Create the class
 * http://www.jimilab.com/
 */

package com.tools.base.util.http;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018年9月4日 上午10:57:21
 */
public class HttpUtil {

    /**
     * 设置session
     */
    public static void setSessionAttribute(String key, Object value, HttpServletRequest request) {
        request.getSession().setAttribute(key, value);
    }

    /**
     * 获取session
     */
    public static Object getSessionAttribute(String key, HttpServletRequest request) {
        return request.getSession().getAttribute(key);
    }

    /**
     * 删除session
     */
    public static void removeSessionAttribute(String key, HttpServletRequest request) {
        request.getSession().removeAttribute(key);
    }

    /**
     * @param request
     * @return
     * @Title: getIpAddr
     * 获取请求远端的IP地址
     * @author yaojianping
     * @date 2018年4月17日 下午2:01:39
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            String[] strIps = StringUtils.stripToEmpty(ip).split(",");
            if (strIps != null && strIps.length > 1) {
                return StringUtils.stripToEmpty(strIps[1]);
            }
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;

    }

    /**
     * 判断浏览器类型
     *
     * @param request 1-IE 2-火狐 3-Safari 4-其他
     * @return java.lang.Integer
     * @author yaojianping
     * @date 2018/10/19 15:51
     */
    public static Integer getBrowingType(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return 1;
        } else if (null != agent && -1 != agent.indexOf("Firefox")) {
            return 2;
        } else if (null != agent && -1 != agent.indexOf("Safari")) {
            return 3;
        } else {
            return 4;
        }
    }

    /**
     * @param request
     * @return
     * @Title: getDomain
     * 获取来源头信息，获取域名
     * @author yaojianping
     * @date 2018年4月17日 下午2:06:09
     */
    public static String getDomain(HttpServletRequest request) {
        String domain = null;
        // request.getRequestURL().toString();
        // 获取头信息（原请求的URL)
        String domainreferer = request.getHeader("referer");
        if (domainreferer != null) {
            String replaceReferer = domainreferer.replace("http://", "");
            if (domainreferer != null && replaceReferer.indexOf("/") > 0) {
                // 获取原URL域名
                domain = replaceReferer.substring(0, replaceReferer.indexOf("/"));
            } else {
                domain = replaceReferer.substring(0, replaceReferer.length());
            }
        }
        return domain;
    }

    /**
     * 获取服务器路径
     *
     * @param request
     * @return
     */
    public static String getServiceRoot(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return url.substring(0, url.lastIndexOf('/'));
    }

    /**
     * 封装转码方法
     *
     * @throws UnsupportedEncodingException
     */
    public static String getEncoding(String encondingName) throws UnsupportedEncodingException {
        return URLEncoder.encode(URLEncoder.encode(encondingName, "UTF-8"), "UTF-8");
    }

    /**
     * 获取完整的URL
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getUrl(HttpServletRequest request) {
        StringBuilder urlBuilder = new StringBuilder();
        try {
            urlBuilder.append(getRootPath(request, false));
            // 格式化中文字符，防止出现乱码
            if (StringUtils.isNotBlank(request.getQueryString())) {
                String query = "?" + new String(request.getQueryString().trim().getBytes("ISO-8859-1"));
                urlBuilder.append(query);
            }
            return URLEncoder.encode(urlBuilder.toString(), "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获得根路径
     *
     * @param request
     * @param includePort
     * @return
     */
    public static String getRootPath(HttpServletRequest request, boolean includePort) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(request.getScheme());
        urlBuilder.append("://");
        urlBuilder.append(request.getServerName());
        if (includePort) {
            urlBuilder.append(":");
            urlBuilder.append(request.getServerPort());
        }
        String context = request.getContextPath();
        if (StringUtils.isBlank(context)) {
            context = getOriginalUri(request);
        }
        urlBuilder.append(context);
        return urlBuilder.toString();
    }

    /**
     * 利用urlrewrite.jar加入的属性获得原始URI
     *
     * @param request
     * @return
     */
    public static String getOriginalUri(HttpServletRequest request) {
        return request.getAttribute("javax.servlet.forward.request_uri").toString();
    }

    /**
     * 组装默认头部信息
     *
     * @param headers 可以传null
     * @return java.util.Map<java.lang.String   ,   java.lang.String>
     * @author yaojianping
     * @date 2018/10/29 11:12
     */
    public static Map<String, String> getDefaultHeader(Map<String, String> headers) {
        if (null == headers) {
            headers = new HashMap<>(2);
        }
        headers.put("Connection", "close");
        headers.put("http-group", "com.jimi.tuqiang");
        return headers;
    }

    /**
     * 组装请求参数
     *
     * @param params
     * @return
     */
    public static String assemblyParams(Map<String, String> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder query = new StringBuilder();
        for (String param : params.keySet()) {
            query.append("&");
            query.append(param);
            query.append("=");
            query.append(params.get(param));
        }
        return "?" + query.toString().substring(1);
    }
}
