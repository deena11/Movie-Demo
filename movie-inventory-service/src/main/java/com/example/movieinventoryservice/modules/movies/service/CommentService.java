package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;

public interface CommentService {

	public Comment addComment(Comment comment) throws RecordNotAddedException,ServiceException;
	
	public void deleteComment(int commentId) throws RecordNotDeletedException,ServiceException;
	
	public Comment updateComment(Comment comment) throws RecordNotUpdatedException,ServiceException;
	
	public Comment getCommentById(int commentId) throws RecordNotFoundException,ServiceException;
	
	public List<Comment> getAllComments() throws EmptyListException,ServiceException;


}
