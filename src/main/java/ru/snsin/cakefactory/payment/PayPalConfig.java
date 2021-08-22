package ru.snsin.cakefactory.payment;

import com.paypal.core.PayPalEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import com.paypal.core.PayPalHttpClient;

@ConfigurationProperties(prefix = "pay-pal")
@ConstructorBinding
@AllArgsConstructor
public class PayPalConfig {
    private final String clientId;
    private final String secret;

    @Bean
    @Profile("!prod")
    PayPalHttpClient sandboxClient() {
        PayPalEnvironment sandboxEnv = new PayPalEnvironment.Sandbox(clientId, secret);
        return new PayPalHttpClient(sandboxEnv);
    }

    @Bean
    @Profile("prod")
    PayPalHttpClient liveClient() {
        PayPalEnvironment liveEnv = new PayPalEnvironment.Live(clientId, secret);
        return new PayPalHttpClient(liveEnv);
    }
}
