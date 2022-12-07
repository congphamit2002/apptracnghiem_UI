package com.example.testpbl4.Payload;

import java.util.Date;

public class HistoryTestRespone {
    private int correct_answer;
    private int incorrect_answer;
    private double score;
    private String name_gr_detail;
    private int id;

    public String getName_gr_detail() {
        return name_gr_detail;
    }

    public void setName_gr_detail(String name_gr_detail) {
        this.name_gr_detail = name_gr_detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public int getIncorrect_answer() {
        return incorrect_answer;
    }

    public void setIncorrect_answer(int incorrect_answer) {
        this.incorrect_answer = incorrect_answer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
