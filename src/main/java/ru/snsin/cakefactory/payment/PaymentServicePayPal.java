package ru.snsin.cakefactory.payment;

import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.snsin.cakefactory.address.Address;
import ru.snsin.cakefactory.domain.BasketItem;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PaymentServicePayPal implements PaymentService {

    private final PayPalHttpClient payPalClient;


    @Override
    public Order createOrder(List<BasketItem> items, Address address, URI returnUri) {
        OrderRequest request = new OrderRequest();
        request.checkoutPaymentIntent("CAPTURE");
        request.purchaseUnits(createPurchases(items));
        request.applicationContext(new ApplicationContext().returnUrl(returnUri.toASCIIString()));
        try {
            HttpResponse<Order> orderResp = payPalClient
                    .execute(new OrdersCreateRequest().requestBody(request));
            return orderResp.result();
        } catch (IOException ioException) {
            log.error("Error create exception", ioException);
            throw new HttpClientErrorException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private List<PurchaseUnitRequest> createPurchases(final List<BasketItem> items) {
        return Objects.requireNonNull(items).stream()
                .map(item -> {
                    BigDecimal itemCost = item.getCake().getPrice()
                            .multiply(BigDecimal.valueOf(item.getCount()));
                    return new PurchaseUnitRequest().amountWithBreakdown(
                            new AmountWithBreakdown()
                                    .currencyCode("RU")
                                    .value(itemCost.toPlainString())
                    );
                })
                .collect(Collectors.toUnmodifiableList());
    }
}
