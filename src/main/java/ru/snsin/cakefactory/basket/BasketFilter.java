package ru.snsin.cakefactory.basket;

import org.springframework.stereotype.Component;
import ru.snsin.cakefactory.components.Basket;

import javax.servlet.*;
import java.io.IOException;

@Component
public class BasketFilter implements Filter {

    private final Basket basket;

    public BasketFilter(Basket basket) {
        this.basket = basket;
    }


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setAttribute("basketItemsCount", basket.countItems());
        chain.doFilter(request, response);
    }
}
