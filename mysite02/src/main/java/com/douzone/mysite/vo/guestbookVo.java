package com.douzone.mysite.vo;

public class guestbookVo {
	private Long no;
	private String name;
	private String password;
	private String message;
	private String dateTime;

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "guestbookVo [no=" + no + ", name=" + name + ", password=" + password + ", message=" + message
				+ ", dateTime=" + dateTime + "]";
	}

}
