package com.example.bookingservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.exception.InValidIdException;
import com.example.bookingservice.exception.ServiceException;
import com.example.bookingservice.model.Booking;

public interface BookingService {

	public Booking addBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException,ServiceException;

	public List<Booking> getAllBooking() throws BookingServiceDaoException,ServiceException;

	public Booking getBookingById(int bookingId,HttpServletRequest request) throws ServiceException, InValidIdException;

	public Booking updateBooking(Booking booking,HttpServletRequest request) throws BookingServiceDaoException,ServiceException;

	public String deleteBooking(int bookingId,HttpServletRequest request) throws BookingServiceDaoException,ServiceException;

}
