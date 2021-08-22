package ru.snsin.cakefactory.payment;

import com.paypal.orders.Order;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.domain.BasketItem;

import java.net.URI;
import java.util.List;

public interface PaymentService {
    Order createOrder(List<BasketItem> items, Address address, URI returnUri);
}
