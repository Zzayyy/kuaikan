package cn.kshost.fastview.backend.handler;
import cn.kshost.fastview.backend.exception.DataException;
import cn.kshost.fastview.backend.exception.LoginException;
import cn.kshost.fastview.backend.exception.TokenException;
import cn.kshost.fastview.backend.pojo.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕获全局异常
 */
@RestControllerAdvice
public class FastViewExceptionHandler  {

    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        return Result.success(e.getMessage());
    }

    /**
     * 数据异常
     */
    @ExceptionHandler(DataException.class)
    public Result exception(DataException dataException) {
        return Result.error(dataException.getMessageEnum());
    }
    /**
     * Token异常
     */
    @ExceptionHandler(TokenException.class)
    public Result exception(TokenException tokenException) {
        return Result.error(tokenException.getFastViewEnum());
    }
    /**
     * 登录异常
     */
    @ExceptionHandler(LoginException.class)
    public Result exception(LoginException loginException) {
        return Result.error(loginException.getFastViewEnum());
    }
}
