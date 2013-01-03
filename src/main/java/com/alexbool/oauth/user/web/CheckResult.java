package com.alexbool.oauth.user.web;

public class CheckResult {

    private final boolean result;

    public CheckResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
