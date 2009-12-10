package easyJ.tools;

public class GeneratorFactory {
    public GeneratorFactory() {}

    public static Generator getGenerator() {
        return GeneratorSQLServerImpl.getInstance();
    }

}
