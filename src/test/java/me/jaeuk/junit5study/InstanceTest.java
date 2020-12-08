package me.jaeuk.junit5study;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InstanceTest {
    // junit 기본 전약. 테스트 마다 클래스 인스턴스를 새로 만든다.
    // 테스트 간의 의존성을 없애기 위함
    // int value = 1 다른 테스트에서 변경하더라도, 테스트는 항상 1을 갖고 시작한다고 보면 된다.
    // 순서가 정해져 있다고 생각하면 안된다.
    // 하지만 변경 가능

    /*
    @TestInstance(Lifecycle.PER_CLASS)
    테스트 클래스당 인스턴스를 하나만 만들어 사용한다.
    경우에 따라, 테스트 간에 공유하는 모든 상태를 @BeforeEach 또는 @AfterEach에서 초기화 할 필요가 있다.
    @BeforeAll과 @AfterAll을 인스턴스 메소드 또는 인터페이스에 정의한 default 메소드로 정의할 수도 있다.
     */

    int value = 1;

    @Disabled
    @DisplayName("반복하는 테스트")
    @RepeatedTest(5)
    void createStudy(){
        System.out.println(new Study(value++));
    }

    @BeforeAll
    // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 에서는
    // static 빼도 됨
    void beforeAll(){
        System.out.println("beforeAll");
    }

    @AfterAll
    // @TestInstance(TestInstance.Lifecycle.PER_CLASS) 에서는
    // static 빼도 됨
    void afterAll(){
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