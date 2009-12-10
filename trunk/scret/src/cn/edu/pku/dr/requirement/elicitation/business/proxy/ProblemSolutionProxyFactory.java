package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.Proxy;

public class ProblemSolutionProxyFactory {
    public ProblemSolutionProxyFactory() {}

    public static Object getProxy(Object object) {
        ProblemSolutionProxy psp = new ProblemSolutionProxy();
        psp.setTarget(object);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), psp);
    }
}
