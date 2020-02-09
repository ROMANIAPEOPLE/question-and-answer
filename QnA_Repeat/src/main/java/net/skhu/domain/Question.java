package net.skhu.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name= "writerId")
	private User writer;
	
	private String title;
	private String contents;
	
	private LocalDateTime createDate;

	public Question() {} //JPA에서는 디폴트생성자가 필수이다.
	
	public Question(User user, String title, String contents) {
		this.writer = user;
		this.title = title;
		this.contents = contents;
//		this.createDate = LocalDateTime.now();
	}
	public String getFormattedCreateDate() {
		if(createDate == null) {
			return "";
		}
		
//		return createDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
}