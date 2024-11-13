package econo.buddybridge.auth.jwt.repository;

import econo.buddybridge.auth.jwt.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<RefreshToken, Long> {

    boolean existsByToken(String refreshToken);
}
