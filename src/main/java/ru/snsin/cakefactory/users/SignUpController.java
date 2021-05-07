package ru.snsin.cakefactory.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ru.snsin.cakefactory.account.Account;
import ru.snsin.cakefactory.address.Address;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
@RequestMapping("/signup")
@Slf4j
public class SignUpController {

    private final SignUp signUp;

    public SignUpController(SignUp signUp) {
        this.signUp = signUp;
    }

    @GetMapping
    public ModelAndView signup() {
        Map<String, Object> pageData = Collections.singletonMap("address", Address.EMPTY_ADDRESS);
        return new ModelAndView("signup", pageData);
    }

    @PostMapping
    public RedirectView signUpNewUser(@Valid Account account, @Valid Address address) {
        if (signUp.accountExists(account.getEmail())) {
            return new RedirectView("/login");
        }
        signUp.signUp(account, address);
        log.info("New user signed up with email = {}", account.getEmail());
        log.info("and address = {}", address);
        setSecurityContext(account);
        return new RedirectView("/");
    }

    private void setSecurityContext(Account account) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserDetails principal = User.withUsername(account.getEmail())
                .password("").roles(Account.ROLE_NAME).build();
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

    }
}
