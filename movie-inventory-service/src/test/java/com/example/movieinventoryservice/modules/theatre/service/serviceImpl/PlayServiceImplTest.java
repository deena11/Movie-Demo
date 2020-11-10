package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.repository.MovieRepository;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.modules.movies.service.serviceImpl.MovieServiceImpl;
import com.example.movieinventoryservice.modules.theatre.repository.AddressRepository;
import com.example.movieinventoryservice.modules.theatre.repository.LocationRepository;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@WebMvcTest({ PlayServiceImpl.class,ScreenServiceImpl.class,TheatreServiceImpl.class, MovieServiceImpl.class })
public class PlayServiceImplTest {

	@Autowired
	private ScreenService screenService;
	
	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private MovieService movieService;
	
	@MockBean
	private TheatreRepository theatreRepository;
	
	public ApiSuccessResponse apiResponse;

	@Autowired
	private PlayService playService;
	
	@MockBean
	private ScreenRepository screenRepository;

	@MockBean
	private PlayRepository playRepository;
	
	@MockBean
	private AddressRepository addressRepository;
	
	@MockBean
	private LocationRepository locationRepository;
	
	@MockBean
	private MovieRepository movieRepository;
	
	@MockBean
	private CastRepository castRepository;

	@MockBean
	private GenreRepository genreRepository;
	
	
	@Before
	public void setUp() throws Exception {
		List<Play> play = new ArrayList<>();
		play.add(getPlay());
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenReturn(getPlay());
		Mockito.when(playRepository.getOne(Mockito.anyInt())).thenReturn(getPlay());
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getPlay()));
		Mockito.when(playRepository.findAll()).thenReturn(play);
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getScreen()));
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Mockito.when(movieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getMovie()));
	}

	@Test
	public void addPlayTest() throws Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenReturn(getPlay());
		Play play = playService.addPlay(getPlay());
		assertEquals(1, play.getId());

	}

	@Test(expected = ServiceException.class)
	public void addPlayTestError() throws Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenThrow(Mockito.mock(DataAccessException.class));
		Play play = playService.addPlay(getPlay());
	}

	@Test
	public void getAllPlayTest() throws Exception {
		List<Play> playList = new ArrayList<>();
		playList.add(getPlay());
		Mockito.when(playRepository.findAll()).thenReturn(playList);
		List<Play> playList1 = playService.getAllPlay();
		assertEquals(1, playList1.size());
	}

	@Test(expected = EmptyListException.class)
	public void getAllPlayNotFoundTest() throws Exception {
		List<Play> playList = new ArrayList<>();
		Mockito.when(playRepository.findAll()).thenReturn(playList);
		List<Play> playList1 = playService.getAllPlay();
	}

	@Test(expected = ServiceException.class)
	public void getAllPlayTestError() throws Exception {
		Mockito.when(playRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		List<Play> playList1 = playService.getAllPlay();
	}

	@Test
	public void getPlayByIdTest() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getPlay()));
		Play play = playService.getPlayById(1);
		assertEquals(1, play.getId());
	}

	@Test(expected = RecordNotFoundException.class)
	public void getPlayByIdNotFoundTest() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Play play = playService.getPlayById(1);
	}

	@Test(expected = ServiceException.class)
	public void getPlayByIdTestError() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		Play play = playService.getPlayById(1);
	}

	@Test
	public void updatePlayTest() throws Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenReturn(getPlay());
		Play play = playService.updatePlay(getPlay());
		assertEquals(1, play.getId());
	}

	@Test(expected = ServiceException.class)
	public void updatePlayTestError() throws JsonProcessingException, Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenThrow(Mockito.mock(DataAccessException.class));
		Play play = playService.updatePlay(getPlay());
	}

	@Test
	public void deletePlayTest() throws Exception {
		playService.deletePlay(1);
		Mockito.verify(playRepository, Mockito.times(1)).deleteById(Mockito.any());
	}

	@Test(expected = ServiceException.class)
	public void deletePlayTestError() throws Exception {
		Mockito.doThrow(Mockito.mock(DataAccessException.class)).when(playRepository).deleteById(Mockito.anyInt());
		playService.deletePlay(1);
	}

	public Play getPlay() {
		Play play = new Play();
		play.setId(1);
		play.setScreen(getScreen());
		play.setMovie(getMovie());
		return play;
	}

	public Screen getScreen() {
		Screen screen = new Screen();
		screen.setId(1);
		return screen;
	}

	public Movie getMovie() {
		Movie movie = new Movie();
		movie.setId(1);
		return movie;
	}
	public Theatre getTheatre() {
		Theatre theatre = new Theatre();
		theatre.setId(1);
		theatre.setName("test");
		
		return theatre;
	}
}
