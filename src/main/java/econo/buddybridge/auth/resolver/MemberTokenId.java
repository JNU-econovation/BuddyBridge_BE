package econo.buddybridge.auth.resolver;

import econo.buddybridge.member.entity.Role;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberTokenId {
    boolean required() default true;
    Role[] allowedRoles() default {Role.USER, Role.ADMIN};
}
