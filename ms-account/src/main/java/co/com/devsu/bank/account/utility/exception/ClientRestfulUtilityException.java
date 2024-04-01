package co.com.devsu.bank.account.utility.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientRestfulUtilityException extends Exception {

    public ClientRestfulUtilityException(String message) {
        super(message);
    }

    public ClientRestfulUtilityException(String message, Exception exception) {
        super(message, exception);
    }
}
