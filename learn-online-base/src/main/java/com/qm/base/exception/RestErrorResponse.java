package com.qm.base.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Mr.M
 * @version 1.0
 * @description 和前端约定返回的异常信息模型
 * @date 2023/2/12 16:55
 */
@Setter
@Getter
public class RestErrorResponse implements Serializable {
    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }
}
