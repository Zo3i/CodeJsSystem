package com.jeesite.modules.common.utils.limit;

/**
 * @Description
 * @Author wangjie<https://my.oschina.net/xiaowangqiongyou>
 * @Date 2017/10/12
 */
public class RequestLimitException extends RuntimeException {
    private static final long serialVersionUID = 1364225358754654702L;

	private String code;
	private String message;

	public RequestLimitException() {
		super();
	}

	public RequestLimitException(String message) {
		this.message = message;
	}


	public RequestLimitException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return this.code;
	}

	/**
	 * 不打印业务异常堆栈
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

}
