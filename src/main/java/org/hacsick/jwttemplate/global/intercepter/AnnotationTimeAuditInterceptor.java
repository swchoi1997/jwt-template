package org.hacsick.jwttemplate.global.intercepter;

import static org.hacsick.jwttemplate.global.utils.time.TimeFormatType.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.hacsick.jwttemplate.global.base.DateFormat;
import org.hacsick.jwttemplate.global.common.type.WrapperClassType;
import org.hacsick.jwttemplate.global.utils.time.TimeFormatType;

/**
 * Mybatis Update Insert 구문에 대해서 CreateAt, ModifiedAt 필드에 대해서 자동으로 데이터를 채워주는 Intercepter
 *
 * @author 최상원
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class AnnotationTimeAuditInterceptor implements Interceptor {

    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
        final Object[] args = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) args[0];
        final Optional<Object> optionalParamMap = Optional.ofNullable(args[1]);

        // Check Insert, Update Query's Target Field
        // Set ParamMap Current Time
        optionalParamMap.ifPresent(paramMap -> this.setParamMap(paramMap,
                TimeAuditingType.searchTargetAnnotations(mappedStatement.getSqlCommandType()),
                LocalDateTime.now()));

        return invocation.proceed();
    }

    private void setParamMap(Object paramMap, List<Class<? extends Annotation>> targets, LocalDateTime value) {
        final ParamMap<Object> paramMap1 = (ParamMap<Object>) paramMap;
        final Collection<Object> params = paramMap1.values();

        params.forEach(param -> this.setParam(param, targets, value));
    }

    private <T> void setParam(final Object param, List<Class<? extends Annotation>> targets, LocalDateTime value) {
        Class<?> clazz = param.getClass();
        if (this.isPrimitiveType(clazz)) {
            return;
        }
        if (this.isWrapperClassType(clazz)) {
            if (this.isCollectionType(clazz)) {
                Collection param1 = (Collection) param;
                assert param1 != null;
                this.setCollectionValue(param, param1.getClass(), targets, value);
            }
            return;
        }
        // When User Define Type
        setValue(param, clazz, targets, value);
    }

    private <T> void setCollectionValue(final Object param,
                                        final Class<T> collectionGenericType,
                                        final List<Class<? extends Annotation>> targets,
                                        final LocalDateTime value) {
        //Check Collection Generic Is Java Type
        if (this.isPrimitiveType(collectionGenericType)) {
            return;
        }
        if (this.isWrapperClassType(collectionGenericType)) {
            if (this.isCollectionType(collectionGenericType)) {
                Collection param1 = (Collection) param;
                assert param1 != null;
                param1.forEach(param2 -> this.setCollectionValue(param2, param2.getClass(), targets, value));
            }
            return;
        }
        this.setParam(param, targets, value);
    }

    private void setValue(Object param, Class<?> clazz, List<Class<? extends Annotation>> targets, LocalDateTime value) {
        List<Field> fields = Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toList());
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null) {
            fields.addAll(List.of(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }

        for (Field field : fields) {
            final List<Annotation> declaredAnnotations = Arrays.stream(field.getDeclaredAnnotations()).toList();
            if (declaredAnnotations.isEmpty()) continue;

            targets.forEach(targetAnnotation -> Arrays.stream(field.getDeclaredAnnotations())
                    .filter(annotation -> annotation.annotationType().equals(targetAnnotation))
                    .forEach(annotation -> {
                        try {
                            field.setAccessible(true);

                            if (field.getType().equals(WrapperClassType.STRING.getClazzType())) {
                                final String dateFormatName = DateFormat.class.getMethods()[0].getName();
                                Method method = annotation.annotationType().getMethod(dateFormatName);
                                assert method != null;

                                field.set(param, value.format(DATE_TIME_FORMATTER_MAP.get((TimeFormatType) method.invoke(annotation)))); // 필드 값 설정
                                return;
                            }
                            field.set(param, value); // 필드 값 설정
                        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }));
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
