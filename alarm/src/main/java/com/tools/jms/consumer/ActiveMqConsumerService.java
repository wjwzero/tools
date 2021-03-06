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
 * 2019/1/30    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.jms.consumer;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/30 10:44
 */
public interface ActiveMqConsumerService {

    /**
     * 停止消费
     * @param queue
     * @return void
     * @author  wangjianwei
     * @date 2019/1/30 16:02
     */
    void shutDownConsumer(String queue);
}
