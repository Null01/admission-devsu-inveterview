package co.com.devsu.bank.account.controllers.dto;

import co.com.devsu.bank.account.constants.type.TransactionType;
import co.com.devsu.bank.account.controllers.dto.technical.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Data
public class TransactionAccountRequest {
    @NotNull
    private BigInteger accountNumber;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private BigDecimal amount;
}
