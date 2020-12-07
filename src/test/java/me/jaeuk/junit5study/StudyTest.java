package me.jaeuk.junit5study;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    @DisplayName("junit5 스터디 중 이모지도 가능 \uD83D\uDE31")
    void create(){
        Study study = new Study();
        assertNotNull(study);
    }

    @Test
    @Disabled // 실행하지 않게 한다
    void create_disabled(){
        Study study = new Study();
        assertNotNull(study);
    }

    @BeforeAll
    // 테스트 클래스 안에 있는 여러 테스트가 모두 실행될때 모든 테스트가 실행되기 전에 딱 1번 호출/반드시 static 메서드 사용해야 한다
    static void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    // 테스트 클래스 안에 있는 여러 테스트가 모두 실행될때 모든 테스트가 실행된 후 딱 1번 호출"
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