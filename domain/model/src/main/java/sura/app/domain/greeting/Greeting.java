package sura.app.domain.greeting;

import lombok.*;

@Getter
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Greeting {
    private final String message;
}
