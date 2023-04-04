package sura.app.sampleone;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import sura.app.domain.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SampleOneHandler {

    @Autowired
    private SampleOne hello;

    @FunctionName("sampleone")
    public HttpResponseMessage execute(
        @HttpTrigger(name = "request", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<User>> request, ExecutionContext context) {
        User user = request.getBody()
                           .filter(u -> u.getName() != null)
                           .orElseGet(() -> User.builder().name(request.getQueryParameters().getOrDefault("name", "world")).build());
        context.getLogger().info("Greeting user name: " + user.getName());
        return request.createResponseBuilder(HttpStatus.OK)
                      .body(hello.apply(user))
                      .header("Content-Type", "application/json")
                      .build();
    }
}
