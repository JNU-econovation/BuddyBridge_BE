package econo.buddybridge.member.dto;

public record MemberSignUpResDto(
        String message
) {

    public MemberSignUpResDto(String message) {
        this.message = message;
    }
}
