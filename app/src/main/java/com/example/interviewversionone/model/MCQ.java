package com.example.interviewversionone.model;

public class MCQ {
    String Question, Answer, mcqId;

    public MCQ() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }

    public String getMcqId() {
        return mcqId;
    }

    public void setMcqId(String mcqId) {
        this.mcqId = mcqId;
    }
}
