package com.jeesite.modules.common.utils;


import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.entity.other.LoginRsp;
import redis.clients.jedis.Jedis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.jeesite.common.cache.JedisUtils;

/**
 * @version 1.0
 * @author jo
 * @data 2018年11月23日
 * @描述:
 */
@Component
public class RedisUtils {

	public static final String SMS_CODE = "comm:smsCode:";
	public static final String SESSION = "token:session:";
	public static final int TIMEOUT_5_MIN = 60 * 5;
	public static final int TIMEOUT_2_HOUR = 2 * 60 * 60;
	public static final int TIMEOUT_1_WEEK = 60 * 60 * 24 * 7;

	public void setSmsCode(String mobile, String smsCode) {
	    JedisUtils.set(SMS_CODE + mobile, smsCode, TIMEOUT_5_MIN);
	}

	public boolean checkSmsCode(String mobile, String smsCode) {
		String string = JedisUtils.get(SMS_CODE + mobile);
		if (StringUtils.isBlank(string) || !string.equals(smsCode)) {
			return false;
		} else {
		    JedisUtils.del(SMS_CODE + mobile);
			return true;
		}
	}

	public void setSession(String sessionId, LoginRsp user) {
		JedisUtils.set(SESSION + sessionId, JSON.toJSONString(user), TIMEOUT_1_WEEK);
	}

    public LoginRsp getSession(String sessionId) {
        String key = SESSION + sessionId;
        String json = JedisUtils.get(key);
        if (StringUtils.isNotBlank(json)) {
            Jedis resource = JedisUtils.getResource();
            resource.expire(key, TIMEOUT_1_WEEK);
            JedisUtils.returnResource(resource);
        }
        return JSON.parseObject(json, LoginRsp.class);
    }


}
