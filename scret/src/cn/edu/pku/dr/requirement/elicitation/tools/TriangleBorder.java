package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.border.Border;

import java.awt.*;

/**
 * @author didikiki
 */

public final class TriangleBorder implements Border {

    private Insets insets;

    private Color color;

    public TriangleBorder(int top, int left, int bottom, int right, Color color) {
        insets = new Insets(top, left, bottom, right);
        if (color == null)
            this.color = Color.BLACK;
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
        int x = bounds.x;
        int y = bounds.y;
        int h = bounds.height - 3;
        int w = bounds.width - 3;

        gr.drawOval(x, y, w, h);
        // gr.drawLine(x, y+h, x+w, y+h);
        // gr.drawLine(x, y+h, x+w/2, y);
        // gr.drawLine(x+w/2, y, x+w, y+h);
        // RenderUtil.drawRect (gr, bounds);
    }

    public boolean isOpaque() {
        return true;
    }

}
