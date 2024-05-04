package org.hacsick.jwttemplate.global.base;

import static org.hacsick.jwttemplate.global.utils.time.TimeFormatType.DATE_HOUR_MINUTE_SECOND;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hacsick.jwttemplate.global.utils.time.TimeFormatType;
import org.springframework.data.annotation.CreatedDate;

@TimeAuditing
@CreatedDate
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MybatisCreateDate {
    TimeFormatType format() default DATE_HOUR_MINUTE_SECOND;
}
