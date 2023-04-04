package sura.app.domain.user.gateway;

import reactor.core.publisher.Mono;
import sura.app.domain.user.User;

public interface UserGateway {
    Mono<User> findById(String id);
}
