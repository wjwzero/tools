package com.tools.websocket.netty.configration;

import java.util.List;
import java.util.Map;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/4/9 18:12
 */
public interface ChannelCacheStore {

    /**
     *
     * @param parameters
     * @return
     */
    String channelCacheTagKey(Map<String, List<String>> parameters);
}
