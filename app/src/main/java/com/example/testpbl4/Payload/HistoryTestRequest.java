package com.example.testpbl4.Payload;

public class HistoryTestRequest {
    private int accountId;
    private int qgrDetailId;
    private int correctAnswer;
    private int inCorrectAnswer;
    private double score;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getQgrDetailId() {
        return qgrDetailId;
    }

    public void setQgrDetailId(int qgrDetailId) {
        this.qgrDetailId = qgrDetailId;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getInCorrectAnswer() {
        return inCorrectAnswer;
    }

    public void setInCorrectAnswer(int inCorrectAnswer) {
        this.inCorrectAnswer = inCorrectAnswer;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

