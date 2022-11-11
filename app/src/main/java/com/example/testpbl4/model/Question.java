package com.example.testpbl4.model;

import java.io.Serializable;

public class Question implements Serializable {
    private int qgroupDetailId;
    private int num;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correct_answer;
    private String answer ="";
    private int checkedID = -1;

    public int getCheckedID() {
        return checkedID;
    }

    public void setCheckedID(int checkedID) {
        this.checkedID = checkedID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQgroupDetailId() {
        return qgroupDetailId;
    }

    public void setQgroupDetailId(int qgroupDetailId) {
        this.qgroupDetailId = qgroupDetailId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public String getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correct_answer = correctAnswer;
    }
}
