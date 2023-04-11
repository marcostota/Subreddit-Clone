package com.tota.RedditClone.dto;

import com.tota.RedditClone.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {

	private VoteType voteType;
	private Long postId;
	
}
