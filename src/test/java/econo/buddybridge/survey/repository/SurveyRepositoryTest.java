package econo.buddybridge.survey.repository;

import static org.junit.jupiter.api.Assertions.assertNull;

import econo.buddybridge.survey.entity.MongoSurvey;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("MongoFormRepository 테스트")
class SurveyRepositoryTest {

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    @DisplayName("저장 및 삭제 테스트")
    void deleteTest() {
        //given
        HashMap<String, Object> content = new HashMap<>();
        content.put("test", "test");
        content.put("test2", "test2");

        MongoSurvey mongoSurvey = MongoSurvey.from(content);

        //when
        surveyRepository.save(mongoSurvey);
        surveyRepository.deleteById(mongoSurvey.getId());

        //then
        assertNull(surveyRepository.findById(mongoSurvey.getId()).orElse(null));
    }
}
