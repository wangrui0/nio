package com.nio.enums;

public enum BusinessExceptionEnum {
    BUFFER_OVER_FLOW(1001, "buffer越界");
    private Integer code;
    private String msg;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
