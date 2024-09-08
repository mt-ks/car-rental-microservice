package com.mtks.vehicle.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtks.vehicle.models.Vehicle;
import com.mtks.vehicle.repository.VehicleRepository;
import com.mtks.vehicle.utils.VehicleFaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@TestPropertySource(locations = "classpath:application-integration-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)

public class VehicleControllerTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehiclesController vehiclesController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(vehicleRepository);
        Assertions.assertNotNull(vehiclesController);
    }


    @Test
    public void get_vehicles_list() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/vehicles"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void create_vehicle() throws Exception{
        Vehicle vehicle = (new VehicleFaker()).getRandomVehicle();


        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", StandardCharsets.UTF_8);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/vehicles")
                        .content(objectMapper.writeValueAsString(vehicle))
                        .locale(Locale.CANADA)
                        .accept(MEDIA_TYPE_JSON_UTF8)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andDo(print());
    }

}
