package com.tota.RedditClone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tota.RedditClone.dto.VoteDto;
import com.tota.RedditClone.exceptions.SpringRedditException;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.Vote;
import com.tota.RedditClone.repository.PostRepository;
import com.tota.RedditClone.repository.VoteRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static com.tota.RedditClone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

	private final PostRepository postRepository;
	private final VoteRepository voteRepository;
	private final AuthService authService;

	@Transactional
	public void vote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new SpringRedditException("Not found post with Id " + voteDto.getPostId()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType());
		}
		if (UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
}
