package test;

import easyJ.tools.*;

public class Test {
    public Test() {}

    public static void main(String[] args) throws Exception {

        Generator generator = GeneratorFactory.getGenerator();
        System.out.println(generator.generateBean("use_case",
                "use_case"));

    }
}
