package com.hy.utils.exception;

import com.hy.utils.gson.GsonUtils;
import com.hy.utils.reponse.ResultCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Michael Michael_evan2030@aliyun.com
 * @Date: 2019-11-07 23:12
 * @Description: Business Service Exception
 */
@Slf4j
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 2359767895161832954L;

    @Getter
    private final ResultCode resultCode;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(String message, Object ... params) {
        super(message);
        StringBuilder sb = new StringBuilder(message);
        List<Object> list = new ArrayList<>();
        for (Object param : params) {
            list.add(param);
        }
        log.error(sb.append(GsonUtils.toJson(list)).toString());
        this.resultCode = ResultCode.FAILURE;
    }

    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }

    public ServiceException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
        this.resultCode = ResultCode.FAILURE;
    }

    /**
     * for better performance
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
