package kz.ggi_dev.medium_test_project_for_boostbrain.service;

public class RestResponseForAnalysis {
    private String code;
    private String value;

    public RestResponseForAnalysis() {
    }

    public RestResponseForAnalysis(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
