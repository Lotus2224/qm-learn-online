package com.qm.base.exception;

import lombok.Getter;
import lombok.Setter;

// 项目自定义异常类型
@Setter
@Getter
public class LearnOnlineException extends RuntimeException {

    private String errMessage;

    public LearnOnlineException() {
    }

    public LearnOnlineException(String message) {
        super(message);
        this.errMessage = message;
    }

    // 为了方便抛出异常，自定义的静态方法cast
    public static void cast(String message) {
        throw new LearnOnlineException(message);
    }

    public static void cast(CommonError error) {
        throw new LearnOnlineException(error.getErrMessage());
    }
}
