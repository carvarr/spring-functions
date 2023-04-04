package sura.app.sampleone;

import org.springframework.stereotype.Component;

import sura.app.domain.greeting.Greeting;
import sura.app.domain.user.User;

import java.util.function.Function;

@Component
public class SampleOne implements Function<User, Greeting> {

    @Override
    public Greeting apply(User user) {
        return Greeting.builder().message("Hello from sample one, " + user.getName() + "!\n").build();
    }
}
