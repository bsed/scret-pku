package easyJ.http.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import easyJ.common.validate.GenericValidator;
import easyJ.common.EasyJException;
import javax.servlet.ServletContext;

public class FowardAction extends ServletAction {
    public FowardAction() {}

    public void process(HttpServletRequest request,
            HttpServletResponse response, ServletContext application)
            throws EasyJException {
        this.request = request;
        this.response = response;
        this.application = application;
        String url = request.getParameter("jspurl");
        if (!GenericValidator.isBlankOrNull(url)) {
            this.returnPath = url;
        }
        super.processReturn();
    }

    public boolean encapsulateObject() {
        return true;
    }
}
