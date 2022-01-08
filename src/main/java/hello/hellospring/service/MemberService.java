package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    /*public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/


    public Long join(Member member){

        validatedDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public void validatedDuplicateMember(Member member){

        /*Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
    
    /**
     *전체 회원 조회
     *  
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
