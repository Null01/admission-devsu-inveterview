package co.com.devsu.bank.client.service.common;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ICommonService<R extends JpaRepository<E, I>, E, I> {

    Optional<E> save(E object);

    Boolean delete(I id);

    Optional<E> getById(I id);

    List<E> getAll();

    default E merge(E source, E target) {
        try {
            final List<String> IGNORE_FIELDS = Arrays.asList("serialVersionUID", "createdDate", "modifiedDate");
            Class<?> sourceClass = Class.forName(source.getClass().getName());
            Class<?> targetClass = Class.forName(target.getClass().getName());
            for (Field field : targetClass.getDeclaredFields()) {
                field.setAccessible(true);
                Object object = field.get(target);
                if (Optional.ofNullable(object).filter($ -> !IGNORE_FIELDS.contains(field.getName())).isPresent()) {
                    Field sourceField = sourceClass.getDeclaredField(field.getName());
                    sourceField.setAccessible(true);
                    sourceField.set(source, object);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return source;
    }

    default Optional<I> getId(E object) {
        try {
            Class<?> objectClass = Class.forName(object.getClass().getName());
            Field field = objectClass.getDeclaredField("id");
            field.setAccessible(true);
            return (Optional<I>) Optional.of(field.get(object));
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NullPointerException e) {
        }
        return Optional.empty();
    }
}
