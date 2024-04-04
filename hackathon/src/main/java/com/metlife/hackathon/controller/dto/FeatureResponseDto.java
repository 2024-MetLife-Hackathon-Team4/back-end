package com.metlife.hackathon.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureResponseDto {
	String title;
	String detail;

	public static FeatureResponseDto of(String feature) {
		return FeatureResponseDto.builder()
			.title(feature.split(":")[0])
			.detail(feature.split(":")[1])
			.build();
	}
}
