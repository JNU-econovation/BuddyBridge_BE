package econo.buddybridge.survey.repository;

import econo.buddybridge.survey.entity.MongoSurvey;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyRepository extends MongoRepository<MongoSurvey, String> {

}
