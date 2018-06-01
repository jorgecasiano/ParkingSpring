package com.casiano.parking.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.casiano.parking.ParkingSpringApplication;
import com.casiano.parking.message.InfoMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParkingSpringApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingRestControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@Test
	public void verifyAllParkingList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3))).andDo(print());
	}

	@Test
	public void verifyParkingById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings/1").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("Parking Parque del Príncipe - Caceres")))
		.andDo(print());
	}


	@Test
	public void verifyParkingNotFoudnById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings/10").accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}


	@Test
	public void verifySaveParking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/parkings")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Test\",\"openingHour\":8,\"closingTime\":20,\"totalSpace\":20,\"freeSpace\":19,\"days\":[],\"latitude\":39.42,\"longitude\":-6.31}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.message", is(InfoMessage.PARKING_SUCCESSFULLY_SAVED)))
		.andDo(print());
	}


	@Test
	public void verifyUpdateParking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/parkings")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\":3,\"name\":\"New Parking\",\"openingHour\":8,\"closingTime\":20,\"totalSpace\":20,\"freeSpace\":19,\"days\":[],\"latitude\":39.42,\"longitude\":-6.31}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(3)))
		.andExpect(jsonPath("$.message", is(InfoMessage.PARKING_SUCCESSFULLY_MODIFIED)))
		.andDo(print());
	}


	@Test
	public void verifyReserveOkParking() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/parkings/2/reserve")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.message", is(InfoMessage.RESERVED_PARKING_SPACE)))
		.andDo(print());
	}


	@Test
	public void verifyLiberateKoExpextationFailed() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/parkings/1/reserve")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isExpectationFailed())
		.andDo(print());
	}


	@Test
	public void verifyFindParkingFilterOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings/filter?latitud=39.475&longitud=-6.38&distancia=1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].name", is("Parking Parque del Príncipe - Caceres")))
		.andDo(print());
	}

	@Test
	public void verifyFindParkingFilterNotFoundKo() throws Exception {
		//there are no parkings in 10 Kms
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings/filter?latitud=20&longitud=20&distancia=10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
	
	@Test
	public void verifyFindParkingFilterUrlError() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/parkings/filter?latitud=test&longitud=20&distancia=10")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}

}