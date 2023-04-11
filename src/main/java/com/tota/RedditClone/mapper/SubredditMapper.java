package com.tota.RedditClone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tota.RedditClone.dto.SubRedditDto;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.Subreddit;
import com.tota.RedditClone.model.User;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
	
	SubredditMapper INSTANCE = Mappers.getMapper(SubredditMapper.class);
	

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditDto mapSubredditToDto(Subreddit subreddit);


    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }
 
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Subreddit mapDtoToSubreddit(SubRedditDto subredditDto, User user);
}