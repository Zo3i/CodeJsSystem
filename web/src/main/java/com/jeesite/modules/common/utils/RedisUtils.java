package com.jeesite.modules.common.utils;


import redis.clients.jedis.Jedis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.jeesite.common.cache.JedisUtils;
import com.jeesite.modules.common.utils.oss.STSToken;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @author linwei
 * @data 2018年2月23日
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

	/**
     * 阿里云STStoken缓存
     */
    public void setSTSToken(STSToken stsToken) {
        JedisUtils.set("stsToken", JSON.toJSONString(stsToken), TIMEOUT_2_HOUR);
    }

    public STSToken getSTSToken() {
        String json = JedisUtils.get("stsToken");
        return StringUtils.isNotBlank(json) ? JSON.parseObject(json, STSToken.class) : null;
    }


}
