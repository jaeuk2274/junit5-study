package me.jaeuk.junit5study;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @ExtendWith(FindSlowTestExtention.class) // 01. 선언적인 등록 방
class SenarioTest {

    // 02.프로그래밍 적 등록
    @RegisterExtension
    static FindSlowTestExtention findSlowTestExtention =
            new FindSlowTestExtention(1000);

    @Order(4)
    @DisplayName("1 반복하는 테스트")
    @Test
    void createStudy1(){
        System.out.println(new Study(1));
    }

    @Order(3)
    @DisplayName("2 반복하는 테스트")
    @Test
    void createStudy2() throws InterruptedException {
        Thread.sleep(1150);
        System.out.println(new Study(2));
    }

    @Order(2)
    @DisplayName("3 반복하는 테스트")
    @Test
    void createStudy3(){
        System.out.println(new Study(3));
    }

    @Order(1)
    @DisplayName("4 반복하는 테스트")
    @Test
    void createStudy4(){
        System.out.println(new Study(4));
    }


    @BeforeAll
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("afterAll");
    }

    @BeforeEach
    void beforeEach(){
        System.out.println("beforeEach");
    }

    @AfterEach
    void afterEach(){
        System.out.println("afterEach");
    }
}