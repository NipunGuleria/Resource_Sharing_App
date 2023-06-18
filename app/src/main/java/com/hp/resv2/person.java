package com.hp.resv2;

public class person {
    private String name;
    private String desc;

    public person(String name, String desc){
        this.name= name;
        this.desc= desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
