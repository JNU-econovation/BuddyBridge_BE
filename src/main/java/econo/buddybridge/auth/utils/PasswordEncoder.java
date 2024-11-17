package econo.buddybridge.auth.utils;

import econo.buddybridge.auth.dto.PasswordHashDto;
import econo.buddybridge.auth.exception.EncryptFailedException;
import econo.buddybridge.auth.exception.GenerateSaltFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class PasswordEncoder {
    
    /*
     * 사용자가 입력한 비밀번호, 저장된 비밀번호(해싱됨), 저장된 솔트(암호화) 를 입력 받아
     * 비밀번호의 일치 여부를 확인합니다.
     * */
    public boolean verify(String password, String storedPassword, String storedSalt) {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        String hashedKey = hashPassword(password, salt);
        return MessageDigest.isEqual(storedPassword.getBytes(), hashedKey.getBytes());
    }

    public PasswordHashDto encrypt(String password) {
        byte[] salt = generateRandomSalt();
        String hashedPassword = hashPassword(password, salt);
        String saltString = Base64.getEncoder().encodeToString(salt);

        return new PasswordHashDto(hashedPassword, saltString);
    }

    /*
     * 사용자가 입력한 비밀번호와 솔트를 이용해 해싱된 비밀번호를 반환합니다.
     * */
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

    /*
     * 솔트를 생성합니다
     * 솔트 : 암호화된 비밀번호를 해독하는데 사용되는 임의의 바이트 배열
     * */
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
