package cyou.pymiliblog.codeoptimizationmaster.common;

import org.springframework.http.ResponseEntity;

public record RestCommon<T>(int code, String msg, T data) {

    public static <T> ResponseEntity<RestCommon<T>> ok(T data) {
        return ResponseEntity.ok(new RestCommon<>(200, "success", data));
    }
    public static <T> ResponseEntity<RestCommon<T>> fail(String msg) {
        return ResponseEntity.badRequest().body(new RestCommon<>(400, msg, null));
    }
    public static <T> ResponseEntity<RestCommon<T>> error(String msg) {
        return ResponseEntity.internalServerError().body(new RestCommon<>(500, msg, null));
    }

    /**
     * 参数缺失
     * @return {@link ResponseEntity}
     */
    public static <T> ResponseEntity<RestCommon<T>> missingParam() {
        return fail("Missing required parameter(s)");
    }

}
