package org.hacsick.jwttemplate.global.base;


import static org.hacsick.jwttemplate.global.utils.time.TimeFormPretty.DATE_HOUR_MINUTE_SECOND;
import static org.hacsick.jwttemplate.global.utils.time.TimeFormStd.YYYYMMDDHH24MISS;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.hacsick.jwttemplate.global.utils.time.TimeFormPretty;
import org.hacsick.jwttemplate.global.utils.time.TimeFormStd;

@TimeAuditing
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface DateFormat {
    TimeFormPretty pretty() default DATE_HOUR_MINUTE_SECOND;

    TimeFormStd std() default YYYYMMDDHH24MISS;

}
