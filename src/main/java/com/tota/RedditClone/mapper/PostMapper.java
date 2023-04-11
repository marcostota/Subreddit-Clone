package com.tota.RedditClone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.tota.RedditClone.dto.PostRequest;
import com.tota.RedditClone.dto.PostResponseDto;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.Subreddit;
import com.tota.RedditClone.model.User;
import com.tota.RedditClone.repository.CommentRepository;

@Mapper(componentModel = "spring")
public abstract class PostMapper {



	@Autowired
	private CommentRepository commentRepository;


	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "voteCount", constant = "0")

	public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

//	    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
//	    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
	@Mapping(target = "id", source = "postId")
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source = "user.username")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	public abstract PostResponseDto mapToDto(Post post);

	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}
	
	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}
	
}
