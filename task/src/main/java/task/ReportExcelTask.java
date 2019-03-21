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
 * 2019年3月15日    WangJianWei         Create the class
 * http://www.jimilab.com/
*/

package task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @FileName ReportExcelTask.java
 * @Description: 
 *
 * @Date 2019年3月15日 上午11:27:49
 * @author WangJianWei
 * @version 1.0
 */
public class ReportExcelTask {
	
	private static BlockingQueue<String> reportExcelQueue = new LinkedBlockingQueue<String>();

	/**
	 * @Title: offerReportExcelTask 
	 * @Description: 
	 * @param task
	 * @return 
	 * @author WangJianWei
	 * @date 2019年3月18日 上午11:52:30
	 */
	public static boolean offerReportExcelTask(String task){
		return reportExcelQueue.offer(task);
	}
}
