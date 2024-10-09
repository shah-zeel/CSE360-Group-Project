package backend;

import java.time.Instant;

public class ResetRequest {
    private String email;
    private String oneTimePassword;
    private Instant expirationTime;

    public ResetRequest(String email, String oneTimePassword, Instant expirationTime) {
        this.email = email;
        this.oneTimePassword = oneTimePassword;
        this.expirationTime = expirationTime;
    }

    public String getEmail() {
        return email;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationTime);
    }
}