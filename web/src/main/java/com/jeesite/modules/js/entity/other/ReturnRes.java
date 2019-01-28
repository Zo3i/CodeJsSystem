package com.jeesite.modules.js.entity.other;

public class ReturnRes {
    private Boolean isWrong;
    private Boolean isRight;
    private String answer;


    public Boolean getWrong() {
        return isWrong;
    }

    public void setWrong(Boolean wrong) {
        isWrong = wrong;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
