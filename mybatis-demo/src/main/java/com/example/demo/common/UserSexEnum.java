package com.example.demo.common;

/**
 * @author xiexingxing
 * @Created by 2019-06-02 10:56 PM.
 */
public enum UserSexEnum {
    MAN(0, "男人"), WOMA(1, "女人");

    private Integer value;
    private String name;


    UserSexEnum(Integer value, String name) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
