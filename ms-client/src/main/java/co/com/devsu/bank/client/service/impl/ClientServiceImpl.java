package co.com.devsu.bank.client.service.impl;

import co.com.devsu.bank.client.controllers.dto.ClientRequest;
import co.com.devsu.bank.client.exception.BusinessException;
import co.com.devsu.bank.client.model.entities.ClientEntity;
import co.com.devsu.bank.client.model.entities.PersonEntity;
import co.com.devsu.bank.client.model.repository.IClientEntityRepository;
import co.com.devsu.bank.client.service.IClientService;
import co.com.devsu.bank.client.utility.EncoderUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ClientServiceImpl implements IClientService {

    private final IClientEntityRepository clientEntityRepository;

    public ClientServiceImpl(IClientEntityRepository clientEntityRepository) {
        this.clientEntityRepository = clientEntityRepository;
    }

    @Override
    public ClientEntity create(ClientRequest request) throws BusinessException {
        try {
            ClientEntity client = ClientEntity.builder()
                    .status(request.getStatus())
                    .password(EncoderUtility.getInstance().generateMD5Hash(request.getPassword()))
                    .person(
                            PersonEntity.builder()
                                    .phone(request.getPhone())
                                    .address(request.getAddress())
                                    .gender(request.getGender())
                                    .birthDate(request.getBirthDate())
                                    .firstName(request.getFirstName())
                                    .lastName(request.getLastName())
                                    .identificationType(request.getIdentificationType())
                                    .identificationNumber(request.getIdentificationNumber())
                                    .build()
                    ).build();

            return clientEntityRepository.save(client);
        } catch (Exception e) {
            log.error("NOT WAS POSSIBLE CREATE A CLIENT.", e);
            throw new BusinessException("Not was possible create a client.");
        }
    }
}
