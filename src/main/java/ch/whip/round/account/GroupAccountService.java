package ch.whip.round.account;

import ch.whip.round.member.Member;
import ch.whip.round.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupAccountService {
    @Autowired
    private GroupAccountRepository groupAccountRepository;
    @Autowired
    private MemberService memberService;

    void createGroupAccount(Member manager, String reason) {
        GroupAccount groupAccount = new GroupAccount();
        groupAccount.setManager(manager);
        groupAccount.setReason(reason);
        groupAccountRepository.save(groupAccount);
        addMember(manager, groupAccount);
    }

    void addMember(Member newMember, GroupAccount account) {
        memberService.addMember(account, newMember);
    }
}
