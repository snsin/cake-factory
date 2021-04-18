package ru.snsin.cakefactory.users;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class UserEmailFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (authentication instanceof AnonymousAuthenticationToken)
                ? null : ((UserDetails) authentication.getPrincipal()).getUsername();
        request.setAttribute("email", email);
        chain.doFilter(request, response);
    }
}