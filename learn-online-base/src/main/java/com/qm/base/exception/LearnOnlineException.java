package com.qm.base.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Mr.M
 * @version 1.0
 * @description 本项目自定义异常类型
 * @date 2023/2/12 16:56
 */

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

    public static void cast(String message) {
        throw new LearnOnlineException(message);
    }

    public static void cast(CommonError error) {
        throw new LearnOnlineException(error.getErrMessage());
    }
}
