package com.example.bookingservice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.exception.InValidRequestException;
import com.example.bookingservice.model.Booking;

public interface BookingService {

	public Booking addBooking(Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException;

	public List<Booking> getAllBooking() throws BookingServiceDaoException, InValidRequestException;

	public Booking getBookingById(int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException, InValidRequestException;

	public Booking updateBooking(Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException, InValidRequestException;

	public String deleteBooking(int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException, InValidRequestException;

}
