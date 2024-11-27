package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceImplTest {

    @Test
    void createOrder() {
        // 설정자 주입(Setter)를 사용할 경우 memberRepository과 discountPolicy를 설정자 주입으로 넣어줘야 함.
        // 따라서 아래 코드는 NullPointerException 발생
//        OrderServiceImpl orderService = new OrderServiceImpl();
//        orderService.createOrder(1L, "itemA", 10000);

        // 이러한 실수를 방지하기 위해 생성자 주입을 사용한다.
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
//        org.assertj.core.api.Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
        // asserThat에 포인터 두고 Alt+Enter 누르고 정적 import문 사용 누르면 아래로 됨
        assertThat(order.getDiscountPrice()).isEqualTo(1000);

    }
}
