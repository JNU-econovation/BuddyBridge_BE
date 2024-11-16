package econo.buddybridge.auth.utils;

import econo.buddybridge.auth.dto.PasswordHashDto;
import econo.buddybridge.auth.exception.EncryptFailedException;
import econo.buddybridge.auth.exception.GenerateSaltFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class PasswordEncoder {

    public PasswordHashDto encrypt(String password) {
        byte[] salt = generateRandomSalt();
        String hashedPassword = hashPassword(password, salt);
        String saltString = Base64.getEncoder().encodeToString(salt);

        return new PasswordHashDto(hashedPassword, saltString);
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("비밀번호 암호화에 실패했습니다.", e); // 서버 에러는 로그로 처리
            throw EncryptFailedException.EXCEPTION;
        }
    }

    private byte[] generateRandomSalt() {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        } catch (Exception e) {
            log.error("솔트 생성에 실패했습니다.", e); // 서버 에러는 로그로 처리
            throw GenerateSaltFailedException.EXCEPTION;
        }
    }
}
