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
	private Long id;
	
	
	private String userId;
	private String name;
	private String email;
	private String password;
	
	public void update(User newUser) {
		this.password= newUser.password;
		this.email = newUser.password;
		this.name = newUser.name;
		
		
	}

}
