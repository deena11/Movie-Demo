package com.example.bookingservice.service.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.bookingservice.dto.Play;
import com.example.bookingservice.exception.BookingServiceDaoException;
import com.example.bookingservice.exception.InValidRequestException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.service.BookingService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author M1053559
 * @description business logics for booking service
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

	private static final String CAUSE = "cause ";

	private static final String MESSAGE = "message ";

	@Value("${play.url}")
	private String playUrl;

	@Value("${kafka.url}")
	private String kafkaUrl;

	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ObjectMapper objectMapper;

	private Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

	/**
	 * @author M1053559
	 *
	 * @param booking
	 * @param request
	 * @return
	 * @throws BookingServiceDaoException
	 * @throws BookingServiceDaoException
	 */
	@Override
	@Transactional
	public Booking addBooking(Booking booking, HttpServletRequest request) throws BookingServiceDaoException {
		try {

			Play play = getPlayById(booking.getPlayId(), request);

			if (play.getSeatsAvailable() - booking.getSeatCount() >= 0) {

				play.setSeatsAvailable(play.getSeatsAvailable() - booking.getSeatCount());

				APISuccessResponse apiResponse = updateSeatsInPlay(play, request);
				kafkaMessage(apiResponse);
				return bookingRepository.save(booking);



			} else {
				throw new InValidRequestException(" Required Seats are  Unavailable seats are full");
			}

		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to Book", ex.getCause());
		} catch (Exception ex) {
			throw new BookingServiceDaoException(ex.getMessage(), ex.getCause());
		}

	}

	private void kafkaMessage(APISuccessResponse apiResponse) {
		try {
			restTemplate.exchange(kafkaUrl + apiResponse.getBody().toString(), HttpMethod.GET, null,
					String.class);
		} catch (Exception ex) {
			logger.error(MESSAGE + ex.getMessage() + CAUSE + ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @return List of bookings
	 * @throws BookingServiceDaoException
	 * @throws InValidRequestException
	 */
	@Override
	@Transactional
	public List<Booking> getAllBooking() throws BookingServiceDaoException, InValidRequestException {
		try {
			List<Booking> bookings = bookingRepository.findAll();
			if (!bookings.isEmpty()) {
				return bookings;
			} else {
				throw new InValidRequestException("No Bookings Data the list is empty");
			}
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to fetch all Booking ", ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param bookingId
	 * @param request
	 * @return booking information of requested id
	 * @throws InValidRequestException
	 * @throws BookingServiceDaoException
	 */
	@Override
	@Transactional
	public Booking getBookingById(int bookingId, HttpServletRequest request)
			throws InValidRequestException, BookingServiceDaoException {
		try {
			Optional<Booking> booking = bookingRepository.findById(bookingId);
			if (booking.isPresent()) {
				return booking.get();
			} else {
				throw new InValidRequestException("No Record Found for the id - " + bookingId);
			}
		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to fetch Booking of id " + bookingId, ex.getCause());
		}
	}

	/**
	 * @author M1053559
	 *
	 * @param booking
	 * @param request
	 * @return updated booking data
	 * @throws BookingServiceDaoException
	 * @throws BookingServiceDaoException
	 * @throws InValidRequestException
	 */
	@Override
	@Transactional
	public Booking updateBooking(Booking booking, HttpServletRequest request)
			throws BookingServiceDaoException, InValidRequestException {
		try {

			Play play = getPlayById(booking.getPlayId(), request);

			if (play.getSeatsAvailable() - booking.getSeatCount() >= 0) {

				play.setSeatsAvailable(play.getSeatsAvailable() - booking.getSeatCount());

				updateSeatsInPlay(play, request);

				return bookingRepository.save(booking);

			} else {
				throw new InValidRequestException(" Required Seats are  Unavailable seats are full");
			}

		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to update Booking", ex.getCause());
		}

	}

	/**
	 * @author M1053559
	 *
	 * @param bookingId
	 * @param request
	 * @return message indicating successfully deleted
	 * @throws BookingServiceDaoException
	 * @throws BookingServiceDaoException
	 * @throws InValidRequestException
	 */
	@Override
	@Transactional
	public String deleteBooking(int bookingId, HttpServletRequest request)
			throws BookingServiceDaoException, InValidRequestException {
		try {

			Optional<Booking> booking = bookingRepository.findById(bookingId);
			if (!booking.isPresent()) {
				throw new InValidRequestException("No Booking Data found for the id - " + bookingId);
			}


			Play play = getPlayById(booking.get().getPlayId(), request);

			play.setSeatsAvailable(play.getSeatsAvailable() + booking.get().getSeatCount());

			updateSeatsInPlay(play, request);

			bookingRepository.deleteById(bookingId);

			return "Successfully deleted booking of id - " + bookingId;

		} catch (DataAccessException ex) {
			throw new BookingServiceDaoException("Failed to Delete Booking", ex.getCause());
		}
	}

	/**
	 * @param response
	 * @return play
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 
//	private Play getPlayObjcetMapper(String response) throws JsonParseException, JsonMappingException, IOException {
//		return new ObjectMapper().readValue(response, Play.class);
//	}

	/**
	 * @param request
	 * @return HttpHeader to build header for RestTemplate
	 */
	private HttpHeaders buildHeader(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Authorization", authorization);
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		return requestHeaders;
	}

	/**
	 * @param playId
	 * @param request
	 * @return Play to fetch play data by playid
	 * @throws BookingServiceDaoException 
	 */
	private Play getPlayById(int playId, HttpServletRequest request) throws BookingServiceDaoException {

		HttpEntity requestEntity = new HttpEntity(buildHeader(request));

		APISuccessResponse response = restTemplate
				.exchange(playUrl + playId, HttpMethod.GET, requestEntity, APISuccessResponse.class).getBody();

		if (response!=null && response.getHttpStatus().equals(HttpStatus.OK.toString())) {
			return objectMapper.convertValue(response.getBody(), new TypeReference<Play>() {
			});
		}else {
			throw new BookingServiceDaoException("Invalid Response Internal server error");
		}

	}

	/**
	 * @param play
	 * @param request
	 * @return SuccessRespone to update Play Record
	 */
	private APISuccessResponse updateSeatsInPlay(Play play, HttpServletRequest request) {

		HttpEntity<Play> updateRequest = new HttpEntity<>(play, buildHeader(request));

		return restTemplate
				.exchange(playUrl, HttpMethod.PUT, updateRequest, APISuccessResponse.class).getBody();

	}

}
