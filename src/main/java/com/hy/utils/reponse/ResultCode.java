package com.hy.utils.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Michael Michael_evan2030@aliyun.com
 * @Date: 2019-11-07 23:11
 * @Description: Result Code Enum
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0, "操作成功"),

    FAILURE(-1,"Bussiness logic Error"),

    FAILURE_BAD_QEQUEST(HttpServletResponse.SC_BAD_REQUEST, "Biz Exception"),

    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "Request Unauthorized"),

    NOT_FOUND(HttpServletResponse.SC_NOT_FOUND, "404 Not Found"),

    MSG_NOT_READABLE(HttpServletResponse.SC_BAD_REQUEST, "Message Can't be Read"),

    METHOD_NOT_SUPPORTED(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method Not Supported"),

    MEDIA_TYPE_NOT_SUPPORTED(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Media Type Not Supported"),

    REQ_REJECT(HttpServletResponse.SC_FORBIDDEN, "Request Rejected"),

    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error"),

    PARAM_MISS(HttpServletResponse.SC_BAD_REQUEST, "Missing Required Parameter"),

    PARAM_TYPE_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Type Mismatch"),

    PARAM_BIND_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Binding Error"),

    PARAM_VALID_ERROR(HttpServletResponse.SC_BAD_REQUEST, "Parameter Validation Error"),

    SQL_EXCEPTION(-2,"数据操作异常"),

    ORDER_NO_CONFLICT(10001,"订单编号冲突，redis的订单编号数据需要迁移"),

    ADVANCE_AUDIT_FAILURE(20001,"预付单审核失败"),

    ADVANCE_PAY_FAILURE(20002,"预付单支付失败"),

    ADVANCE_PAYED(20003,"预付单已支付"),

    AC_AUDIT_FAILURE(30001,"结算单审核失败"),

    AC_PAY_FAILURE(30002,"结算单支付失败"),

    AC_PAYED(30003,"结算单已支付"),
    ZTF_FAIL(1,"系统异常"),

    API_INVALID(-10000,"非法请求，需要授权！"),

    WEBCHAT_LOGIN_FAILURE(11001,"当前手机号未登记，请联系物流人员"),
    WEBCHAT_UNAUTHORIZED(11002,"微信登录未授权"),
    TOKEN_IN_VALID(-401,"token失效，请重新登录"),
    MESSAGE_EXAM_FAILURE(200,"考核没通过"),

    OVER_EXPIRATION_TIME_ONE_SIXTH(12001,"已超保质期的1/6，是否按采购单价的95%收货入库?")
    ;

    final int code;

    final String msg;
}
