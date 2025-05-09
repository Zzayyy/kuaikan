package cn.kshost.fastview.backend.exception;

import cn.kshost.fastview.backend.emus.FastViewEnum;

public class DataException  extends RuntimeException {
    private FastViewEnum messageEnum;
    public DataException(FastViewEnum messageEnum) {
        this.messageEnum = messageEnum;
    }
    public FastViewEnum getMessageEnum() {
        return messageEnum;
    }
}
