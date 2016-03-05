package ch.whip.round.account;

import ch.whip.round.member.Member;
import ch.whip.round.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@CrossOrigin(origins = "*")
public class GroupAccountController {
    @Autowired
    private GroupAccountService groupAccountService;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/create/account", method = RequestMethod.POST)
    public void createAccount(@RequestParam Member manager, @RequestParam String reason) {
        groupAccountService.createGroupAccount(manager, reason);
    }

    @RequestMapping(value = "/account/{accountId}/add/member/{memberId}", method = RequestMethod.POST)
    public void addMember(@PathVariable Member member, @PathVariable GroupAccount account) {
        groupAccountService.addMember(member, account);
    }

    @RequestMapping(value = "/account/add/money", method = RequestMethod.POST)
    public void addMoney(@RequestParam BigDecimal amount, @RequestParam GroupAccount group, @RequestParam Member member) {
        transactionService.addTransaction(amount, group, member);
    }
}
