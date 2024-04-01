
package co.com.devsu.bank.client.constants;

import co.com.devsu.bank.client.controllers.dto.technical.BaseResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author andresduran0205@gmail.com
 */
@NoArgsConstructor
public class MessageResponseConstant {

    public static final BaseResponse<?> EXCEPTION_NOT_FOUND_DATA = message(HttpStatus.NOT_FOUND, "Not exist data associated.");
    public static final BaseResponse<?> EXCEPTION_NOT_WAS_POSIBLE_CREATE_OBJECT = message(HttpStatus.INTERNAL_SERVER_ERROR, "Not was possible create a object.");


    private static BaseResponse<?> message(HttpStatus status, String message) {
        return BaseResponse.builder(status, message).build();
    }

    public static BaseResponse<?> message(String message) {
        return MessageResponseConstant.message(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
