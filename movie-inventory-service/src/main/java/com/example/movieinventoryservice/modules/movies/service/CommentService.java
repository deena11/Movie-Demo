package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.BusinessException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface CommentService {

	public Comment addComment(Comment comment) throws BusinessException,ServiceException;
	
	public void deleteComment(int commentId) throws BusinessException,ServiceException;
	
	public Comment updateComment(Comment comment) throws BusinessException,ServiceException;
	
	public Comment getCommentById(int commentId) throws BusinessException,ServiceException;
	
	public List<Comment> getAllComments() throws BusinessException,ServiceException;


}
