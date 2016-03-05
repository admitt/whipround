package ch.whip.round.account;

import ch.whip.round.member.Member;
import ch.whip.round.transaction.Transaction;
import ch.whip.round.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
public class GroupAccountController {
    @Autowired
    private GroupAccountService groupAccountService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createAccount(@RequestParam Member manager, @RequestParam String reason) {
        groupAccountService.createGroupAccount(manager, reason);
    }

    @RequestMapping(value = "/{accountId}/add/member/{memberId}", method = RequestMethod.POST)
    public void addMember(@PathVariable Member member, @PathVariable GroupAccount account) {
        groupAccountService.addMember(member, account);
    }

    @RequestMapping(value = "/add/transaction", method = RequestMethod.POST)
    public void addMoney(@RequestParam BigDecimal amount, @RequestParam GroupAccount group, @RequestParam Member member) {
        transactionService.addTransaction(amount, group, member);
    }

    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public BigDecimal addMoney(@RequestParam GroupAccount group) {
        return transactionService.accountBalance(group);
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public List<Transaction> accountTransactions(@RequestParam GroupAccount group) {
        return transactionService.findAccountTransactions(group);
    }
}
