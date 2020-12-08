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
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class StudyTest {

    @DisplayName("반복하는 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo){
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + " / "
                + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @ValueSource(strings = {"첫번째", "두번째", "세번째"})
    //@EmptySource // 비어있는 문자열 넣어준다
    //@NullSource // null 넣어주고
    @NullAndEmptySource // 둘다 넣어준다
    void parameterizedTest(String msg){
        System.out.println(msg);
    }


    @DisplayName("인자 값 타입 변환 / 암시적 규칙 있으나 직접 구현 가능")
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @ValueSource(ints = {10,20,40})
    void typeChange(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("다중 파라미터 테스트")
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void ParameterizedTest2(Integer limit, String name){
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("Aggregator 테스트")
    @ParameterizedTest(name = "{index} {displayName} msg={0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void AggregatorTest(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
    }




    @FastTest
    @DisplayName("환경변수, 시스템 변수 등 특정값 일때 실행 등")
    @EnabledOnOs({OS.MAC, OS.WINDOWS}) // 맥, 윈도우에서만 실행
    // @DisabledOnOs({OS.MAC}) // 맥에서만 미실행
    // @EnabledOnJre({JRE.JAVA_8}) // java8 에서만 실행
    void create_new_study(){
        // 환경변수, 시스템 변수에 따라 실행하거나 실행하지 않아야 한다면
        String test_env = System.getenv("TEST_ENV");
        System.out.println("test_env : " + test_env);

        // assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        assumingThat("LOCAL".equalsIgnoreCase(test_env), () -> {
            // local 이면 이게 작동
        });
        assumingThat("jaeuk".equalsIgnoreCase(test_env), () -> {
            // jaeuk 이면 이게 작동
        });
        assumingThat(null == test_env, () -> {
            System.out.println("test_env : " + test_env);
        });
    }


    @Test
    @DisplayName("스터디 생성 / 이모지도 가능 \uD83D\uDE31")
    void create(){
        // 해당 익셉션이 발생하는지
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        // 해당 시간 내에 끝내지 않으면 실패, 걸린 시간도 비교
        // 다소 시간 걸릴수도 있지만 안전하다
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study(10);
            //Thread.sleep(300);
        });

        // 그냥 실패하면 바로 끝남, 시간비교 x
        // 하지만 주의해야 한다. 해당 코드블럭을 별도의 스레드, 스레드치 로컬을 사용하는 코드가 있다면 예상 못한 결과 발생할 수 있다.
        // 스프링의 트랜잭션이 제대로 작동 안할 수 있다. 스레드로컬과 관련 없으면 상관 없다.
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            new Study(10);
            //Thread.sleep(300);
        });

        Study study = new Study(10);
        assertNotNull(study);
        // 파라미터 의도-기대값, 실제값 / expected, actual
        //assertEquals(StudyStatus.DRAFT, .getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다");

        // 에러 메세지를 만드는 방법이 복잡할 경우, 람다식이study면 실패했을 경우만 해당 문자처리, 문자열 연산이 복잡할 경우 성능
        assertEquals(StudyStatus.DRAFT, study.getStatus(),
                () -> "스터디를 처음 만들면 상태값 : " + StudyStatus.DRAFT);
        assertTrue(study.getLimit() > 0 , "limit은 0보다 커야 한다");

        // 이런 방식도 가능
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    @DisplayName("assertAll 다중 틀린 것 한번에 확인 가능")
    void create_assert_all(){
        Study study = new Study(10);
        // 2개 이상 틀려도 한면에 에러 동시에 뜬다.
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값 : " + StudyStatus.DRAFT),
                () -> assertTrue(study.getLimit() > 0, "limit은 0보다 커야 한다")
        );
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