package co.com.devsu.bank.account.controllers.common;

import co.com.devsu.bank.account.constants.MessageResponseConstant;
import co.com.devsu.bank.account.controllers.dto.technical.BaseResponse;
import co.com.devsu.bank.account.controllers.dto.technical.CommonRequest;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.service.common.ICommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class CommonRestController<R extends JpaRepository<E, ID>, E, D extends CommonRequest, ID> {

    @Autowired
    private ICommonService<R, E, ID> service;

    /***
     * Generic method for find resource by id.
     * @param id
     * @return
     * @throws BusinessException
     */
    @GetMapping("/v1/{id}")
    public ResponseEntity<?> findResourceById(@PathVariable ID id) throws BusinessException {
        Optional<E> entity = service.getById(id);
        if (entity.isPresent())
            return ResponseEntity.ok(BaseResponse.builder(entity.get()).build());
        throw new BusinessException(MessageResponseConstant.EXCEPTION_NOT_FOUND_DATA);
    }

    /***
     * Generic method for find all resources.
     * @return
     */
    @GetMapping("/v1")
    public ResponseEntity<?> findAllResources() {
        List<E> entities = service.getAll();
        return ResponseEntity.ok(BaseResponse.builder(entities).build());
    }

    /***
     * Generic method for save resource.
     * @param request
     * @return
     * @throws BusinessException
     */
    @PostMapping("/v1")
    public ResponseEntity<?> saveResource(@Valid @RequestBody D request) throws BusinessException {
        E entity = this.mapDtoToEntity(request);
        if (entity != null) {
            Optional<E> entityNew = service.save(entity);
            return ResponseEntity.ok(BaseResponse.builder(entityNew).build());
        }
        throw new BusinessException(MessageResponseConstant.EXCEPTION_NOT_WAS_POSIBLE_CREATE_OBJECT);
    }

    /***
     * Generic method for update fields of an entity.
     * @param id
     * @param request
     * @return
     */
    @PutMapping("/v1/{id}")
    public ResponseEntity<?> updateResource(@PathVariable ID id, @RequestBody D request) {
        E entity = this.mapDtoToEntity(request);
        //request.setId(id);
        Optional<E> entityUpdate = service.save(entity);
        return ResponseEntity.ok(BaseResponse.builder(entityUpdate).build());
    }

    /***
     * Generic method for delete resource by id
     * @param id
     * @return
     */
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteResourceById(@PathVariable ID id) {
        Boolean state = service.delete(id);
        Map<String, Object> outcome = new HashMap<>();
        outcome.put("removed", state);
        return ResponseEntity.ok(BaseResponse.builder(outcome).build());
    }

    private E mapDtoToEntity(D request) {
        E entity = null;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length >= 2) {
                if (typeArguments[1] instanceof Class) {
                    Class<E> entityType = ((Class<E>) typeArguments[1]);
                    try {
                        entity = entityType.getDeclaredConstructor().newInstance();
                        Field[] dtoFields = request.getClass().getDeclaredFields();
                        Field[] entityFields = entityType.getDeclaredFields();

                        for (Field dtoField : dtoFields) {
                            dtoField.setAccessible(true);
                            for (Field entityField : entityFields) {
                                entityField.setAccessible(true);
                                if (dtoField.getName().equals(entityField.getName())) {
                                    entityField.set(entity, dtoField.get(request));
                                }
                            }
                        }
                    } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        return null;
                    }
                }
            }
        }
        return entity;
    }
}
