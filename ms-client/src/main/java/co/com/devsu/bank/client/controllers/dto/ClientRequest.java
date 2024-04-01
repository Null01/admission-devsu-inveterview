package co.com.devsu.bank.client.controllers.dto;

import co.com.devsu.bank.client.constants.type.GenderType;
import co.com.devsu.bank.client.constants.type.IdentificationType;
import co.com.devsu.bank.client.controllers.dto.technical.CommonRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientRequest extends CommonRequest {
    @NotNull
    private String identificationNumber;
    @NotNull
    private IdentificationType identificationType;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Min(value = 100000000, message = "Numeric field must have more than 8 digits")
    private BigInteger phone;
    @NotNull
    private String address;
    @NotNull
    private Date birthDate;
    @NotNull
    private GenderType gender;

    @NotNull
    private String password;
    @NotNull
    private Boolean status;
}
