package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import easyJ.common.EasyJException;
import cn.edu.pku.dr.requirement.elicitation.action.AmbiguityIntercepter;

public class AmbiguityProxy implements InvocationHandler {
    public AmbiguityProxy() {}

    private Object target;

    AmbiguityIntercepter ai = new AmbiguityIntercepter();

    public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalArgumentException,
            IllegalAccessException, EasyJException {
        Object result = null;
        if (method.getName().equals("getAmbiguity")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("ambiguityUpdate")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("ambiguityEvaluation")) {

            result = method.invoke(target, args);
        }
        if (method.getName().equals("creatingAmbiguity")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("ambiguityTypeEvaluation")) {

            result = method.invoke(target, args);
        }

        if (method.getName().equals("viewAllAmbiguity")) {

            result = method.invoke(target, args);
        }

        return result;
    }

    public void setTarget(Object o) {
        this.target = o;
    }
}
