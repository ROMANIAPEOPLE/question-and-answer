package net.skhu.domain;

import javax.persistence.Entity;

import lombok.Data;

@Data
public class User {
	private Long id;
	private String userId;
	private String password;
	private String name;
	private String email;
	

}
