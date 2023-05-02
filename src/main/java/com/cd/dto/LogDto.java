package com.cd.dto;

import java.time.LocalDateTime;

public class LogDto {

	private LocalDateTime datetime;
	
	private LogLevel event;
	
	private String message;

	public LogDto() {
		super();
	}

	public LogDto(LocalDateTime datetime, LogLevel event, String message) {
		super();
		this.datetime = datetime;
		this.event = event;
		this.message = message;
	}
	
	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public LogLevel getEvent() {
		return event;
	}

	public void setEvent(LogLevel event) {
		this.event = event;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
