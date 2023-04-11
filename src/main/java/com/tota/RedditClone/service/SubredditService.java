package com.tota.RedditClone.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tota.RedditClone.dto.SubRedditDto;
import com.tota.RedditClone.exceptions.SpringRedditException;
import com.tota.RedditClone.mapper.SubredditMapper;
import com.tota.RedditClone.model.Subreddit;
import com.tota.RedditClone.model.User;
import com.tota.RedditClone.repository.SubredditRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	private AuthService authService;


	@Transactional
	public SubRedditDto save(SubRedditDto subredditDto) {
		User currentUser = authService.getCurrentUser();
		Subreddit save = subredditRepository.save(subredditMapper.mapDtoToSubreddit(subredditDto, currentUser));
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	@Transactional(readOnly = true)
	public List<SubRedditDto> getAll() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDto).collect(toList());
	}

	public SubRedditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id)
				.orElseThrow(() -> new SpringRedditException("No subreddit found with ID - " + id));
		return subredditMapper.mapSubredditToDto(subreddit);
	}


	



	
}