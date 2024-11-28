package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AllBeanTest {

    @Test
    void findAllBean() {
        // 스프링 컨테이너 생성, 스프링 빈(AutoAppConfig, DiscountService) 생성
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);

        // DiscountService 빈 가져오기
        DiscountService discountService = ac.getBean(DiscountService.class);

        // 테스트용 Member 생성
        Member member = new Member(1L, "userA", Grade.VIP);

        // 할인 정책 적용
        int FixDiscountPrice = discountService.discount(member, 1000, "fixDiscountPolicy");

        // 검증 - 코드 요약: Alt + Enter
        assertThat(discountService).isInstanceOf(DiscountService.class);
        assertThat(FixDiscountPrice).isEqualTo(1000);

        int RateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        assertThat(RateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        // 생성자 주입
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;

            // 의존성 확인 출력
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            // 코드에 해당하는 DiscountPolicy 가져오기
            DiscountPolicy discountPolicy = policyMap.get(discountCode);

            // 디버깅 출력
            System.out.println("discountCode = " + discountCode);
            System.out.println("discountPolicy = " + discountPolicy);

            // 할인 적용 및 반환
            return discountPolicy.discount(member, price);
        }
    }
}
