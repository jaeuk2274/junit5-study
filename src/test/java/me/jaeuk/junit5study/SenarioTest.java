package me.jaeuk.junit5study;

import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SenarioTest {

    @Order(4)
    @DisplayName("1 반복하는 테스트")
    @Test
    void createStudy1(){
        System.out.println(new Study(1));
    }

    @Order(3)
    @DisplayName("2 반복하는 테스트")
    @Test
    void createStudy2(){
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