package co.com.devsu.bank.account.controllers.dto;

import co.com.devsu.bank.account.constants.type.AccountType;
import co.com.devsu.bank.account.constants.type.TransactionType;
import co.com.devsu.bank.account.controllers.dto.technical.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountRequest extends CommonRequest {
    @NotNull
    private UUID clientId;
    @NotNull
    private BigInteger accountNumber;
    @NotNull
    private AccountType accountType;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private Boolean status;
}
