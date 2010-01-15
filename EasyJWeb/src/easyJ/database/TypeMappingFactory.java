package easyJ.database;

public class TypeMappingFactory {
    public TypeMappingFactory() {}

    public static TypeMapping getTypeMapping() {
        return TypeMappingSQLServerImpl.getInstance();
    }

}
