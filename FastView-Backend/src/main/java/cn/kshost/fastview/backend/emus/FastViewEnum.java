package cn.kshost.fastview.backend.emus;

/**
 * 枚举类统一常用返回信息
 */
public enum FastViewEnum {

    /**
     * 顺利返回区 (用于正常返回)
     */
    ADD_SUCCESS(401,"添加成功"),
    DELETE_SUCCESS(200,"删除成功"),
    MODIFY_SUCCESS(200,"修改成功"),
    QUERY_SUCCESS(200,"查询成功"),
    TOKEN_REFRESH_SUCCESS(200,"Token修改成功"),


    /**
     * 异常区 （用于new异常类）
     */
    DATA_ERROR(401,"数据异常"),
    USERNAME_PASSWORD_ERROR(411,"用户名或密码错误"),
    TOKEN_REFRESH_ERROR(412,"Token刷新失败");


    private Integer code;
    private String message;
    FastViewEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public Integer getCode() {
        return code;
    }
}
