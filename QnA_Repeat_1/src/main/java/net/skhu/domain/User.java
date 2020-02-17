package net.skhu.domain;

import javax.persistence.Column;
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
	
	@Column(nullable=true ,length=20, unique=true)
	private String userId;
	private String password;
	private String name;
	private String email;
	
	public void update(User updateUser) {
		this.userId=updateUser.userId;
		this.password=updateUser.password;
		this.name=updateUser.name;
		this.email=updateUser.email;
		
	}
	

}
