package pl.tsieciechowicz.db.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by tsieciechowicz on 12.03.2017.
 */
@Repository
public class UserRepositoryExt {

    private Logger logger = LoggerFactory.getLogger(UserRepositoryExt.class);

    @Autowired
    private MongoTemplate template;
}
