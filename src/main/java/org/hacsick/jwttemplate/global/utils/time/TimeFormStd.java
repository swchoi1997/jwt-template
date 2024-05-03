package org.hacsick.jwttemplate.global.utils.time;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TimeFormStd {

    NONE("None", 0),

    YYYYMMDDHH24MISSNANO("yyyyMMddHHmmssSSSSSSSSS", 23),
    YYYYMMDDHH24MISSMICRO("yyyyMMddHHmmssSSSSSS", 20),
    YYYYMMDDHH24MISSMILLI("yyyyMMddHHmmssSSS", 17),
    YYYYMMDDHH24MISS("yyyyMMddHHmmss", 14),
    YYYYMMDDHH24MI("yyyyMMddHHmm", 12),
    YYYYMMDDHH24("yyyyMMddHH", 10),
    YYYYMMDD("yyyyMMdd" ,8),
    YYYYMM("yyyyMM", 6),
    YYYY("yyyy", 4);

    private static final Map<Integer, TimeFormStd> TIME_FORM_STD = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(TimeFormStd::getLen, Function.identity())));

    private final String form;
    private final int len;

    TimeFormStd(String form, int len) {
        this.form = form;
        this.len = len;
    }

    public static TimeFormStd getForm(Integer length) {
        return TIME_FORM_STD.get(length);
    }

    public String getForm() {
        return form;
    }

    public int getLen() {
        return len;
    }
}
