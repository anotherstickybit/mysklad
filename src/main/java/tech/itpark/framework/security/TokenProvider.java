package tech.itpark.framework.security;


import org.springframework.security.crypto.codec.Hex;

import java.security.SecureRandom;

public class TokenProvider {
    private final SecureRandom random = new SecureRandom();
    public String createToken() {
        final var bytes = new byte[128];
        random.nextBytes(bytes);
        return String.valueOf(Hex.encode(bytes));
    }
}
