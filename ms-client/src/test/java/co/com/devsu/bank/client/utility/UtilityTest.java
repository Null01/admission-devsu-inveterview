package co.com.devsu.bank.client.utility;

import co.com.devsu.bank.client.constants.type.GenderType;
import co.com.devsu.bank.client.constants.type.IdentificationType;
import co.com.devsu.bank.client.model.entities.ClientEntity;
import co.com.devsu.bank.client.model.entities.PersonEntity;

import java.time.LocalDateTime;
import java.util.Calendar;

public class UtilityTest {

    private static UtilityTest utilityTest;

    public static UtilityTest getInstance() {
        if (utilityTest == null)
            return new UtilityTest();
        return utilityTest;
    }

    public ClientEntity getClientEntityMock(String identificationNumber, IdentificationType identificationType, String password,
                                            GenderType gender, String firstName, String lastName) {
        return ClientEntity.builder()
                .status(Boolean.TRUE)
                .password(password)
                .createdAt(LocalDateTime.now())
                .person(PersonEntity.builder()
                        .identificationType(identificationType)
                        .identificationNumber(identificationNumber)
                        .firstName(firstName)
                        .lastName(lastName)
                        .gender(gender)
                        .updatedAt(LocalDateTime.now())
                        .build())
                .build();
    }
}
