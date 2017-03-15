package pl.tsieciechowicz.db.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by tsieciechowicz on 11.03.2017.
 */

@Data //compiler annotation processing require
@Builder //design pattern for unit tests
@AllArgsConstructor
@NoArgsConstructor
@Document //unnecessary
public class User {

    @Id
    private String id;
    private String username;
}
