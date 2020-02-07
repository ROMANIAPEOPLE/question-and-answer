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
	@Column(nullable=false, length=2, unique=true)
	private String userId;
	private String name;
	private String email;
	private String password;
	
	public void update(User newUser) {
		this.password= newUser.password;
		this.email = newUser.password;
		this.name = newUser.name;
	}
	
	public boolean matchId(Long newId) {
		if(newId==null) {
			return false;
		}
		return newId.equals(id);
	}
	public boolean matchPassword(String newPassword) {
		if(newPassword == null) {
			return false;
		}		
		return newPassword.equals(password);
	}
}