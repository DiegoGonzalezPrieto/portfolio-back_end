package com.argentinaprograma.portfolio.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Message {
    
    private String msg;

	public Message(String msg){
		this.msg = msg;
	}
}
