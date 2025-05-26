package com.randolflu.atm;

public class Account {
    //  卡号
    private String id;
    //  密码
    private String pwd;
    //  余额
    private double balance;
    //  姓名
    private String name;
    //  性别
    private String gender;
    //  年龄
    private String age;
    //  电话
    private String phone;
    //  地址
    private String addr;
    //  身份证号码
    private String identity;
    //  限额
    private double limit;
    /* 构造方法 */


    public Account() {
    }

    public Account(String id, String pwd, double balance, String name, String gender, String age, String phone, String addr, String identity, double limit) {
        this.id = id;
        this.pwd = pwd;
        this.balance = balance;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.addr = addr;
        this.identity = identity;
        this.limit = limit;
    }

    /* Getter and setter */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", addr='" + addr + '\'' +
                ", identity='" + identity + '\'' +
                ", limit=" + limit +
                '}';
    }
}
