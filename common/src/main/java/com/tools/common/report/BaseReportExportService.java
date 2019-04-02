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
 * 2019年1月9日    Administrator         Create the class
 * http://www.jimilab.com/
*/

package com.tools.common.report;


import com.tools.common.excel.model.ExportModel;

/**
 * 报表导出基础接口，每个要导出报表的类都必须实现这个类
 *
 * @author zhengjuwnei
 * @version 1.0
 * @date 2019年1月9日 上午10:02:19
 */
public interface BaseReportExportService {

    /**
     * 获取报表导出的报表信息
     * 
     * @param reportQuery
     *            其中的targetClass和datas是必须的
     * @param page
     * @param size
     * @return
     * @author zhengjunwei
     * @date 2019年1月11日 下午2:05:29
     */
    public ExportModel<?> listExportData(ReportExportQuery reportQuery, Integer page, Integer size);

}
