package com.example.testpbl4.Payload;

public class QuestionGrDetailRespone {
    private int id;
    private String name_gr_detail;
    private String description;
    private int number_question;
    private int time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_gr_detail() {
        return name_gr_detail;
    }

    public void setName_gr_detail(String name_gr_detail) {
        this.name_gr_detail = name_gr_detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber_question() {
        return number_question;
    }

    public void setNumber_question(int number_question) {
        this.number_question = number_question;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
