package com.nob.pick.badge.command.domain.aggregate;

import com.nob.pick.challenge.command.domain.aggregate.Challenge;
import com.nob.pick.common.config.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BADGE")
@Getter
@Setter
public class Badge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private Integer requirement;
	private int advantage;
	private String description;

	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private Boolean isDeleted = false;

	@ManyToOne
	@JoinColumn(name = "challenge_id")
	private Challenge challenge;
}
