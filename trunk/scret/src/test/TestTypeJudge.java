package test;

import easyJ.common.validate.GenericValidator;
import java.util.ArrayList;

public class TestTypeJudge {
    public TestTypeJudge() {}

    public static void main(String[] args) {
        try {
            GenericValidator.isSetType(ArrayList.class);
        } catch (Exception e) {

        }
    }
}
