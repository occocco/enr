package com.enr.task.api.forecast;

import lombok.Getter;

@Getter
public enum ResultCode {

    NORMAL_SERVICE("00", "정상", "Normal Service."),
    APPLICATION_ERROR("01", "어플리케이션 에러", "Application Error."),
    DB_ERROR("02", "데이터베이스 에러", "Database Error."),
    NO_DATA("03", "데이터없음 에러", "No data Error."),
    HTTP_ERROR("04", "HTTP 에러", "HTTP Error."),
    SERVICETIME_OUT("05", "서비스 연결실패 에러", "Service Timeout Error."),
    INVALID_REQUEST_PARAMETER_ERROR("10", "잘못된 요청 파라메터 에러", "Invalid Request Parameter Error."),
    NO_MANDATORY_REQUEST_PARAMETERS_ERROR("11", "필수요청 파라메터가 없음", "No mandatory request parameter Error."),
    NO_OPENAPI_SERVICE_ERROR("12", "해당 오픈API서비스가 없거나 폐기됨", "No OpenAPI Service Error."),
    SERVICE_ACCESS_DENIED_ERROR("20", "서비스 접근거부", "Service Access Denied Error."),
    TEMPORARILY_DISABLE_THE_SERVICEKEY_ERROR("21", "일시적으로 사용할 수 없는 서비스 키", "Temporarily disable the servicekey Error."),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR("22", "서비스 요청제한횟수 초과에러", "Limited number of service requests exceeds Error."),
    SERVICE_KEY_IS_NOT_REGISTERED_ERROR("30", "등록되지 않은 서비스키", "Service Key is not Registered Error."),
    DEADLINE_HAS_EXPIRED_ERROR("31", "기한만료된 서비스키", "Deadline has expired Error."),
    UNREGISTERED_IP_ERROR("32", "등록되지 않은 IP", "Unregistered IP Error."),
    UNSIGNED_CALL_ERROR("33", "서명되지 않은 호출", "Unsigned Call Error."),
    UNKNOWN_ERROR("99", "기타에러", "Unknown Error.");

    private final String code;
    private final String message;
    private final String description;

    ResultCode(String code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
