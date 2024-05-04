package org.hacsick.jwttemplate.global.utils.time;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TimeFormatType {
    HOUR_MINUTE("hh:mm"),
    HOUR_MINUTE_SECOND("hh:mm:ss"),
    HOUR_MINUTE_SECOND_MILLISECOND("hh:mm:ss.SSS"),
    TWELVE_HOUR_CLOCK("hh:mm a"),
    TWENTY_FOUR_HOUR_CLOCK("HH:mm"),
    DATE("yyyy-MM-dd"),
    DATE_HOUR_MINUTE("yyyy-MM-dd hh:mm"),
    DATE_HOUR_MINUTE_SECOND("yyyy-MM-dd hh:mm:ss"),
    DATE_HOUR_MINUTE_SECOND_MILLISECOND("yyyy-MM-dd hh:mm:ss.SSS"),
    ISO_INSTANT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"),
    YYYYMMDDHH24MISSNANO("yyyyMMddHHmmssSSSSSSSSS"),
    YYYYMMDDHH24MISSMICRO("yyyyMMddHHmmssSSSSSS"),
    YYYYMMDDHH24MISSMILLI("yyyyMMddHHmmssSSS"),
    YYYYMMDDHH24MISS("yyyyMMddHHmmss"),
    YYYYMMDDHH24MI("yyyyMMddHHmm"),
    YYYYMMDDHH24("yyyyMMddHH"),
    YYYYMMDD("yyyyMMdd"),
    YYYYMM("yyyyMM"),
    YYYY("yyyy");

    public static final Map<TimeFormatType, DateTimeFormatter> DATE_TIME_FORMATTER_MAP = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(Function.identity(), type -> DateTimeFormatter.ofPattern(type.getForm())))
    );

    private final String form;

    TimeFormatType(String form) {
        this.form = form;
    }

    public String getForm() {
        return form;
    }


}
