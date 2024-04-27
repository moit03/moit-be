package com.sparta.moit.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    /*
        - 기프티파이 형식 -> 번호로 상태 구분 + 실제로 발생한 status 코드 반환
        - 현재 상태 -> 에러 이름으로 구분 + 해당 에러마다 status 코드를 custom
        REGISTER_ACCOUNT_SUCCESS(true, 2100, "회원가입이 완료되었습니다"),
        LOGIN_SUCCESS(true, 2101, "로그인이 완료되었습니다."),
        KAKAO_LOGIN_SUCCESS(true, 2102, "카카오 로그인이 완료되었습니다."),
        GOOGLE_LOGIN_SUCCESS(true, 2103, "구글 로그인이 완료되었습니다."),
        LOGOUT_SUCCESS(true, 2104, "로그아웃이 완료되었습니다."),
        DELETE_ACCOUNT_SUCCESS(true, 2105, "회원탈퇴가 완료되었습니다.");

        private final boolean isSuccess;
        private final int code;
        private final String message;
        private HttpStatus httpStatus;

        BaseResponseStatus(boolean isSuccess, int code, String message) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.message = message;
        }

        BaseResponseStatus(boolean isSuccess, int code, String message, HttpStatus httpStatus) {
            this.isSuccess = isSuccess;
            this.code = code;
            this.message = message;
            this.httpStatus = httpStatus;
        }
    */

    /* 회원 */
    DUPLICATED_EMAIL("DUPLICATED_EMAIL", "중복된 이메일입니다.", HttpStatus.BAD_REQUEST),
    DUPLICATED_MEMBER("DUPLICATED_MEMBER", "중복된 회원입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_MEMBER("ALREADY_MEMBER", "이미 참가한 회원입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_USER("NOT_EXIST_USER", "해당 유저는 존재하지 않습니다.", HttpStatus.BAD_REQUEST),
    NOT_MATCH_PWD("NOT_MATCH_PWD", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    /* 모임 */
    MEETING_FULL("MEETING_FULL", "가득찬 모임입니다.", HttpStatus.BAD_REQUEST),
    NOT_MEETING_MEMBER("NOT_MEETING_MEMBER", "모임에 가입한 유저가 아닙니다.", HttpStatus.FORBIDDEN),
    MEETING_NOT_FOUND("MEETING_NOT_FOUND", "모임을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    CREATOR_CAN_NOT_LEAVE("CREATOR_CAN_NOT_LEAVE", "작성자는 탈퇴할 수 없습니다.", HttpStatus.BAD_REQUEST),
    /* 토큰 */
    AUTHORITY_ACCESS("AUTHORITY_ACCESS", "접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", "로그인 후 이용할 수 있습니다. 계정이 없다면 회원 가입을 진행해주세요.", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("INVALID_TOKEN", "토큰이 유효하지 않거나 이미 삭제되었습니다.", HttpStatus.UNAUTHORIZED),
    /* 유효성 검사 */
    VALIDATION_ERROR("VALIDATION_ERROR", "잘못된 입력입니다.", HttpStatus.BAD_REQUEST),
    /* 채팅 */
    CHATROOM_NOT_FOUND("CHATROOM_NOT_FOUND", "채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    /* 북마크 */
    NOT_BOOKMARKED("NOT_BOOKMARKED", "북마크 하지 않은 모임입니다.",HttpStatus.BAD_REQUEST),
    ALREADY_BOOKMARKED("ALREADY_BOOKMARKED", "이미 북마크 된 모임입니다.", HttpStatus.BAD_REQUEST),
    BOOKMARK_NOT_FOUND("BOOKMARK_NOT_FOUND", "북마크를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);



    private final String key;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String key, String message, HttpStatus httpStatus) {
        this.key = key;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

