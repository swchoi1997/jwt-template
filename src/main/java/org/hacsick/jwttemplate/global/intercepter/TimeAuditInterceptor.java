package org.hacsick.jwttemplate.global.intercepter;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.hacsick.jwttemplate.global.common.type.WrapperClassType;

/**
 * Mybatis Update Insert 구문에 대해서 CreateAt, ModifiedAt 필드에 대해서 자동으로 데이터를 채워주는 Intercepter
 *
 * @author 최상원
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class TimeAuditInterceptor implements Interceptor {

    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) args[0];
        final Object paramMap = args[1];

        // Check Insert, Update Query's Target Field
        // Set ParamMap Current Time
        if (paramMap != null) {
            final List<String> targetFields = TimeAuditingType.searchTargetFields(mappedStatement.getSqlCommandType());
            this.setParamMap(paramMap, targetFields, LocalDateTime.now());
        }

        return invocation.proceed();
    }

    private void setParamMap(Object paramMap, List<String> fieldNames, LocalDateTime value) {
        final ParamMap<Object> paramMap1 = (ParamMap<Object>) paramMap;
        final Collection<Object> params = paramMap1.values();

        params.forEach(param -> this.setParam(param, fieldNames, value));
    }

    private <T> void setParam(final Object param, List<String> fieldNames, LocalDateTime value) {
        Class<?> clazz = param.getClass();
        if (this.isPrimitiveType(clazz)) {
            return;
        }
        if (this.isWrapperClassType(clazz)) {
            if (this.isCollectionType(clazz)) {
                Collection param1 = (Collection) param;
                assert param1 != null;
                this.setCollectionValue(param, param1.getClass(), fieldNames, value);
            }
            return;
        }
        // When User Define Type
        setValue(param, clazz, fieldNames, value);
    }

    private <T> void setCollectionValue(final Object param,
                                        final Class<T> collectionGenericType,
                                        final List<String> fieldNames,
                                        final LocalDateTime value) {
        //Check Collection Generic Is Java Type
        if (this.isPrimitiveType(collectionGenericType)) {
            return;
        }
        if (this.isWrapperClassType(collectionGenericType)) {
            if (this.isCollectionType(collectionGenericType)) {
                Collection param1 = (Collection) param;
                assert param1 != null;
                param1.forEach(param2 ->
                        this.setCollectionValue(param2, param2.getClass(), fieldNames, value));
            }
            return;
        }

        this.setParam(param, fieldNames, value);
    }

    private void setValue(Object param, Class<?> clazz, List<String> fieldNames, LocalDateTime value) {
        for (String fieldName : fieldNames) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                declaredField.set(param, value);

            } catch (NoSuchFieldException e) {
                // Finally Check Is Object Class
                if (clazz.getSuperclass() != null) {
                    this.setValue(param, param.getClass().getSuperclass(), fieldNames, value);
                } else {
                    throw new IllegalArgumentException("Field " + fieldName + " not found in the class hierarchy");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean isPrimitiveType(final Class<?> clazz) {
        return clazz.isPrimitive();
    }

    private boolean isWrapperClassType(final Class<?> clazz) {
        return WrapperClassType.isWrapperType(clazz);
    }

    private boolean isCollectionType(final Class<?> clazz) {
        return Collection.class.isAssignableFrom(clazz);
    }

    @Override
    public Object plugin(final Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(final Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
