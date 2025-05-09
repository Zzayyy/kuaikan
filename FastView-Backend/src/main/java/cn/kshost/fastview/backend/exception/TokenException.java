package cn.kshost.fastview.backend.exception;

import cn.kshost.fastview.backend.emus.FastViewEnum;
import lombok.Getter;

/**
 * token刷新失败异常
 */

@Getter
public class TokenException extends RuntimeException {
    private FastViewEnum fastViewEnum;
    public TokenException(FastViewEnum fastViewEnum) {
        this.fastViewEnum = fastViewEnum;
    }
}
