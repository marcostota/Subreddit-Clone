package com.tota.RedditClone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tota.RedditClone.dto.PostRequest;
import com.tota.RedditClone.dto.PostResponseDto;
import com.tota.RedditClone.service.PostService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;	

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor

public class PostController {

	private final PostService postService;

	 @PostMapping
	    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
	        postService.save(postRequest);
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    }
	

	@GetMapping	
	public ResponseEntity<List<PostResponseDto>> getAllPosts() {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
	}
	
	@GetMapping("by-subreddit/{id}")
	public ResponseEntity<List<PostResponseDto>> getPostsBySubreddit(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(id));
	}
	
	@GetMapping("by-user/{name}")
	public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable Long username){
		return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubreddit(username));
	}
	
}
