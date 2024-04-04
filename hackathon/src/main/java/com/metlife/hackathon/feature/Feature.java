package com.metlife.hackathon.feature;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Feature {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	@Comment("보험 아이디")
	private Long insuranceId;

	@Column(nullable = false)
	@Comment("특장점")
	private String feature;
}
