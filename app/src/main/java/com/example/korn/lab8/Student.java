package com.example.korn.lab8

public class Student {
    private String id;
    private String name;
    private String tel;
    private String std_email;


    public Student(String id,String name,String tel,String std_email){
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.std_email = std_email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStd_email() {
        return std_email;
    }

    public void setStd_email(String std_email) {
        this.std_email = std_email;
    }
}
