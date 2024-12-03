package econo.buddybridge.common.persistence;

import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public abstract class SoftDeletableEntity extends BaseEntity {

    private boolean deleted = false;

    private LocalDateTime deletedAt;

    public void delete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
