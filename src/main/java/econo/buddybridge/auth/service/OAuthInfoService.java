package econo.buddybridge.auth.service;

import econo.buddybridge.auth.OAuthProvider;
import econo.buddybridge.auth.client.OAuthApiClient;
import econo.buddybridge.auth.dto.OAuthInfoResponse;
import econo.buddybridge.auth.dto.OAuthLoginParams;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuthInfoService {

    private final Map<OAuthProvider, OAuthApiClient> clients;

    public OAuthInfoService(List<OAuthApiClient> clients) {
        this.clients = new EnumMap<>(clients.stream()
                .collect(Collectors.toMap(OAuthApiClient::getOAuthProvider, Function.identity())));
    }

    public OAuthInfoResponse getUserInfo(OAuthLoginParams params) {
        OAuthApiClient client = clients.get(params.oAuthProvider());

        String accessToken = client.getAccessToken(params);
        log.info("accessToken: {}", accessToken);

        return client.getUserInfo(accessToken);
    }

    public void logout(OAuthProvider provider) {
        OAuthApiClient client = clients.get(provider);
        client.logout();
    }
}
