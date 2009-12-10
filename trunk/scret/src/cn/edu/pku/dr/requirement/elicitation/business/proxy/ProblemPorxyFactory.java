package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.*;

public class ProblemPorxyFactory {
    public ProblemPorxyFactory() {}

    public static Object getProxy(Object object) {
        ProblemProxy pp = new ProblemProxy();
        pp.setTarget(object);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), pp);
    }
}
