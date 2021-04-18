package ru.snsin.cakefactory.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.snsin.cakefactory.components.Basket;
import ru.snsin.cakefactory.domain.BasketItem;
import ru.snsin.cakefactory.domain.CakeItem;
import ru.snsin.cakefactory.domain.OrderPlacedEvent;
import ru.snsin.cakefactory.users.Address;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrdersControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private Basket basket;

    @MockBean
    private ApplicationEventPublisher publisher;

    private String line1;
    private String line2;
    private String code;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrdersController(basket, publisher)).build();
        line1 = "Line One st.";
        line2 = "line two.st";
        code = "123456";
    }

    @Test
    void shouldHandleOrdersPostRequest() throws Exception {
        MultiValueMap<String, String> form = createOrderInfoForm();
        assertFalse(form.isEmpty());
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").queryParams(form))
                .andExpect(status().is3xxRedirection());
        Mockito.verify(basket).clearBasket();
    }

    @Test
    void shouldPublishEvent() throws Exception {
        MultiValueMap<String, String> form = createOrderInfoForm();
        final List<BasketItem> expectedBasket = Collections.singletonList(
                new BasketItem(new CakeItem("ts", "Test Cake", BigDecimal.TEN), 1));
        final Address expectedAddress = new Address(line1, line2, code);

        Mockito.when(basket.getBasketItems()).thenReturn(expectedBasket);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").queryParams(form))
                .andExpect(status().is3xxRedirection());

        Mockito.verify(publisher)
                .publishEvent(new OrderPlacedEvent(expectedAddress, expectedBasket));
    }


    private MultiValueMap<String, String> createOrderInfoForm() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("addressLine1", line1);
        form.add("addressLine2", line2);
        form.add("postcode", code);
        return form;
    }
}