package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.modules.visual.util.RenderUtil;
import org.netbeans.api.visual.border.Border;

import java.awt.*;

public final class EditorBorder implements Border {

    private Insets insets;

    private Color color;

    public EditorBorder(int top, int left, int bottom, int right, Color color) {
        insets = new Insets(top, left, bottom, right);
        if (color == null)
            this.color = Color.black;
        else
            this.color = color;
    }

    public Insets getInsets() {
        return insets;
    }

    public void paint(Graphics2D gr, Rectangle bounds) {
        gr.setColor(color);
        // gr.rotate(Math.toRadians(-90),bounds.getCenterX(),bounds.getCenterY());
        // RenderUtil.drawRect (gr, bounds);
    }

    public boolean isOpaque() {
        return true;
    }

}
