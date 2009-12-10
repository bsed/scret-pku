package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.InvocationHandler;

import cn.edu.pku.dr.requirement.elicitation.action.ProblemReasonIntercepter;
import java.lang.reflect.InvocationTargetException;
import easyJ.common.EasyJException;
import java.lang.reflect.Method;

public class ProblemReasonProxy implements InvocationHandler {
    public ProblemReasonProxy() {}

    private Object target;

    ProblemReasonIntercepter pri = new ProblemReasonIntercepter();

    public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalArgumentException,
            IllegalAccessException, EasyJException {
        Object result = null;
        if (method.getName().equals("getProblemreason")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("problemreasonUpdate")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("viewAllProblemreason")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("creatingProblemReason")) {

            result = method.invoke(target, args);
        }
        return result;
    }

    public void setTarget(Object o) {
        this.target = o;
    }

}
