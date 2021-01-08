package ru.snsin.cakefactory.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.snsin.cakefactory.services.BasketService;
import ru.snsin.cakefactory.services.CakeCatalogService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BasketController.class)
class BasketControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CakeCatalogService cakeCatalog;

    @MockBean
    BasketService basketService;


    @Test
    void addItem() throws Exception {
        final String expectedItemName = "Victoria Sponge";

        mockMvc.perform(post("/basket")
                .queryParam("cakeName", expectedItemName))
                .andExpect(status().is3xxRedirection());
    }
}