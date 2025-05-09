package cn.kshost.fastview.backend.handler;

import cn.kshost.fastview.backend.exception.DataException;
import cn.kshost.fastview.backend.exception.LoginException;
import cn.kshost.fastview.backend.exception.TokenException;
import cn.kshost.fastview.backend.pojo.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕获全局异常
 */
@Slf4j
@RestControllerAdvice
public class FastViewExceptionHandler  {

    /**
     * 未选中异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e) {
        log.error(String.format("×错误异常 信息为： %s"),e.getMessage());
        return Result.error(500,e.getMessage());
    }


    /**
     * 数据异常
     */
    @ExceptionHandler(DataException.class)
    public Result exception(DataException dataException) {

        log.error(String.format("×错误异常 信息为： %s"),dataException.getMessage());
        return Result.error(dataException.getMessageEnum());
    }
    /**
     * Token异常
     */
    @ExceptionHandler(TokenException.class)
    public Result exception(TokenException tokenException) {
        log.error(String.format("×错误异常 信息为： %s"),tokenException.getMessage());
        return Result.error(tokenException.getFastViewEnum());
    }
    /**
     * 登录异常
     */
    @ExceptionHandler(LoginException.class)
    public Result exception(LoginException loginException) {
        log.error(String.format("×错误异常 信息为： %s"),loginException.getMessage());
        return Result.error(loginException.getFastViewEnum());
    }
}
