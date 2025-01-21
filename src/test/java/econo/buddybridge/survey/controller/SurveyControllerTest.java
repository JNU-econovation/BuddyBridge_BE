package econo.buddybridge.survey.controller;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.Rollback;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Rollback
@DisplayName("설문조사 API E2E 테스트")
class SurveyControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("설문지 저장")
    void saveSurvey() {
        //given
        Map<String, Object> content = new HashMap<>(Map.of(
                "title", "설문조사 제목",
                "description", "설문조사 설명",
                "firstQuestion", "첫번째 질문",
                "firstAnswer", "첫번째 답변",
                "secondQuestion", "두번째 질문",
                "secondAnswer", "두번째 답변"
        ));

        content.put("title", null); // 값이 null인 경우도 저장되어야 함

        //when
        given()
                .port(port)
                .contentType("application/json")
                .body(content)
                .log().all()    // 요청 로그 출력
                .when()
                .post("/api/v1/surveys")
                .then()
                .statusCode(201)
                .log().all();   // 응답 로그 출력
    }
}
