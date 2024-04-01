package co.com.devsu.bank.client.service;

import co.com.devsu.bank.client.constants.type.GenderType;
import co.com.devsu.bank.client.constants.type.IdentificationType;
import co.com.devsu.bank.client.controllers.dto.ClientRequest;
import co.com.devsu.bank.client.exception.BusinessException;
import co.com.devsu.bank.client.model.entities.ClientEntity;
import co.com.devsu.bank.client.model.repository.IClientEntityRepository;
import co.com.devsu.bank.client.service.impl.ClientServiceImpl;
import co.com.devsu.bank.client.utility.UtilityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private IClientEntityRepository clientEntityRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void createClientWhenMD5HashedPasswordIsWrong() throws BusinessException {
        // Given
        ClientRequest request = new ClientRequest();
        request.setStatus(Boolean.TRUE);
        request.setIdentificationNumber("123456789");
        request.setIdentificationType(IdentificationType.CC);
        request.setPassword("password");

        Mockito.when(clientEntityRepository.save(Mockito.any()))
                .thenReturn(UtilityTest.getInstance().getClientEntityMock("123456789", IdentificationType.CC, "password", GenderType.M, "first", "second"));

        // When
        ClientEntity savedClient = clientService.create(request);

        // Then
        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(true, savedClient.getStatus());
        Assertions.assertNotEquals("hashedPassword", savedClient.getPassword());
    }

    @Test
    public void createClientWhenMD5HashedPasswordNotHaveValue() {
        // Given
        ClientRequest request = new ClientRequest();
        request.setStatus(Boolean.TRUE);
        request.setIdentificationNumber("123456789");
        request.setIdentificationType(IdentificationType.CC);
        request.setGender(GenderType.M);

        // When
        BusinessException exception = Assertions.assertThrows(BusinessException.class, () -> clientService.create(request));

        // Then
        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Not was possible create a client.", exception.getMessage());
    }

    @Test
    public void createClientWhenAllParametersAreCorrect() throws BusinessException {
        // Given
        ClientRequest request = new ClientRequest();
        request.setStatus(Boolean.TRUE);
        request.setIdentificationNumber("123456789");
        request.setIdentificationType(IdentificationType.CC);
        request.setPassword("password");
        request.setGender(GenderType.M);

        Mockito.when(clientEntityRepository.save(Mockito.any()))
                .thenReturn(UtilityTest.getInstance().getClientEntityMock("123456789", IdentificationType.CC, "5f4dcc3b5aa765d61d8327deb882cf99", GenderType.M, "first", "second"));

        // When
        ClientEntity savedClient = clientService.create(request);

        // Then
        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals(true, savedClient.getStatus());
        Assertions.assertEquals("5f4dcc3b5aa765d61d8327deb882cf99", savedClient.getPassword());
        Assertions.assertNotNull(savedClient.getCreatedAt());

        // Verify that save method is called on clientEntityRepository with correct argument
        Mockito.verify(clientEntityRepository).save(Mockito.any(ClientEntity.class));
    }
}
