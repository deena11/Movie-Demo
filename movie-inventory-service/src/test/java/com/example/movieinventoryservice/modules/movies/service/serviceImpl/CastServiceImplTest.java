package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.service.CastService;

@RunWith(SpringRunner.class)
@WebMvcTest({ CastServiceImpl.class })
public class CastServiceImplTest {


	@Autowired
	private CastService castService;

	@MockBean
	private CastRepository castRepository;

	@Test
	public void testGetCast() throws Exception {
		Mockito.when(castRepository.getOne(Mockito.anyInt())).thenReturn(getCast());
		Mockito.when(castRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCast()));
		Cast cast = castService.getCastById(1);
		assertTrue(cast.toString().contains("test"));
	}
	@Test(expected=ServiceException.class)
	public void testGetCastException() throws Exception {
		Mockito.when(castRepository.findById(Mockito.anyInt()))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.getCastById(1);

	}
	
	@Test(expected=RecordNotFoundException.class)
	public void testGetNoCastException() throws Exception {
		Mockito.when(castRepository.findById(Mockito.anyInt()))
		.thenReturn(Optional.ofNullable(null));
		castService.getCastById(1);

	}


	@Test
	public void testGetAllCast() throws Exception {
		Mockito.when(castRepository.findAll()).thenReturn(getCastList());
		List<Cast> casts = castService.getAllCasts();
		assertTrue(casts.toString().contains("test"));
	}
	
	@Test(expected=EmptyListException.class)
	public void testGetAllCastEmptyList() throws Exception {
		Mockito.when(castRepository.findAll()).thenReturn(new ArrayList<Cast>());
		List<Cast> casts = castService.getAllCasts();
	}
	
	@Test(expected=ServiceException.class)
	public void testGetAllCastException() throws Exception {
		Mockito.when(castRepository.findAll())
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.getAllCasts();

	}


	@Test
	public void testAddCast() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class))).thenReturn(getCast());
		Cast cast = castService.addCast(getCast());
		assertTrue(cast.toString().contains("test"));

	}
	@Test(expected=ServiceException.class)
	public void testAddCastException() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.addCast(getCast());

	}



	@Test
	public void testUpdateCast() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class))).thenReturn(getCast());
		Mockito.when(castRepository.getOne(Mockito.anyInt())).thenReturn(getCast());
		Mockito.when(castRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCast()));
		Cast cast = castService.updateCast(getCast());
		assertTrue(cast.toString().contains("test"));

	}
	
	@Test(expected=RecordNotUpdatedException.class)
	public void testUpdateCastException() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.updateCast(getCast());

	}
	@Test(expected=ServiceException.class)
	public void testUpdateCastServiceException() throws Exception {
		Mockito.when(castRepository.getOne(Mockito.anyInt())).thenReturn(getCast());
		Mockito.when(castRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCast()));
		Mockito.when(castRepository.save(Mockito.any(Cast.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.updateCast(getCast());

	}



	@Test
	public void testDeleteCast() throws Exception {
		Mockito.when(castRepository.getOne(Mockito.anyInt())).thenReturn(getCast());
		Mockito.when(castRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCast()));
		castService.deleteCast(1);
		Mockito.verify(castRepository, Mockito.times(1)).deleteById(Mockito.any());

	}




	public Cast getCast() {

		Cast cast = new Cast();

		cast.setId(1);
		cast.setName("test");

		return cast;
	}


	public List<Cast> getCastList() {

		Cast cast = new Cast();

		cast.setId(1);
		cast.setName("test");

		List<Cast> casts = new ArrayList<>();
		casts.add(cast);
		return casts;
	}
}


