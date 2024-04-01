package co.com.devsu.bank.account.controllers;

import co.com.devsu.bank.account.constants.MessageResponseConstant;
import co.com.devsu.bank.account.controllers.dto.TransactionAccountRequest;
import co.com.devsu.bank.account.controllers.dto.technical.BaseResponse;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.TransactionAccountEntity;
import co.com.devsu.bank.account.service.ITransactionAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;


@Slf4j
@RestController
@RequestMapping(value = "/api/movimientos")
public class TransactionAccountRestController {

    private final ITransactionAccountService transactionAccountService;

    public TransactionAccountRestController(ITransactionAccountService transactionAccountService) {
        this.transactionAccountService = transactionAccountService;
    }

    @PostMapping("/v1")
    public ResponseEntity<?> register(@Valid @RequestBody TransactionAccountRequest request) throws BusinessException {

        TransactionAccountEntity transactionAccountEntity = transactionAccountService.register(request);

        return ResponseEntity.ok(BaseResponse.builder(transactionAccountEntity).build());
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<?> findResourceById(@PathVariable UUID id) throws BusinessException {
        Optional<TransactionAccountEntity> entity = transactionAccountService.getById(id);
        if (entity.isPresent())
            return ResponseEntity.ok(BaseResponse.builder(entity.get()).build());
        throw new BusinessException(MessageResponseConstant.EXCEPTION_NOT_FOUND_DATA);
    }

    @GetMapping("/v1")
    public ResponseEntity<?> findAllResources(@RequestParam(required = false) UUID accountId) {

        List<TransactionAccountEntity> entities;
        if (Optional.ofNullable(accountId).isPresent()) {
            entities = transactionAccountService.getAllByAccountId(accountId, null, null);
        } else {
            entities = transactionAccountService.getAll();
        }
        return ResponseEntity.ok(BaseResponse.builder(entities).build());
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<?> deleteResourceById(@PathVariable UUID id) {
        Boolean state = transactionAccountService.delete(id);
        Map<String, Object> outcome = new HashMap<>();
        outcome.put("removed", state);
        return ResponseEntity.ok(BaseResponse.builder(outcome).build());
    }

}
