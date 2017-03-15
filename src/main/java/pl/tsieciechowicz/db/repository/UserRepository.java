package pl.tsieciechowicz.db.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.tsieciechowicz.db.model.User;

/**
 * Created by tsieciechowicz on 11.03.2017.
 */

@Repository
public interface UserRepository extends MongoRepository<User, String>{
}
