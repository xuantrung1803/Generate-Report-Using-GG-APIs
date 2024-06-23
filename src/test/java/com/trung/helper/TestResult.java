package com.trung.helper;

public class TestResult {
    private final String result;
    private final long duration;

    public TestResult(String result, long duration) {
        this.result = result;
        this.duration = duration;
    }

    public String getResult() {
        return result;
    }

    public long getDuration() {
        return duration;
    }
}
