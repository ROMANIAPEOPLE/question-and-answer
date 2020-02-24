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
	public boolean mathPassword(String newPassword) {
		if(newPassword == null) {
			return false; //패스워스를 입력하지 않았을 경우
		}
		return newPassword.equals(password);
	}
	
	public boolean matchId(Long newId) {
		if(newId == null) {
			return false;
		}
		return newId.equals(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
