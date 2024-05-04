package org.hacsick.jwttemplate.global.base;


import static org.hacsick.jwttemplate.global.utils.time.TimeFormatType.DATE_HOUR_MINUTE_SECOND;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hacsick.jwttemplate.global.utils.time.TimeFormatType;

@TimeAuditing
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface DateFormat {

    TimeFormatType format() default DATE_HOUR_MINUTE_SECOND;

}
