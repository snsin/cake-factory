package ru.snsin.cakefactory.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.snsin.cakefactory.account.AccountController;
import ru.snsin.cakefactory.account.AccountService;
import ru.snsin.cakefactory.components.Basket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @MockBean
    AddressService addressService;

    @MockBean
    Basket basket;

    @Test
    void accountPageShouldNotAvailableForAnonymous() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    void accountPageShouldBeAvailableToAuthenticatedUser() throws Exception {
        mockMvc.perform(get("/account"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void testUpdate() throws Exception {
        assertTrue(true);
        mockMvc.perform(post("/account/update").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        MultiValueMap<String, String> addressParams = new LinkedMultiValueMap<>();
        addressParams.set("addressLine1", "Al1");
        addressParams.set("addressLine2", "");
        addressParams.set("postcode", "");

        mockMvc.perform(post("/account/update").params(addressParams).with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

}