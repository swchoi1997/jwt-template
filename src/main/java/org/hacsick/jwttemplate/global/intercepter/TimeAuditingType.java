package org.hacsick.jwttemplate.global.intercepter;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.ibatis.mapping.SqlCommandType;
import org.hacsick.jwttemplate.global.base.MyBatisLastModifiedDate;
import org.hacsick.jwttemplate.global.base.MybatisCreateDate;

public enum TimeAuditingType {

    INSERT_TARGET_FIELD(SqlCommandType.INSERT, List.of("createdAt", "modifiedAt"), List.of(MybatisCreateDate.class, MyBatisLastModifiedDate.class)),
    UPDATE_TARGET_FIELD(SqlCommandType.UPDATE, List.of("modifiedAt"), List.of(MybatisCreateDate.class, MyBatisLastModifiedDate.class)),
    ;

    private static final Map<SqlCommandType, List<String>> SQL_COMMAND_TYPE_TARGET_FIELD = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(TimeAuditingType::getSqlCommandType, TimeAuditingType::getTargetFields))
    );

    private static final Map<SqlCommandType, List<Class<? extends Annotation>>> SQL_COMMAND_TYPE_TARGET_ANNOTATION = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(TimeAuditingType::getSqlCommandType, TimeAuditingType::getAnnotationClasses))
    );



    public static List<String> searchTargetFields(final SqlCommandType sqlCommandType) {
        return Optional.ofNullable(SQL_COMMAND_TYPE_TARGET_FIELD.get(sqlCommandType))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<Class<? extends Annotation>> searchTargetAnnotations(final SqlCommandType sqlCommandType) {
        return Optional.ofNullable(SQL_COMMAND_TYPE_TARGET_ANNOTATION.get(sqlCommandType))
                .orElseThrow(IllegalArgumentException::new);
    }

    public static boolean isTargetQuery(final SqlCommandType sqlCommandType) {
        return SQL_COMMAND_TYPE_TARGET_FIELD.keySet().stream().anyMatch(sqlType -> sqlType.equals(sqlCommandType));
    }

    private final SqlCommandType sqlCommandType;
    private final List<String> targetFields;
    private final List<Class<? extends Annotation>> annotationClasses;

    TimeAuditingType(final SqlCommandType sqlCommandType,
                     final List<String> targetFields,
                     final List<Class<? extends Annotation>> annotationClasses) {
        this.sqlCommandType = sqlCommandType;
        this.targetFields = targetFields;
        this.annotationClasses = annotationClasses;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public List<String> getTargetFields() {
        return targetFields;
    }

    public List<Class<? extends Annotation>> getAnnotationClasses() {
        return annotationClasses;
    }
}
