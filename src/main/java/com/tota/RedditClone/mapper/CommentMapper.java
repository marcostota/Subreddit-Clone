package com.tota.RedditClone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tota.RedditClone.dto.CommentsDto;
import com.tota.RedditClone.model.Comment;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	
	@Mapping(target="id", ignore = true)
	@Mapping(target="text", source = "commentsDto.text")
	@Mapping(target="post", source = "post")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	Comment map(CommentsDto commentsDto, Post post, User user);
	
	
	@Mapping(target="postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target="userName", expression = "java(comment.getUser().getUsername())")
	CommentsDto mapToDto(Comment comment);
}
