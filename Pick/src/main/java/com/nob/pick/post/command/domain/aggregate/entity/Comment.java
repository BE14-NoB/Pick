package com.nob.pick.post.command.domain.aggregate.entity;

import java.text.SimpleDateFormat;

import com.nob.pick.post.command.application.dto.MemberNicknameDTO;
import com.nob.pick.post.command.application.dto.PostStatus;

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
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Comment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "is_adopted")
	private String isAdopted;
	
	@Column(name = "upload_at", nullable = false)
	private String uploadAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
	
	@Column(name = "update_at")
	private String updateAt;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "status", nullable = false)
	private int status = 0;
	
	@Column(name = "post_id", nullable = false)
	private int postId;
	
	@Column(name = "root_comment_id")
	private Integer rootCommentId;	// nullable
	
	@Column(name = "member_id", nullable = false)
	private Long memberId;
}
