package com.example.bookingservice.service.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.bookingservice.dto.Play;
import com.example.bookingservice.dto.Screen;
import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.exception.InValidRequestException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {BookingServiceImpl.class,ObjectMapper.class})
public class BookingServiceImplTest {
	
	public APISuccessResponse apiResponse;

	@MockBean
	private WebClient.Builder webclient;
	
	@Autowired
	private BookingService bookingService;

	@MockBean
	private BookingRepository bookingRepository;

	@MockBean
	private RestTemplate restTemplate;
	
	@MockBean
	private HttpServletRequest httpServletRequest;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		List<Booking> bookings = new ArrayList<>();
		bookings.add(getBooking());
		Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(getBooking());
//		Mockito.verify(bookingRepository).deleteById(Mockito.anyInt());
		Mockito.when(bookingRepository.getOne(Mockito.anyInt())).thenReturn(getBooking());
		Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getBooking()));
		Mockito.when(bookingRepository.findAll()).thenReturn(bookings);
		
		Mockito.when(restTemplate.exchange(
	            Matchers.anyString(),
	            Matchers.eq(HttpMethod.GET),
	            Matchers.<HttpEntity> any(),
	            Matchers.<Class<APISuccessResponse>>any())
	        ).thenReturn(getApiSuccessResponse());
		
		Mockito.when(restTemplate.exchange(
	            Matchers.anyString(),
	            Matchers.eq(HttpMethod.PUT),
	            Matchers.<HttpEntity<?>> any(),
	            Matchers.<Class<APISuccessResponse>>any())
	        ).thenReturn(getApiSuccessResponse());
		
		//Mockito.when(webclient.build().get().uri(Mockito.anyString()).retrieve().bodyToMono(String.class)).thenReturn(Mono.just("Success"));
		
		
	}

	@Test
	public void addBooking() throws Exception {

		Mockito.when(bookingRepository.findById(Mockito.any())).thenReturn(Optional.of(getBooking()));
		Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(getBooking());
		Booking booking = bookingService.addBooking(getBooking(),httpServletRequest);
		assertEquals(1,booking.getId());
	}
	@Test
	public void updateBooking() throws Exception {

		Mockito.when(bookingRepository.findById(Mockito.any())).thenReturn(Optional.of(getBooking()));
		Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(getBooking());
		Booking booking = bookingService.addBooking(getBooking(),httpServletRequest);
		assertEquals(1,booking.getId());
	}
	
	@Test(expected=BookingServiceDaoException.class)
	public void addBookingTestError() throws Exception {
		Mockito.when(bookingRepository.save(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));
		Booking booking=bookingService.addBooking(getBooking(),httpServletRequest);
	}
	
	@Test(expected = BookingServiceDaoException.class)
	public void updateBookingTestError() throws Exception {
		Mockito.when(bookingRepository.save(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));
		Booking booking=bookingService.addBooking(getBooking(),httpServletRequest);
	}
	
	@Test
	public void getAllBooking() throws Exception {
		List list = new ArrayList();
		list.add(getBooking());
		Mockito.when(bookingRepository.findAll()).thenReturn(list);
//		APISuccessResponse api = new APISuccessResponse();
//		List list1 = new ArrayList();
//		list1.add(getBooking());
//		api.setBody(list1);
//		api.setStatusCode(200);
//		Mockito.when(restTemplate.exchange(ArgumentMatchers.matches("http://USER-SERVICE/users/v1/"),
//				Matchers.eq(HttpMethod.GET), Mockito.any(), ArgumentMatchers.any(Class.class)))
//				.thenReturn(new ResponseEntity<APISuccessResponse>(api, HttpStatus.ACCEPTED));
//		List list2 = new ArrayList();
//		list2.add(play());
//		APISuccessResponse api2 = new APISuccessResponse();
//		api2.setBody(list2);
//		api2.setStatusCode(200);
//		Mockito.when(restTemplate.exchange(ArgumentMatchers.matches("http://INVENTORY-SERVICE/play/v1/"),
//				Matchers.eq(HttpMethod.GET), Mockito.any(), ArgumentMatchers.any(Class.class)))
//				.thenReturn(new ResponseEntity<APISuccessResponse>(api2, HttpStatus.ACCEPTED));
		
		List<Booking> bookingList=bookingService.getAllBooking();
		assertEquals(1,bookingList.size());
	}
	
	@Test(expected=BookingServiceDaoException.class)
	public void getAllBookingException() throws Exception{
		Mockito.when(bookingRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		List<Booking> bookingList=bookingService.getAllBooking();
	}
	
	
	@Test
	public void getBookingByIdTest() throws Exception{
		APISuccessResponse api = new APISuccessResponse();
		api.setBody(getBooking());
		api.setStatusCode(200);
		Mockito.when(bookingRepository.findById(Mockito.any())).thenReturn(Optional.of(getBooking()));
//		Mockito.when(restTemplate.exchange(ArgumentMatchers.matches("http://USER-SERVICE/users/v1/1"),
//				Matchers.eq(HttpMethod.GET), Mockito.any(), ArgumentMatchers.any(Class.class)))
//				.thenReturn(new ResponseEntity<APISuccessResponse>(api, HttpStatus.ACCEPTED));

		Booking booking=bookingService.getBookingById(1,httpServletRequest);
		assertEquals(1,booking.getId());
	}
	
	@Test(expected=InValidRequestException.class)
	public void getBookingByIdTestError() throws Exception{
		Mockito.when(bookingRepository.findById(Mockito.any())).thenReturn(Optional.empty());
		Booking booking=bookingService.getBookingById(1,httpServletRequest);
	}
	
	@Test
	public void deleteBooking() throws Exception {
		Mockito.when(bookingRepository.findById(Mockito.any())).thenReturn(Optional.of(getBooking()));
		bookingService.deleteBooking(1,httpServletRequest);
		Mockito.verify(bookingRepository,Mockito.times(1)).deleteById(Mockito.any());
	}
	
	@Test(expected=BookingServiceDaoException.class)
	public void deleteBookingException() throws Exception {
		Mockito.doThrow(Mockito.mock(DataAccessException.class)).when(bookingRepository).deleteById(Mockito.anyInt());
		bookingService.deleteBooking(1, httpServletRequest);
	}
	
	
	
	
	public ResponseEntity<APISuccessResponse> getApiSuccessResponse() {
		APISuccessResponse response = new APISuccessResponse();
		response.setBody(getPlay());
		
		response.setMessage("success");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	public Play getPlay() {

		Play play = new Play();

		play.setDate("testDate");
		play.setSeatsAvailable(120);
		
		return play;
	}
	
	public Booking getBooking() {
		Booking booking = new Booking();
		booking.setId(1);
		booking.setSeatCount(2);
		booking.setPlayId(1);
		booking.setUserId(1);
		booking.setTotalPrice(1234);
		return booking;
	}


	public Play play() {
		Screen screen = new Screen();
		screen.setTotalSeatsPerScreen(200);
		Play play = new Play();
		play.setSeatsAvailable(90);
		play.setId(1);
		play.setId(1);
		play.setScreen(screen);
		return play;
	}
	
	

	
	public String bookingJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(getBooking());
	}
}
