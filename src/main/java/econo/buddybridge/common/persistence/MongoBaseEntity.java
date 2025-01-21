package econo.buddybridge.common.persistence;

import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
public abstract class MongoBaseEntity {

    @Field("created_at")
    private String createdAt;

    @Field("modified_at")
    private String modifiedAt;

    protected MongoBaseEntity() {
        this.createdAt = LocalDateTime.now().toString();
        this.modifiedAt = LocalDateTime.now().toString();
    }
}
