package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.InvocationHandler;
import cn.edu.pku.dr.requirement.elicitation.action.ProblemValueIntercepter;
import java.lang.reflect.InvocationTargetException;
import easyJ.common.EasyJException;
import java.lang.reflect.Method;

public class ProblemValueProxy implements InvocationHandler {
    public ProblemValueProxy() {}

    private Object target;

    ProblemValueIntercepter pvi = new ProblemValueIntercepter();

    public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalArgumentException,
            IllegalAccessException, EasyJException {
        Object result = null;
        if (method.getName().equals("getProblemvalue")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("problemvalueUpdate")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("problemvalueEvaluation")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("problemvalueTypeEvaluation")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("viewAllProblemvalue")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("creatingProblemValue")) {

            result = method.invoke(target, args);
        }
        return result;
    }

    public void setTarget(Object o) {
        this.target = o;
    }

}
