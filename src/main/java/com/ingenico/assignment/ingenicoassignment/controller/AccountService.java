package com.ingenico.assignment.ingenicoassignment.controller;

import com.ingenico.assignment.ingenicoassignment.controller.exception.AccountNotFound;
import com.ingenico.assignment.ingenicoassignment.controller.dao.Database;
import com.ingenico.assignment.ingenicoassignment.controller.exception.BusinessException;
import com.ingenico.assignment.ingenicoassignment.controller.exception.RequestParameterMissing;
import com.ingenico.assignment.ingenicoassignment.model.Account;
import com.ingenico.assignment.ingenicoassignment.model.Transaction;
import com.ingenico.assignment.ingenicoassignment.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountService {

    /**
     * Creates a new bank account. If the customer is not registered in the system, creates
     * the customer identification as well.
     *
     * @param account The details of the account to be created.
     *
     * @return The number of the created account.
     *
     * @throws RequestParameterMissing When some required request parameter is missing.
     */
    @RequestMapping(value = "/account/new",method = RequestMethod.PUT)
    public synchronized String newAccount(@RequestBody Account account) throws RequestParameterMissing, BusinessException {

        // Validating required fields
        if(account == null) {
            throw new RequestParameterMissing("account");
        }
        if(account.type == null) {
            throw new RequestParameterMissing("type");
        }
        if(account.balance == null) {
            throw new RequestParameterMissing("balance");
        }
        if(account.owner == null) {
            throw new RequestParameterMissing("owner");
        }
        if(account.owner.documentNumber == null) {
            throw new RequestParameterMissing("owner.documentNumber");
        }
        if(account.owner.firstName == null) {
            throw new RequestParameterMissing("owner.firstName");
        }
        if(account.owner.lastName == null) {
            throw new RequestParameterMissing("owner.lastName");
        }

        if(account.balance < 0) {
            throw new BusinessException("Account balance can not be negative");
        }

        // If this customer does not have a register in the system,
        // creates a new entity register for him
        if(!Database.customerExists(account.owner.documentNumber)) {
            Database.newCustomer(account.owner);
        }

        Database.newAccount(account);

        return account.number;
    }

    /**
     * Registers a monetary transfer between two accounts.
     *
     * @param transfer The details about the transfer that must be completed.
     *
     * @return The number of the transfer (identifier for a receipt entity in more complex project).
     *
     * @throws RequestParameterMissing When some required request parameter is missing.
     * @throws AccountNotFound When does not exists an account with the supplied account number.
     * @throws BusinessException Generic business rule violation.
     */
    @RequestMapping(value = "/account/transfer",method = RequestMethod.PUT)
    public synchronized String transferMoney(@RequestBody Transfer transfer)
            throws RequestParameterMissing, AccountNotFound, BusinessException {

        // Validating required fields
        if(transfer == null) {
            throw new RequestParameterMissing("transfer");
        }
        if(transfer.origin == null) {
            throw new RequestParameterMissing("origin");
        }
        if(transfer.destiny == null) {
            throw new RequestParameterMissing("destiny");
        }
        if(transfer.amount == null) {
            throw new RequestParameterMissing("amount");
        }

        if(!Database.accountExists(transfer.origin)) {
            throw new AccountNotFound(transfer.origin);
        }

        if(!Database.accountExists(transfer.destiny)) {
            throw new AccountNotFound(transfer.destiny);
        }

        Account origin = Database.getAccount(transfer.origin);
        Account destiny = Database.getAccount(transfer.destiny);

        if(origin.balance - transfer.amount < 0) {
            throw new BusinessException("Account balance can not be negative");
        }

        origin.balance -= transfer.amount;

        // Account balance update is not necessary for this project
        // since we are using in-memory data structure

        Transaction originTransaction = new Transaction(origin, -transfer.amount,
                "Transfer to account " + destiny.number);
        Database.newTransfer(originTransaction);

        // Just for tests, used by account/list endpoint
        origin.transactions.add(originTransaction);

        Transaction destinyTransaction = new Transaction(destiny, transfer.amount,
                "Transfer from account " + origin.number);
        Database.newTransfer(destinyTransaction);

        // Just for tests, used by account/list endpoint
        destiny.transactions.add(destinyTransaction);

        return originTransaction.number;
    }

    /**
     * @return All accounts stored in the in-memory database (just for tests)
     */
    @RequestMapping(value = "/account/list",method = RequestMethod.GET)
    public synchronized List<Account> getAllAccounts() {
        return Database.getAllAccounts();
    }

    @ExceptionHandler(RequestParameterMissing.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String,Object> handleRequestParameterMissing(RequestParameterMissing ex,
                                                        HttpServletRequest request, HttpServletResponse resp) {

        // Handles the exceptions of class RequestParameterMissing
        // that were thrown by this service
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", String.format("Parameter %s is required", ex.getParameter()));
        return result;
    }

    @ExceptionHandler(AccountNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String,Object> handleAccountNotFound(AccountNotFound ex,
                                             HttpServletRequest request, HttpServletResponse resp) {

        // Handles the exceptions of class AccountNotFound
        // that were thrown by this service
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", String.format("Account %s not found", ex.getAccountNumber()));
        return result;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Map<String,Object> handleAccountNotFound(BusinessException ex,
                                             HttpServletRequest request, HttpServletResponse resp) {

        // Handles the exceptions of class BusinessException
        // that were thrown by this service
        HashMap<String, Object> result = new HashMap<>();
        result.put("error", ex.getMessage());
        return result;
    }
}
