package net.skhu.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "userId"))
	private User writer;
	
	private String title;
	
	private String contents;

	public Question() {} //JPA에서는 디폴트생성자가 필수이다.
	
	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}
}
