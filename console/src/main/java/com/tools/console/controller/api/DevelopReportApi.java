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
 * 2019/3/20    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.console.controller.api;

import io.swagger.annotations.Api;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日志报表接口
 * @author wangjianwei
 * @version 1.0
 * @date 2019/3/20 16:50
 */
@RequestMapping("/activemq")
@Validated
@Api(tags = "DevelopReport")
public interface DevelopReportApi {


}
