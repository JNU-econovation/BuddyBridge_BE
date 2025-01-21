package econo.buddybridge.survey.controller;

import econo.buddybridge.common.annotation.AllowAnonymous;
import econo.buddybridge.survey.service.SurveyService;
import econo.buddybridge.utils.api.ApiResponse;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import econo.buddybridge.utils.api.ApiResponseGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/surveys")
@Tag(name = "설문조사 API", description = "설문조사 관련 API")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "설문조사 생성", description = "설문조사를 생성합니다.")
    @PostMapping
    @AllowAnonymous
    public ApiResponse<CustomBody<Void>> createSurvey(@RequestBody Map<String, Object> content) {
        surveyService.save(content);
        return ApiResponseGenerator.success(HttpStatus.CREATED);
    }
}
