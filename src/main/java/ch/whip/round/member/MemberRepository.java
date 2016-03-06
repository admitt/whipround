package ch.whip.round.member;

import org.springframework.data.repository.CrudRepository;

interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUsername(String username);
}
