package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CommentRepository;
import com.example.movieinventoryservice.modules.movies.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Override
	public void deleteComment(int commentId) throws RecordNotDeletedException, ServiceException {
		try {
			if (getCommentById(commentId) != null) {
				commentRepository.deleteById(commentId);
			}
		} catch (RecordNotFoundException e) {
			throw new RecordNotDeletedException("commentd id - " + commentId + " not found");
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-" + commentId, ex.getCause());
		}
	}

	@Override
	public Comment updateComment(Comment comment) throws RecordNotUpdatedException, ServiceException {
		try {
			if (getCommentById(comment.getId()) != null) {
				return commentRepository.save(comment);
			}
		} catch (RecordNotFoundException ex) {
			throw new RecordNotUpdatedException("commentd id - " + comment.getId() + " not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		} catch (Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		return comment;
	}

	@Override
	public Comment getCommentById(int commentId) throws RecordNotFoundException, ServiceException {
		try {
			logger.info("Entered into Comment Service - getByid " + commentId);
			logger.info(commentRepository.findAll().toString());
			Optional<Comment> comment = commentRepository.findById(commentId);
			if (comment.isPresent()) {
				logger.info(comment.get().toString());
				return comment.get();
			} else {
				throw new RecordNotFoundException("Comment id -" + commentId + "is not present");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Comment> getAllComments() throws EmptyListException, ServiceException {
		try {
			List<Comment> comments = commentRepository.findAll();
			if (comments.size() > 0) {
				return comments;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public Comment addComment(Comment comment) throws RecordNotAddedException, ServiceException {
		try {
			return commentRepository.save(comment);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Comment", ex.getCause());
		} catch (Exception ex) {
			throw new RecordNotAddedException("Invalid Data");
		}
	}

}
