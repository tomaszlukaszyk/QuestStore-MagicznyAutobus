package com.codecool.queststore.controller.server.httphandler;

public enum ResponsesEnum {

    REDIRECT(302),
    NF(404),
    OK(200);

    private final int code;

    ResponsesEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
