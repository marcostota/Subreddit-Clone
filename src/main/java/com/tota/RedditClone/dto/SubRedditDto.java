package com.tota.RedditClone.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubRedditDto {

	private Long id;
	@NotBlank(message="Community is required")
	private String name;
	@NotBlank(message="Description is required")
	private String description;
	private Integer numberOfPosts;
}
