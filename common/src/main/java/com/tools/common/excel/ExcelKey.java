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
 * 2018年12月24日    Administrator         Create the class
 * http://www.jimilab.com/
 */

package com.tools.common.excel;

/**
 * @author zhengjunwei
 * @version 1.0
 * @date 2018年12月24日 下午5:27:43
 */
public final class ExcelKey {
    /**
     * excel文件保存路径默认前缀
     */
    public static final String TOGETHER = System.getProperty("user.dir").replaceAll("\\\\", "/");

    private ExcelKey() {
        throw new UnsupportedOperationException();
    }

    /**
     * 设备模板保存目录
     */
    public static class Tempalte {
        public static final String PREFIX = TOGETHER + "/template/";

        private Tempalte() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * 设备模板基本字段
     */
    public static class BaseTempalteHeader {
        public static final String PREFIX = "序号,设备名称,IMEI,机型分类,设备图标,分组名称,SIM卡号";
        public static final String FIELD_NAME = "seqNum,deviceName,imei,mctype,icon,groupName,sim";

        private BaseTempalteHeader() {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * 设备导入结果保存目录
     */
    public static class ImportError {
        public static final String PREFIX = TOGETHER + "/import/";

        private ImportError() {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * 报表导出结果保存目录
     */
    public static class ExportReport {
        public static final String PREFIX = TOGETHER + "/export/";

        private ExportReport() {
            throw new UnsupportedOperationException();
        }
    }

}
