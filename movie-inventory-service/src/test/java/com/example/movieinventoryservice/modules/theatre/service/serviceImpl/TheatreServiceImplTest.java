package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.movieinventoryservice.entity.Address;
import com.example.movieinventoryservice.entity.Location;
import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.ServiceException;
import com.example.movieinventoryservice.modules.theatre.repository.AddressRepository;
import com.example.movieinventoryservice.modules.theatre.repository.LocationRepository;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TheatreServiceImpl.class })
public class TheatreServiceImplTest {

	@Autowired
	TheatreService theatreService;

	@MockBean
	TheatreRepository theatreRepository;

	@MockBean
	AddressRepository addressRepository;
	
	@MockBean
	LocationRepository locationRepository;
	
	@Test
	public void addTheatreTest() throws Exception
	{
		Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(getAddress());
		Mockito.when(locationRepository.save(Mockito.any(Location.class))).thenReturn(getLocation());
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class))).thenReturn(getTheatre());
		Theatre theatre=theatreService.addTheatre(getTheatre());
		assertEquals(1, theatre.getId());
	}

	@Test(expected=ServiceException.class)
	public void addTheatreTestError() throws JsonProcessingException, Exception
	{
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class))).thenThrow(Mockito.mock(DataAccessException.class));
		Theatre theatre=theatreService.addTheatre(getTheatre());
	}
	
	@Test
	public void getAllTheatreTest() throws Exception
	{
		List<Theatre> theatreList = new ArrayList<>();
		theatreList.add(getTheatre());
		Mockito.when(theatreRepository.findAll()).thenReturn(theatreList);
		List<Theatre> theatreList1=theatreService.getAllTheatre();
		assertEquals(1,theatreList1.size());
	}
	
	@Test(expected=EmptyListException.class)
	public void getAllTheatreNotFoundTest() throws Exception
	{
		List<Theatre> theatreList = new ArrayList<>();
		Mockito.when(theatreRepository.findAll()).thenReturn(theatreList);
		List<Theatre> theatreList1=theatreService.getAllTheatre();
		
	}
	
	@Test(expected=ServiceException.class)
	public void getAllTheatreTestError() throws Exception
	{
		Mockito.when(theatreRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		List<Theatre> theatreList1=theatreService.getAllTheatre();
	}
	
	@Test
	public void getTheatreByIdTest() throws Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Theatre theatre=theatreService.getTheatreById(1);
		assertEquals(1, theatre.getId());	
	}
	
	@Test(expected=RecordNotFoundException.class)
	public void getTheatreByIdNotFoundTest() throws Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		Theatre theatre=theatreService.getTheatreById(1);
	
	}
	
	@Test(expected=ServiceException.class)
	public void getTheatreByIdTestError() throws Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		Theatre theatre=theatreService.getTheatreById(1);
		
	}
	
	@Test
	public void updateTheatreTest() throws Exception
	{
		Mockito.when(addressRepository.save(Mockito.any(Address.class))).thenReturn(getAddress());
		Mockito.when(locationRepository.save(Mockito.any(Location.class))).thenReturn(getLocation());
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class))).thenReturn(getTheatre());
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Theatre theatre=theatreService.updateTheatre(getTheatre());
		assertEquals(1, theatre.getId());
	}
	
	@Test(expected=ServiceException.class)
	public void updateTheatreTestError() throws JsonProcessingException, Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class))).thenThrow(Mockito.mock(DataAccessException.class));
		Theatre theatre=theatreService.updateTheatre(getTheatre());
	}
	
	@Test
	public void deleteTheatreTest() throws Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		theatreService.deleteTheatre(1);
		Mockito.verify(theatreRepository,Mockito.times(1)).deleteById(Mockito.any());
	}
	
	@Test(expected=RecordNotDeletedException.class)
	public void deleteTheatreTestError() throws Exception
	{
		theatreService.deleteTheatre(1);
	}
	
	@Test(expected=ServiceException.class)
	public void deleteTheatreTestServiceException() throws Exception
	{
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Mockito.doThrow(Mockito.mock(DataAccessException.class)).when(theatreRepository).deleteById(Mockito.anyInt());
		theatreService.deleteTheatre(1);
	}
	
	public Address getAddress() {
		Address address = new Address();
		address.setCity("chennai");
		return address;
	}
	
	public Location getLocation() {
		Location location = new Location();
		location.setLatitude("180");
		return location;
	}
	
	public Theatre getTheatre()
	{
		Theatre theatre=new Theatre();
		theatre.setId(1);
		theatre.setName("test");
		theatre.setAddress(getAddress());
		theatre.setLocation(getLocation());
	     return theatre;
	}
}