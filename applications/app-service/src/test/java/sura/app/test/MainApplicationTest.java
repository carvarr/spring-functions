package sura.app.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sura.app.MainApplication;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MainApplicationTest {

    @Test
    public void shouldStartApp() {
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

            mocked.when(() -> { SpringApplication.run(MainApplication.class); })
                    .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            MainApplication.main(new String[] {});

            mocked.verify(() -> { SpringApplication.run(MainApplication.class); });

        }
    }
}
