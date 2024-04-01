package co.com.devsu.bank.client.controllers;

import co.com.devsu.bank.client.controllers.common.CommonRestController;
import co.com.devsu.bank.client.controllers.dto.ClientRequest;
import co.com.devsu.bank.client.controllers.dto.technical.BaseResponse;
import co.com.devsu.bank.client.exception.BusinessException;
import co.com.devsu.bank.client.model.entities.ClientEntity;
import co.com.devsu.bank.client.model.repository.IClientEntityRepository;
import co.com.devsu.bank.client.service.IClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/api/clientes")
public class ClientRestController extends CommonRestController<IClientEntityRepository, ClientEntity, ClientRequest, UUID> {

    private final IClientService clientService;

    public ClientRestController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<?> saveResource(@Valid @RequestBody ClientRequest request) throws BusinessException {

        ClientEntity client = clientService.create(request);

        return ResponseEntity.ok(BaseResponse.builder(client).build());
    }
}
