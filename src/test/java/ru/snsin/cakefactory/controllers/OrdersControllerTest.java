package ru.snsin.cakefactory.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.snsin.cakefactory.components.Basket;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdersController.class)
class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("unused")
    @MockBean
    private Basket basket;

    @Test
    void shouldHandleOrdersPostRequest() throws Exception {
        MultiValueMap<String, String> form = createOrderInfoForm();
        assertFalse(form.isEmpty());
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").queryParams(form))
                .andExpect(status().is3xxRedirection());
    }

    private MultiValueMap<String, String> createOrderInfoForm() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("addressLine1", "Line One st.");
        form.add("addressLine2", "line two.st");
        form.add("postCode", "123456");
        return form;
    }
}