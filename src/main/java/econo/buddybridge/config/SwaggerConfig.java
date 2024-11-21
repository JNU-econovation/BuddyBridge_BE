package econo.buddybridge.config;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import econo.buddybridge.common.exception.BusinessException;
import econo.buddybridge.common.exception.ErrorCode;
import econo.buddybridge.common.exception.ErrorResponse;
import econo.buddybridge.common.swagger.ApiExceptionExamples;
import econo.buddybridge.common.swagger.ExplainError;
import econo.buddybridge.common.swagger.SwaggerExampleHolder;
import econo.buddybridge.utils.api.ApiResponse.CustomBody;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "access-token";

    private final ApplicationContext applicationContext;

    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {

        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);

        // JWT 토큰을 위한 보안 스킴 정의
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(SECURITY_SCHEME_NAME);

        return new OpenAPI()
                .servers(List.of(server))
                .info(swaggerInfo())
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme))
                .addSecurityItem(securityRequirement);
    }

    private Info swaggerInfo() {
        License license = new License();
        license.setUrl("https://github.com/JNU-econovation/BuddyBridge_BE");
        license.setName("Buddy Bridge");

        return new Info()
                .version("v0.0.1")
                .title("Buddy Bridge API문서")
                .description("Buddy Bridge의 API 문서 입니다.")
                .license(license);
    }

    @Bean
    public OperationCustomizer customizer() {
        return (operation, handlerMethod) -> {
            ApiExceptionExamples apiExceptionExamples =
                    handlerMethod.getMethodAnnotation(ApiExceptionExamples.class);

            if (apiExceptionExamples != null) {
                generateExceptionResponseExamples(operation, apiExceptionExamples.value());
            }

            operation.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
            return operation;
        };
    }

    private void generateExceptionResponseExamples(Operation operation, Class<?> type) {
        ApiResponses responses = operation.getResponses();

        Object bean = applicationContext.getBean(type);
        Field[] declaredFields = bean.getClass().getDeclaredFields();

        Map<Integer, List<SwaggerExampleHolder>> statusWithExampleHolders = Arrays.stream(declaredFields)
                .filter(field -> field.getAnnotation(ExplainError.class) != null)           // ExplainError 어노테이션이 붙은 필드만 필터링
                .filter(field -> BusinessException.class.isAssignableFrom(field.getType())) // BusinessException 타입만 필터링
                .map(field -> {
                    try {
                        BusinessException exception = (BusinessException) field.get(bean);
                        ExplainError explainError = field.getAnnotation(ExplainError.class);

                        String value = explainError.value();
                        ErrorCode errorCode = exception.getErrorCode();

                        return SwaggerExampleHolder.builder()
                                .holder(getSwaggerExample(value, errorCode))
                                .name(field.getName().replace('_', ' '))    // 드롭다운 메뉴에 표시될 이름
                                .code(errorCode.getHttpStatus().value())
                                .build();
                    } catch (IllegalAccessException e) {
                        // 문서가 생성되지 않는 예외 케이스
                        throw new RuntimeException(e);
                    }
                }).collect(groupingBy(SwaggerExampleHolder::getCode));

        addExamplesToResponses(responses, statusWithExampleHolders);
    }

    private Example getSwaggerExample(String value, ErrorCode errorCode) {
        Example example = new Example();
        CustomBody<String> errorResponse = new CustomBody<>(false, null, new ErrorResponse(errorCode));

        example.value(errorResponse);   // Example Value에 표시될 내용
        example.description(value);     // Example Description에 표시될 내용
        return example;
    }

    private void addExamplesToResponses(
            ApiResponses responses,
            Map<Integer, List<SwaggerExampleHolder>> statusWithExampleHolders
    ) {
        statusWithExampleHolders.forEach((status, exampleHolders) -> {
            ApiResponse apiResponse = new ApiResponse();
            Content content = new Content();
            MediaType mediaType = new MediaType();

            exampleHolders.forEach(exampleHolder ->
                    mediaType.addExamples(exampleHolder.getName(), exampleHolder.getHolder()));

            content.addMediaType(APPLICATION_JSON_VALUE, mediaType);
            apiResponse.setContent(content);

            responses.addApiResponse(status.toString(), apiResponse);
        });
    }
}
