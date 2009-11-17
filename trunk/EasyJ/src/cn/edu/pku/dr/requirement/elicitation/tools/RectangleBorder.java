package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.border.Border;
import org.netbeans.modules.visual.util.RenderUtil;
/**
 * @author didikiki
 */

import java.awt.*;

public final class RectangleBorder implements Border {

    private Insets insets;

    private Color color;

    public RectangleBorder(int top, int left, int bottom, int right, Color color) {
        insets = new Insets(top, left, bottom, right);
        if (color == null)
            this.color = Color.black;
        else
            this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Insets getInsets() {
        return insets;
    }

    public void paint(Graphics2D gr, Rectangle bounds) {
        gr.setColor(color);
        RenderUtil.drawRect(gr, bounds);
        // gr.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        /*
         * int x = bounds.x; int y = bounds.y; int h = bounds.height-3; int w =
         * bounds.width; gr.drawLine(x, y+h/2, x+w/2, y); gr.drawLine(x+w/2, y,
         * x+w, y+h/2); gr.drawLine(x+w, y+h/2, x+w/2, y+h); gr.drawLine(x+w/2,
         * y+h, x, y+h/2);
         */

    }

    public boolean isOpaque() {
        return true;
    }

}
