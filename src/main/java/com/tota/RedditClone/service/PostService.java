package com.tota.RedditClone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tota.RedditClone.dto.AuthenticationResponse;
import com.tota.RedditClone.dto.PostRequest;
import com.tota.RedditClone.dto.PostResponseDto;
import com.tota.RedditClone.exceptions.SpringRedditException;
import com.tota.RedditClone.mapper.PostMapper;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.Subreddit;
import com.tota.RedditClone.model.User;
import com.tota.RedditClone.repository.PostRepository;
import com.tota.RedditClone.repository.SubredditRepository;
import com.tota.RedditClone.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SpringRedditException("Not found subb" +postRequest.getSubredditName()));
       User currentUser = authService.getCurrentUser();
       postRepository.save(postMapper.map(postRequest, subreddit, currentUser));
      
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new SpringRedditException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponseDto> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SpringRedditException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<PostResponseDto> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new SpringRedditException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}