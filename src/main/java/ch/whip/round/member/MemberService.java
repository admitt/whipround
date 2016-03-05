package ch.whip.round.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public void save(String username, String firstName, String lastName, String email) {
        Member member = new Member();
        member.setUsername(username);
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        memberRepository.save(member);
    }

    public Member find(Long id) {
        return memberRepository.findOne(id);
    }
}
