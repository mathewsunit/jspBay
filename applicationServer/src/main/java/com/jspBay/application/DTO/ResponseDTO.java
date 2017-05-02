package com.jspBay.application.DTO;

/**
 * Created by scy11a on 5/2/17.
 */
public class ResponseDTO<T> {

	String status;
	String message;
	T object;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public ResponseDTO(String status, String message, T object) {
		this.status = status;
		this.message = message;
		this.object = object;
	}

	public ResponseDTO(String status) {
		this.status = status;
	}

	public ResponseDTO(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public ResponseDTO() {
	}
}