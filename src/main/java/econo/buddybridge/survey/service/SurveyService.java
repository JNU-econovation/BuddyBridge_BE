package econo.buddybridge.survey.service;

import econo.buddybridge.survey.entity.MongoSurvey;
import econo.buddybridge.survey.repository.SurveyRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public void save(Map<String, Object> content) {
        surveyRepository.save(MongoSurvey.from(content));
    }
}
