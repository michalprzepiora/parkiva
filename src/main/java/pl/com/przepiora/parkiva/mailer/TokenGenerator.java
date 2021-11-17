package pl.com.przepiora.parkiva.mailer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@PropertySource("classpath:email.properties")
public class TokenGenerator {

    @Value("${parkiva.mailer.tokengenerator}")
    private int LENGHT;
    private final String CHARACTERS = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    public String generate() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LENGHT; i++) {
            builder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }
}
