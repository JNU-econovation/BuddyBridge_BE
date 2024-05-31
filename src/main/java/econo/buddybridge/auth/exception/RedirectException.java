package econo.buddybridge.auth.exception;

import lombok.Getter;

@Getter
public class RedirectException extends Exception {
    private final String location;

    public RedirectException(String location) {
        super("302 Found: " + location);
        this.location = location;
    }
}
