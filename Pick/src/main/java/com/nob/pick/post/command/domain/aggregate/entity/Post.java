package com.nob.pick.post.command.domain.aggregate.entity;

import java.text.SimpleDateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Post {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "category", nullable = false)
	private int category;
	
	@Column(name = "upload_at", nullable = false)
	private String uploadAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	
	@Column(name = "update_at")
	private String updateAt;
	
	@Column(name = "status", nullable = false)
	private int status = 0;
	
	@Column(name = "member_id", nullable = false)
	private int memberId;
	
	
}
