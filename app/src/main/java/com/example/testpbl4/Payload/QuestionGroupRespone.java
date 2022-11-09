package com.example.testpbl4.Payload;

public class QuestionGroupRespone {
    private int id;
    private String nameGroup;
    private SubjectRespone subjectRespone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public SubjectRespone getSubjectRespone() {
        return subjectRespone;
    }

    public void setSubjectRespone(SubjectRespone subjectRespone) {
        this.subjectRespone = subjectRespone;
    }
}
