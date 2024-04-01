package co.com.devsu.bank.client.controllers.handler;

import co.com.devsu.bank.client.controllers.dto.technical.BaseResponse;
import co.com.devsu.bank.client.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author andresduran0205@gmail.com
 */
@Slf4j
@RestControllerAdvice
public class RestControllerHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        log.error("handleAllExceptions - Exception: ", exception);
        Map<String, Object> response = new HashMap<>();
        HttpStatus status = exception instanceof BusinessException ? Optional.ofNullable(((BusinessException) exception)).map(BusinessException::getResponse).map(BaseResponse::getStatus).orElse(HttpStatus.INTERNAL_SERVER_ERROR)
                : HttpStatus.INTERNAL_SERVER_ERROR;
        response.put("status", status);
        response.put("message", exception.getMessage());
        response.put("timestamp", Calendar.getInstance().getTime());
        return new ResponseEntity<>(response, status);
    }

    @ResponseBody
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", status);
        responseMap.put("message", exception.getMessage());
        responseMap.put("timestamp", Calendar.getInstance().getTime());

        if (exception instanceof MethodArgumentNotValidException) {
            responseMap.put("status", HttpStatus.BAD_REQUEST);
            List<String> errors = ((MethodArgumentNotValidException) exception).getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            responseMap.put("error", errors);
            return new ResponseEntity<>(responseMap, headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(responseMap, status);
    }
}
