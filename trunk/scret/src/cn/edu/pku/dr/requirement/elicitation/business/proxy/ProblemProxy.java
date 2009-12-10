package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import cn.edu.pku.dr.requirement.elicitation.action.ProblemIntercepter;
import cn.edu.pku.dr.requirement.elicitation.data.Problem;
import easyJ.common.EasyJException;

public class ProblemProxy implements InvocationHandler {

    private Object target;

    ProblemIntercepter pi = new ProblemIntercepter();

    public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalArgumentException,
            IllegalAccessException, EasyJException {
        Object result = null;
        if (method.getName().equals("getProblem")) {

            boolean[] judgeResult = new boolean[2];
            judgeResult = pi.judgeProblem((Problem) args[0]);
            if (judgeResult[0] == true) {
                args[1] = new Boolean("true");
            }
            if (judgeResult[1] == true) {
                args[2] = new Boolean("true");
            }

            result = method.invoke(target, args);
        }

        if (method.getName().equals("newProblem")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("getAmbiguity")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("modifyProblem")) {

            result = method.invoke(target, args);
        }

        return result;
    }

    public void setTarget(Object o) {
        this.target = o;
    }
}
