package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.Observable;

public class BusinessObject extends Observable {
    private String msg = "";

    public BusinessObject() {}

    public void createSystemMessage() {
        super.setChanged();
        notifyObservers(this.msg);
    }

    public void setMessage(String msg) {
        this.msg = msg;
        createSystemMessage();
    }

    public String getMsg() {
        return this.msg;

    }

}
