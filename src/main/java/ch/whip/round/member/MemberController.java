package ch.whip.round.member;

import ch.whip.round.account.GroupAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/groups")
    public List<GroupAccount> memberGroups(@RequestParam Member member) {
        return memberService.findGroups(member);
    }
}
