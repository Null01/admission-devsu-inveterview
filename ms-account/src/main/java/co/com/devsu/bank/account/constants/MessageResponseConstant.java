
package co.com.devsu.bank.account.constants;

import co.com.devsu.bank.account.controllers.dto.technical.BaseResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author andresduran0205@gmail.com
 */
@NoArgsConstructor
public class MessageResponseConstant {

    public static final BaseResponse<?> EXCEPTION_NOT_FOUND_DATA = message(HttpStatus.NOT_FOUND, "Not exist data associated.");
    public static final BaseResponse<?> EXCEPTION_NOT_WAS_POSIBLE_CREATE_OBJECT = message(HttpStatus.INTERNAL_SERVER_ERROR, "Not was possible create a object.");
    public static final BaseResponse<?> EXCEPTION_VALIDATION_STRING_SANITIZE = message(HttpStatus.BAD_REQUEST, "The parameter value is not valid for a system.");

    public static final BaseResponse<?> CLIENT_NOT_EXIST = message(HttpStatus.BAD_REQUEST, "The number identification not exist.");

    public static final BaseResponse<?> ACCOUNT_NOT_EXIST = message(HttpStatus.BAD_REQUEST, "The number account not exist.");
    public static final BaseResponse<?> ACCOUNT_INSUFFICIENT_BALANCE = message(HttpStatus.BAD_REQUEST, "The account not have sufficient balance.");


    private static BaseResponse<?> message(HttpStatus status, String message) {
        return BaseResponse.builder(status, message).build();
    }

    public static BaseResponse<?> message(String message) {
        return MessageResponseConstant.message(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
