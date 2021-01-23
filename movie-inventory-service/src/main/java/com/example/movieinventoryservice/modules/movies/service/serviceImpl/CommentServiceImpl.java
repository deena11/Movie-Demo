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
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CommentRepository;
import com.example.movieinventoryservice.modules.movies.service.CommentService;

/**
 * @author M1053559
 *
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	private Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	/**
	 * @author M1053559
	 *
	 * @param commentId
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public void deleteComment(int commentId) throws BusinessException, ServiceException {
		try {
			if (getCommentById(commentId) != null) {
				commentRepository.deleteById(commentId);
			}
		} catch (BusinessException e) {
			throw new BusinessException("commentd id - " + commentId + " not found");
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Delete id-" + commentId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param comment
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Comment updateComment(Comment comment) throws BusinessException, ServiceException {
		try {
			if (getCommentById(comment.getId()) != null) {
				return commentRepository.save(comment);
			}
		} catch (BusinessException ex) {
			throw new BusinessException("commentd id - " + comment.getId() + " not found");
		}

		catch (DataAccessException ex) {
			throw new ServiceException("Failed to Update", ex.getCause());
		} catch (Exception ex) {
			throw new BusinessException("Failed to Update", ex.getCause());
		}
		return comment;
	}

	/**
	 * @author M1053559
	 *
	 * @param commentId
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Comment getCommentById(int commentId) throws BusinessException, ServiceException {
		try {
			logger.info("Entered into Comment Service - getByid " + commentId);
			logger.info(commentRepository.findAll().toString());
			Optional<Comment> comment = commentRepository.findById(commentId);
			if (comment.isPresent()) {
				logger.info(comment.get().toString());
				return comment.get();
			} else {
				throw new BusinessException("Comment id -" + commentId + "is not present");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public List<Comment> getAllComments() throws BusinessException, ServiceException {
		try {
			List<Comment> comments = commentRepository.findAll();
			if (!comments.isEmpty()) {
				return comments;
			} else {
				throw new BusinessException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed to Fetch", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param comment
	 * @return
	 * @throws BusinessException
	 * @throws ServiceException
	 */
	@Override
	public Comment addComment(Comment comment) throws BusinessException, ServiceException {
		try {
			return commentRepository.save(comment);
		} catch (DataAccessException ex) {
			throw new ServiceException("Failed To Add Comment", ex.getCause());
		} catch (Exception ex) {
			throw new BusinessException("Invalid Data");
		}
	}

}
