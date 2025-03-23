package com.nob.pick.post.command.domain.aggregate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "category")
	private int category;
	
	@Column(name = "upload_at")
	private String uploadAt;
	
	@Column(name = "update_at")
	private String updateAt;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "member_id")
	private int memberId;
}
