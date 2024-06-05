package com.lagou.demo;

/**
 * @Author: Jaa
 * @Description:
 * @Date 2024/6/1
 */
public class KouZhao {

    private int id;
    private String type;

    @Override
    public String toString() {
        return "KouZhao{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
