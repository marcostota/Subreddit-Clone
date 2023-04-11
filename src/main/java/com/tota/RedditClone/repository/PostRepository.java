package com.tota.RedditClone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.Subreddit;
import com.tota.RedditClone.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);

}
