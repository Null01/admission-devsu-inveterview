package co.com.devsu.bank.account.controllers;

import co.com.devsu.bank.account.constants.IdentificationType;
import co.com.devsu.bank.account.constants.MessageResponseConstant;
import co.com.devsu.bank.account.controllers.dto.technical.BaseResponse;
import co.com.devsu.bank.account.exception.BusinessException;
import co.com.devsu.bank.account.model.entities.AccountEntity;
import co.com.devsu.bank.account.model.entities.TransactionAccountEntity;
import co.com.devsu.bank.account.service.IAccountService;
import co.com.devsu.bank.account.service.ITransactionAccountService;
import co.com.devsu.bank.account.utility.CommonsUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;


@Slf4j
@RestController
@RequestMapping(value = "/api/reportes")
public class ReportsRestController {

    private final IAccountService accountService;
    private final ITransactionAccountService transactionAccountService;

    public ReportsRestController(IAccountService accountService,
                                 ITransactionAccountService transactionAccountService) {
        this.accountService = accountService;
        this.transactionAccountService = transactionAccountService;
    }


    @PostMapping("/v1")
    public ResponseEntity<?> report(@RequestParam String identificationNumber, @RequestParam IdentificationType identificationType,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fromDate,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime toDate) throws BusinessException {

        if (!CommonsUtility.getInstance().isSanitized(identificationNumber))
            throw new BusinessException(MessageResponseConstant.EXCEPTION_VALIDATION_STRING_SANITIZE);

        final LocalDateTime from = Optional.ofNullable(fromDate).orElse(LocalDateTime.now());
        final LocalDateTime to = Optional.ofNullable(toDate).orElse(LocalDateTime.now());

        Map<String, Object> report = new HashMap<>();
        report.put("report", from + " - " + to);

        report.put("client", identificationType + " " + identificationNumber);
        List<AccountEntity> accounts = accountService.report(identificationNumber, identificationType);
        if (!accounts.isEmpty()) {
            List<Map<String, Object>> details = new ArrayList<>();
            accounts.forEach(account -> {
                Map<String, Object> detail = new HashMap<>();
                detail.put("Account number", account.getAccountNumber());
                detail.put("Account type", account.getAccountType());
                detail.put("Current balance", account.getBalance());
                detail.put("Account status", account.getStatus());

                List<TransactionAccountEntity> transactions = transactionAccountService.getAllByAccountId(account.getId(), from, to);
                detail.put("transactions", transactions);
                details.add(detail);
            });
            report.put("accounts", details);
        }
        return ResponseEntity.ok(BaseResponse.builder(report).build());
    }

}
