package cn.edu.pku.dr.requirement.elicitation.action;

import java.util.Observer;
import java.util.Observable;
import cn.edu.pku.dr.requirement.elicitation.data.Message;
import easyJ.common.EasyJException;

public class SystemMessage implements Observer {
    Observable observable;

    private String msg;

    public SystemMessage(Observable observable) {
        this.observable = observable;
        observable.addObserver(this);
    }

    public void update(Observable obs, Object arg) {

        if (arg instanceof String) {
            this.msg = (String) arg;
            try {
                this.notice();
            } catch (EasyJException e) {

            }
        }
    }

    public void notice() throws EasyJException {
        MessageAction messageaction = new MessageAction();
        messageaction.testMessage(this.msg);
    }
}
