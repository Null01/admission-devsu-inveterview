package co.com.devsu.bank.client.controllers.dto.technical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Calendar;
import java.util.Date;


/**
 * @author andresduran0205@gmail.com
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private HttpStatus status;
    private String message;
    private Date timestamp;
    private T data;

    public static BaseResponseBuilder<?> builder(HttpStatus status, String message) {
        return new BaseResponseBuilder<>().status(status).message(message).timestamp(Calendar.getInstance().getTime());
    }

    public static BaseResponseBuilder<?> builder(Object data) {
        return new BaseResponseBuilder<>().status(HttpStatus.OK).timestamp(Calendar.getInstance().getTime()).data(data);
    }

}
