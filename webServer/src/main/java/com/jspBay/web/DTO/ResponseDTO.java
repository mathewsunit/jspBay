package com.jspBay.web.DTO;

/**
 * Created by scy11a on 5/2/17.
 */
public class ResponseDTO<T> {

	String message;
	String status;
	T object;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public ResponseDTO() {
	}

	public ResponseDTO(String message, String status, T object) {
		this.message = message;
		this.status = status;
		this.object = object;
	}

	public ResponseDTO(String message, String status) {
		this.message = message;
		this.status = status;
	}

	public ResponseDTO(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseDTO{" +
				"message='" + message + '\'' +
				", status='" + status + '\'' +
				", object=" + object +
				'}';
	}
}
