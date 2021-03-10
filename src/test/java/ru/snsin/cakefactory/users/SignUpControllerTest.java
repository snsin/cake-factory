package ru.snsin.cakefactory.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
class SignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SessionSignUp sessionSignUp;

    @Test
    void signUpPageShouldExist() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk());
    }

    @Test
    void userShouldNotSignUpWhenParametersInvalid() throws Exception {
        mockMvc.perform(post("/signup"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userAndAddressShouldStoreOnSignup() throws Exception {
        Account account = new Account("test@example.com", "pass");
        Address address = new Address("al1", "al2", "code");
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("email", account.getEmail());
        requestParams.add("password", account.getPassword());
        requestParams.add("addressLine1", address.getAddressLine1());
        requestParams.add("addressLine2", address.getAddressLine2());
        requestParams.add("postcode", address.getPostcode());

        mockMvc.perform(post("/signup").params(requestParams))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        Mockito.verify(sessionSignUp).signUp(account, address);

    }

}