package cn.kshost.fastview.backend.util;

import cn.kshost.fastview.backend.security.LoginUserDetail;

/**
 * 封装ThreadLocal 用户获取用户信息
 */
public class FastViewContextUtil {

   final static ThreadLocal<LoginUserDetail> threadLocal =   new ThreadLocal();

    /**
     * 设置 loginUserDetail
     * @param loginUserDetail
     */
   public static void setLoginUserDetail(LoginUserDetail loginUserDetail) {
       threadLocal.set(loginUserDetail);
   }

    /**
     * 获取 loginUserDetail
     *
     */
    public static LoginUserDetail getLoginUserDetail() {
        return threadLocal.get();
    }

    /**
     * 删除 loginUserDetail
     *
     */
    public static void removeLoginUserDetail() {
        threadLocal.remove();
    }

}
