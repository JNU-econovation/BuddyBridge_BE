package econo.buddybridge.survey.entity;

import econo.buddybridge.common.persistence.MongoBaseEntity;
import java.util.Map;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "surveys")
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MongoSurvey extends MongoBaseEntity {

    @Id
    private String id;

    @Field("content")
    private Map<String, Object> content;

    public static MongoSurvey from(Map<String, Object> content) {
        return MongoSurvey.builder()
                .id(UUID.randomUUID().toString())
                .content(content)
                .build();
    }
}
