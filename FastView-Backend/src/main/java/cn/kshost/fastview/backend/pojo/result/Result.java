package cn.kshost.fastview.backend.pojo.result;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import lombok.Data;

@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result<>(200, message, null);
    }
    public static <T> Result<T> success(FastViewEnum exceptionEnum) {
        return new Result<>(200, exceptionEnum.getMessage(), null);
    }
    public static <T> Result<T> success(FastViewEnum messageEnum, T data) {
        return new Result<>(messageEnum.getCode(), messageEnum.getMessage(), data);
    }

    public static <T> Result<T> error(FastViewEnum messageEnum) {
        return new Result<>(messageEnum.getCode(), messageEnum.getMessage(),null);
    }
    public static <T> Result<T> error(FastViewEnum messageEnum, T data) {
        return new Result<>(messageEnum.getCode(), messageEnum.getMessage(),data);
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> error(String message, T data) {
        return new Result<>(-1, message, data);
    }

    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
