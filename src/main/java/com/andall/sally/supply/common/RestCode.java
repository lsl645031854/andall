package com.andall.sally.supply.common;

public enum RestCode {
  OK(0,"OK"),
  UNKNOWN_ERROR(1, "未知异常"),
  TOKEN_CHECK_FAILED(100104, "token失效");
    public final int code;
    public final String msg;

    private RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
