package com.tota.RedditClone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tota.RedditClone.dto.CommentsDto;
import com.tota.RedditClone.exceptions.SpringRedditException;
import com.tota.RedditClone.mapper.CommentMapper;
import com.tota.RedditClone.model.Comment;
import com.tota.RedditClone.model.NotificationEmail;
import com.tota.RedditClone.model.Post;
import com.tota.RedditClone.model.User;
import com.tota.RedditClone.repository.CommentRepository;
import com.tota.RedditClone.repository.PostRepository;
import com.tota.RedditClone.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class CommentService {

	private final CommentMapper commentMapper;
	private final AuthService authService;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final MailContentBuilder mailContentBuilder;
	private final MailService mailService;
	private final UserRepository userRepository;
	
	public void save(CommentsDto commentsDto) {
		Post post = postRepository.findById(commentsDto.getPostId())
				.orElseThrow(() -> new SpringRedditException("Not found post with id" + commentsDto.getPostId()));

		Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
		commentRepository.save(comment);

		String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post ");
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId){
		Post post = postRepository.findById(postId).orElseThrow(()-> new SpringRedditException("not found post with id "+postId));
		return commentRepository.findByPost(post)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}
	
	public List<CommentsDto>getAllCommentsForUser(String userName){
		User user = userRepository.findByUsername(userName).orElseThrow(()-> new SpringRedditException("Not found user with name "+ userName));
		return commentRepository.findAllByUser(user)
				.stream().map(commentMapper::mapToDto)
				.collect(Collectors.toList());
	}
}
