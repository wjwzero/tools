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
 * 2019/1/29    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.jms.producer;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/29 11:23
 */
public interface ActiveMqProducerService {

    /**
     * 开启生产者服务
     * @param threadNum
     * @param loopNum
     * @param msg
     * @param destinationName
     * @param threadSleepTime
     * @return void
     * @author  wangjianwei
     * @date 2019/1/29 17:05
     */
    void run(int threadNum, Integer loopNum, String msg, String destinationName, long threadSleepTime);

    /**
     * 关闭生产者服务
     * @return void
     * @author  wangjianwei
     * @date 2019/1/29 17:06
     */
    void stop();

}
