package easyJ.http.config;

public class ModuleConfig {
    private ControllerConfig controllerConfig;

    protected String prefix = null;

    public ModuleConfig(String prefix) {
        super();
        this.prefix = prefix;
    }

    public ControllerConfig getControllerConfig() {
        return controllerConfig;
    }

    public void setControllerConfig(ControllerConfig controllerConfig) {
        this.controllerConfig = controllerConfig;
    }
}
