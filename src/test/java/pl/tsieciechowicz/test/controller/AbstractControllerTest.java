package pl.tsieciechowicz.test.controller;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by tsieciechowicz on 12.03.2017.
 */

public abstract class AbstractControllerTest {

    protected Logger logger;

    public AbstractControllerTest() {
        logger = LoggerFactory.getLogger(getClass().getName());
        logger.info("{} setup completed", getClass().getName());
    }
}
