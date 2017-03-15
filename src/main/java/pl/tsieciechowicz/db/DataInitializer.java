package pl.tsieciechowicz.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import pl.tsieciechowicz.db.model.User;
import pl.tsieciechowicz.db.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by tsieciechowicz on 11.03.2017.
 */

@Component
@Profile({"default", "test"})
public class DataInitializer implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.deleteAll();
        logger.info("user repository cleared.");
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        Resource usersResource = new ClassPathResource("data/users.json");
        List<User> users = objectMapper.readValue
                (usersResource.getInputStream(), new TypeReference<List<User>>(){});
        users.forEach(user -> userRepository.save(user));
        logger.info("data initializing completed.");
    }

}
