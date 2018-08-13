package com.wangxin.rabbitmq.demo.rabbitmqdemo.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:jzwx
 * @Desicription: TestUser
 * @Date:Created in 2018-08-08 13:49
 * @Modified By:
 */
public class TestUser implements Serializable{
    private static final long serialVersionUID = 3350268298260406731L;
    private String userName;
    private String name;
    private String age;
    private String phone;
    private String sex;
    private Date time;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TestUser{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", time=" + time +
                '}';
    }
}
