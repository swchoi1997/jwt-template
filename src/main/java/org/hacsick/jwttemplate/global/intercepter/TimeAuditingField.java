package org.hacsick.jwttemplate.global.intercepter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.ibatis.mapping.SqlCommandType;

public enum TimeAuditingField {

    INSERT_TARGET_FIELD(SqlCommandType.INSERT, List.of("createdAt", "modifiedAt")),
    UPDATE_TARGET_FIELD(SqlCommandType.UPDATE, List.of("modifiedAt")),
    ;

    private static final Map<SqlCommandType, List<String>> SQL_COMMAND_TYPE_TARGET_FIELD = Collections.unmodifiableMap(
            Stream.of(values())
                    .collect(Collectors.toMap(TimeAuditingField::getSqlCommandType, TimeAuditingField::getTargetFields))
    );

    public static List<String> searchTargetFields(final SqlCommandType sqlCommandType) {
        return Optional.ofNullable(SQL_COMMAND_TYPE_TARGET_FIELD.get(sqlCommandType))
                .orElseThrow(IllegalArgumentException::new);
    }

    private final SqlCommandType sqlCommandType;

    private final List<String> targetFields;

    TimeAuditingField(final SqlCommandType sqlCommandType, final List<String> targetFields) {
        this.sqlCommandType = sqlCommandType;
        this.targetFields = targetFields;
    }

    public SqlCommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public List<String> getTargetFields() {
        return targetFields;
    }
}
