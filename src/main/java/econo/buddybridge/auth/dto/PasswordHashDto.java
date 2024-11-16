package econo.buddybridge.auth.dto;

public record PasswordHashDto(
        String hashedPassword,
        String salt
) {
}
