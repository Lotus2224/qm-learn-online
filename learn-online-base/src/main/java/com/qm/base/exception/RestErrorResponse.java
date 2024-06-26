package com.qm.base.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

// 和前端约定返回的异常信息模型
@Setter
@Getter
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }
}
