package econo.buddybridge.auth.jwt.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import econo.buddybridge.auth.jwt.RefreshToken;
import econo.buddybridge.config.RedisConfig;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;

@DataRedisTest
@Import(RedisConfig.class)  // keyspace events를 구독하기 위해 RedisConfig를 import
class TokenRepositoryTest {

    @Autowired
    TokenRepository tokenRepository;

    @AfterEach
    void tearDown() {
        tokenRepository.deleteAll();
    }

    @Test
    void 레포지토리_생성() {
        assertNotNull(tokenRepository);
    }

    @Test
    void 리프레시토큰_저장() {
        // given
        String value = UUID.randomUUID().toString();
        RefreshToken token = new RefreshToken(1L, value, 10L);

        // when
        RefreshToken savedToken = tokenRepository.save(token);

        // then
        assertEquals(token, savedToken);
    }

    @Test
    void 리프레시토큰_조회() {
        // given
        String value = UUID.randomUUID().toString();
        RefreshToken token = new RefreshToken(1L, value, 10L);

        // when
        tokenRepository.save(token);
        RefreshToken foundToken = tokenRepository.findById(1L).orElse(null);

        boolean exists = tokenRepository.existsByToken(value);

        // then
        assertThat(token.getToken()).isEqualTo(foundToken.getToken());
        assertThat(exists).isTrue();
    }

    @Test
    void 리프레시토큰_삭제() {
        // given
        String value = UUID.randomUUID().toString();
        RefreshToken token = new RefreshToken(1L, value, 10L);

        // when
        tokenRepository.save(token);
        tokenRepository.delete(token);

        // then
        assertFalse(tokenRepository.findById(1L).isPresent());
    }
}
