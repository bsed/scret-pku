package cn.edu.pku.dr.requirement.elicitation.business.proxy;

import java.lang.reflect.*;

public class ProblemReasonProxyFactory {
    public ProblemReasonProxyFactory() {}

    public static Object getProxy(Object object) {
        ProblemReasonProxy arp = new ProblemReasonProxy();
        arp.setTarget(object);
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), arp);
    }

}
