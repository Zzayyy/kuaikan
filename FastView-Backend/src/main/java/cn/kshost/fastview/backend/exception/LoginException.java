package cn.kshost.fastview.backend.exception;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import lombok.Getter;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 登录异常
 */
@Getter
public class LoginException extends RuntimeException {
    private FastViewEnum fastViewEnum;
    public LoginException(FastViewEnum fastViewEnum) {
        this.fastViewEnum = fastViewEnum;
    }
}