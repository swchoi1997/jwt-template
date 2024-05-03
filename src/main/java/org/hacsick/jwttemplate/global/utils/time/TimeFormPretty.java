package org.hacsick.jwttemplate.global.utils.time;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TimeFormPretty {
    HOUR_MINUTE("hh:mm"),
    HOUR_MINUTE_SECOND("hh:mm:ss"),
    HOUR_MINUTE_SECOND_MILLISECOND("hh:mm:ss.SSS"),
    TWELVE_HOUR_CLOCK("hh:mm a"),
    TWENTY_FOUR_HOUR_CLOCK("HH:mm"),
    DATE("yyyy-MM-dd"),
    DATE_HOUR_MINUTE("yyyy-MM-dd hh:mm"),
    DATE_HOUR_MINUTE_SECOND("yyyy-MM-dd hh:mm:ss"),
    DATE_HOUR_MINUTE_SECOND_MILLISECOND("yyyy-MM-dd hh:mm:ss.SSS"),
    ISO_INSTANT("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    public static final Map<TimeFormPretty, String> PRETTY_TIME_FORM = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Function.identity(), TimeFormPretty::getForm))
    );

    public static String getDefaultForm() {
        return TimeFormPretty.PRETTY_TIME_FORM.get(DATE_HOUR_MINUTE_SECOND);
    }


    private final String form;

    TimeFormPretty(String format) {
        this.form = format;
    }

    public String getForm() {
        return form;
    }
}
