package net.skhu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String userId;
	private String password;
	private String name;
	private String email;
	
	public void update(User newUser) {
		this.password =  newUser.password;
		this.email = newUser.email;
		this.name = newUser.name;
	}
	
}
