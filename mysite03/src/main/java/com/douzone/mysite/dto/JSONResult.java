package com.douzone.mysite.dto;

public class JSONResult {
	private String result; /* success or fail */
	private Object data;
	
	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	private String message;
	
	private JSONResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	private JSONResult(String message) {
		this.result = "fail";
		this.message = message;
	}
		
	public static JSONResult success(Object data) {
		return new JSONResult(data);
	}
	
	public static JSONResult fail(String message) {
		return new JSONResult(message);
	}
}
