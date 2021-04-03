package ru.snsin.cakefactory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.snsin.cakefactory.users.Account;
import ru.snsin.cakefactory.users.AccountService;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2Y;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountService accountService;

    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((authorize) -> authorize
                        .antMatchers("/account/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password"));
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder($2Y);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        return (email) -> accountService.findByEmail(email)
                .map(this::mapAccountToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private UserDetails mapAccountToUserDetails(Account account) {
        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(Account.ROLE_NAME)
                .build();
    }
}
