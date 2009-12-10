package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.*;

public class ProblemValueProxyFactory {
    public ProblemValueProxyFactory() {}

    public static Object getProxy(Object object) {
        ProblemValueProxy avp = new ProblemValueProxy();
        avp.setTarget(object);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), avp);
    }

}
