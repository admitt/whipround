package ch.whip.round.member;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class GroupMembers implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Collection<Member> members = new ArrayList<Member>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Member> getMembers() {
        return members;
    }

    public void setMembers(Collection<Member> members) {
        this.members = members;
    }

    public void addMember(Member member) {
        members.add(member);
    }
}
