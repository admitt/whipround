package ch.whip.round.member;

import ch.whip.round.account.GroupAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;

    public Long save(String username, String firstName, String lastName, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        memberRepository.save(member);
        return member.getId();
    }

    public List<GroupMember> groupMembers(GroupAccount account) {
        return groupMemberRepository.findByAccount(account);
    }

    public void addMember(GroupAccount account, Member member) {
        GroupMember groupMember = new GroupMember();
        groupMember.setMember(member);
        groupMember.setAccount(account);
        groupMemberRepository.save(groupMember);
    }

    List<GroupAccount> findGroups(Member member) {
        return groupMemberRepository.findGroups(member);
    }

    public long findUserId(String username) {
        Member byUsername = memberRepository.findByUsername(username);
        if (byUsername == null) {
            throw new IllegalStateException("Not able to find a member");
        }
        return byUsername.getId();
    }
}
