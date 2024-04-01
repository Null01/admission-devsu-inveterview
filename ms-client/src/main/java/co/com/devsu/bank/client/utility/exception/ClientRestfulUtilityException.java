package co.com.devsu.bank.client.utility.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class ClientRestfulUtilityException extends Exception {

    public ClientRestfulUtilityException(String message) {
        super(message);
    }

    public ClientRestfulUtilityException(String message, Exception exception) {
        super(message, exception);
    }
}
