package ru.snsin.cakefactory.users;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.address.Address;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SignUpControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SignUpComponent signUpComponent;

    @Test
    void signUpPageShouldExist() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk());
    }

    @Test
    void userShouldNotSignUpWhenParametersInvalid() throws Exception {
        mockMvc.perform(post("/signup").with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void userAndAddressShouldStoreOnSignup() throws Exception {
        Account account = new Account("test@example.com", "pass");
        Address address = new Address("al1", "al2", "code");
        MultiValueMap<String, String> requestParams = createRequestParams(account, address);

        mockMvc.perform(post("/signup").with(csrf()).params(requestParams))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        Mockito.verify(signUpComponent).signUp(account, address);
    }

    @Test
    void shouldRedirectToLoginPageIfEmailIsTaken() throws Exception {
        Account account = new Account("will-be-redirected@example.com", "pass");
        Address address = new Address("al1", "al2", "code");
        MultiValueMap<String, String> requestParams = createRequestParams(account, address);

        Mockito.when(signUpComponent.accountExists(account.getEmail())).thenReturn(true);
        mockMvc.perform(post("/signup").with(csrf()).params(requestParams))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/login"));

        Mockito.verify(signUpComponent, Mockito.never()).signUp(account, address);
    }

    private MultiValueMap<String, String> createRequestParams(Account account, Address address) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("email", account.getEmail());
        requestParams.add("password", account.getPassword());
        requestParams.add("addressLine1", address.getAddressLine1());
        requestParams.add("addressLine2", address.getAddressLine2());
        requestParams.add("postcode", address.getPostcode());
        return requestParams;
    }

}