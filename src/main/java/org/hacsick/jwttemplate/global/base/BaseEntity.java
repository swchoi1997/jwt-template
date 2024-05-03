package org.hacsick.jwttemplate.global.base;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public abstract class BaseEntity {

    @MybatisCreateDate
    protected LocalDateTime createdAt = null;

    @MyBatisLastModifiedDate
    protected LocalDateTime modifiedAt = null;

}
