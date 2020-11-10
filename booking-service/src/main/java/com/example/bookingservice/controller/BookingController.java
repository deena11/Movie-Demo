package com.example.bookingservice.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.exception.InValidIdException;
import com.example.bookingservice.exception.ServiceException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.response.ApiErrorResponse;
import com.example.bookingservice.service.BookingService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * @author M1053559
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/bookings/v1")
public class BookingController {

	private Logger logger = LoggerFactory.getLogger(BookingController.class);

	private String message = "";

	@Autowired
	private BookingService bookingService;

	/**
	 * @param booking
	 * @param request
	 * @return
	 * @throws BookingServiceDaoException
	 * @throws ServiceException
	 */
	@PostMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponseBooking", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> addBooking(@RequestBody Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException, ServiceException {

		logger.info("Adding booking Data Request is Processing ");

		Booking bookingDetails = bookingService.addBooking(booking, request);
		message = "Booking Data Saved Successfull";
		
		logger.info("Booking Data is Stored Successfully");

		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(responseBuilder(message, bookingDetails));
	}

	/**
	 * @return
	 * @throws BookingServiceDaoException
	 * @throws ServiceException
	 */
	@GetMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
//	@PreAuthorize("hasRole('ROLE_user') or hasRole('ROLE_admin')")
	public ResponseEntity<?> getAllBooking() throws BookingServiceDaoException, ServiceException {

		logger.info("Get All booking Data Request is Processing");

		List<Booking> bookingList = new ArrayList<Booking>();
		bookingList = bookingService.getAllBooking();
		message = "Fetching all Booking Data is Successfull";
		
		logger.info("Successfully fetched All Booking Data");

		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(responseBuilder(message, bookingList));
	}

	/**
	 * @param bookingId
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws InValidIdException
	 */
	@GetMapping("/{bookingId}")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> getBookingById(@PathVariable int bookingId, HttpServletRequest request)
			throws ServiceException, InValidIdException {

		logger.info("Fetching booking Data of id - "+bookingId);

		Booking booking = bookingService.getBookingById(bookingId, request);
		message = "Get Booking by Id Successfull";

		logger.info("Booking Data of id - "+ bookingId +" is Successfull");
		
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(responseBuilder(message, booking));
	}

	/**
	 * @param booking
	 * @param request
	 * @return
	 * @throws BookingServiceDaoException
	 * @throws ServiceException
	 */
	@PutMapping("/")
	@HystrixCommand(fallbackMethod = "fallBackResponseBooking", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> updateBooking(@RequestBody Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException, ServiceException {

		logger.info("Update booking Data Request is processing ");

		Booking bookingDetails = bookingService.updateBooking(booking, request);
		message = "Booking Updated Successfull";
		
		logger.info("Successfully Updated Booking data of id - "+booking.getId());

		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(responseBuilder(message, bookingDetails));
	}

	/**
	 * @param bookingId
	 * @param request
	 * @return
	 * @throws BookingServiceDaoException
	 * @throws ServiceException
	 */
	@DeleteMapping("/{bookingId}")
	@HystrixCommand(fallbackMethod = "fallBackResponse", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000") })
	public ResponseEntity<?> deleteUser(@PathVariable int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException, ServiceException {

		logger.info("Delete booking Request is Processing");

		message = "Booking Deleted Successfull";
		String result = bookingService.deleteBooking(bookingId, request);
		
		logger.info("Successfully Deleted Booking Data of id - "+bookingId);

		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.ACCEPTED))
				.body(responseBuilder(message, result));
	}

	/**
	 * @param bookingId
	 * @param request
	 * @return
	 * fallback response
	 */
	public ResponseEntity<?> fallBackResponse(int bookingId, HttpServletRequest request) {
		
		logger.info("Fallback Service is Called  ");
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(fallbackResponseBuilder());
	}

	/**
	 * @param booking
	 * @param request
	 * @return
	 */
	public ResponseEntity<?> fallBackResponseBooking(Booking booking, HttpServletRequest request) {
		
		logger.info("Fallback Service is Called  ");
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(fallbackResponseBuilder());
	}

	/**
	 * @return
	 */
	public ResponseEntity<?> fallBackResponse() {
		
		logger.info("Fallback Service is Called  ");
		return ResponseEntity.status(HttpStatus.OK).header("message", String.valueOf(HttpStatus.GATEWAY_TIMEOUT))
				.body(fallbackResponseBuilder());
	}

	/**
	 * @param message
	 * @param body
	 * @return
	 * 
	 * fallback success response
	 */
	public APISuccessResponse responseBuilder(String message, Object body) {

		logger.info("Success Response is Building");

		APISuccessResponse response = new APISuccessResponse();
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.OK.toString());
		response.setBody(body);
		response.setStatusCode(200);
		
		logger.info("Success Response is builded Successfully");

		return response;

	}

	/**
	 * @return
	 */
	public ApiErrorResponse fallbackResponseBuilder() {
		
		logger.info("Fallback Response Builder Method processing started");

		ApiErrorResponse response = new ApiErrorResponse();
		response.setHttpStatus(HttpStatus.REQUEST_TIMEOUT.toString());
		response.setMessage("Service Takes More time than Expected");
		response.setError(true);
		response.setSuccess(false);
		
		logger.info("Fallabck response is builded successfully");

		return response;

	}

}