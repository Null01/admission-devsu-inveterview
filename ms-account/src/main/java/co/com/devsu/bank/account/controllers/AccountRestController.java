package co.com.devsu.bank.account.controllers;

import co.com.devsu.bank.account.controllers.common.CommonRestController;
import co.com.devsu.bank.account.controllers.dto.AccountRequest;
import co.com.devsu.bank.account.model.entities.AccountEntity;
import co.com.devsu.bank.account.model.repository.IAccountEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;


@Slf4j
@RestController
@RequestMapping(value = "/api/cuentas")
public class AccountRestController extends CommonRestController<IAccountEntityRepository, AccountEntity, AccountRequest, UUID> {
}
