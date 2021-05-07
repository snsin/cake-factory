package ru.snsin.cakefactory.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.address.AddressService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginWithFacebookTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AddressService addressService;

    @MockBean
    ClientRegistrationRepository registrations;

    @Test
    public void shouldUpdateAddressIfCustomerLoggedInWithFacebook() throws Exception {
        String userEmail = "test@oauth.com";
        OAuth2AuthenticationToken authenticationToken = createToken(userEmail);
        Address expectedAddress = new Address("Al1", "al2", "pc0");

        MultiValueMap<String, String> addressParams = new LinkedMultiValueMap<>();
        addressParams.set("addressLine1", expectedAddress.getAddressLine1());
        addressParams.set("addressLine2", expectedAddress.getAddressLine2());
        addressParams.set("postcode", expectedAddress.getPostcode());

        this.mockMvc.perform(post("/account/update").params(addressParams)
                .with(csrf())
                .with(authentication(authenticationToken)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));

        Address actualAddress = addressService.findByUserId(userEmail).orElseThrow();
        assertEquals(expectedAddress, actualAddress);
    }

    @Test
    void addressShouldBeFilledUpWhenUserLoggedInWithFacebook() throws Exception {
        String userEmail = "test-address@oauth.com";
        OAuth2AuthenticationToken authenticationToken = createToken(userEmail);
        Address expectedAddress = new Address("Al1", "al2", "pc0");
        addressService.save(expectedAddress, userEmail);

        MvcResult mvcResult = this.mockMvc.perform(get("/basket")
                .with(csrf())
                .with(authentication(authenticationToken)))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertNotNull(mvcResult.getModelAndView());
        Map<String, Object> model = mvcResult.getModelAndView().getModel();
        assertEquals(expectedAddress, model.get("address"));
    }

    private OAuth2AuthenticationToken createToken(String email) {
        Set<GrantedAuthority> authorities = new HashSet<>(AuthorityUtils.createAuthorityList("USER"));
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities,
                Collections.singletonMap("email", email), "email");
        return new OAuth2AuthenticationToken(oAuth2User, authorities, "login-client");
    }
}
