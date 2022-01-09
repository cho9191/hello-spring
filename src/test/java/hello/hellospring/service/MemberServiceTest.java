package hello.hellospring.service;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;



class MemberServiceTest {

    MemberService memberService;
    MemberRepository memberRepository;


    @BeforeEach
    public void beforeEach(){
        //memberRepository = new MemoryMemberRepository();
        //memberService = new MemberService(memberRepository);

    }

    @AfterEach
    public void afterEach(){
        //memberRepository.
        System.out.println("AfterEach 시작!");
    }

    @Test
    void join() {
        Member member = new Member();
        member.setName("hello");

        Long saveId = memberService.join(member);

        System.out.println("saveId : "+saveId);

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    void validatedDuplicateMember() {
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1);

        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }


    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}