package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.border.Border;
/**
 * @author didikiki
 */

import java.awt.*;

public final class LozengeBorder implements Border {

    private Insets insets;

    private Color color;

    /*
     * public LozengeBorder (int w, Color color) { insets = new Insets (w/4*20,
     * 30, w/4*20, 30); this.color = color; }
     */
    public LozengeBorder(int top, int left, int bottom, int right, Color color) {
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
        int y = bounds.y + 2;
        int h = bounds.height - 3;
        int w = bounds.width;
        gr.drawLine(x, y + h / 2, x + w / 2, y);
        gr.drawLine(x + w / 2, y, x + w, y + h / 2);
        gr.drawLine(x + w + 2, y + h / 2, x + w / 2, y + h);
        gr.drawLine(x + w / 2, y + h, x, y + h / 2);
        /*
         * gr.drawLine(x,y-h/2,x+w/2,y); gr.drawLine(x+w/2,y,x+w,y-h/2);
         * gr.drawLine(x+w,y-h/2,x-w/2,y-h); gr.drawLine(x-w/2,y-h,x,y-h/2);
         * gr.drawLine(x-h, y-h/2, x+w/2, y+w/4); gr.drawLine(x+w/2, y+w/4,
         * x+w+h, y-h/2); gr.drawLine(x+w+h, y-h/2, x+w/2, y-h-w/4);
         * gr.drawLine(x+w/2, y-h-w/4, x-h, y-h/2);
         */
    }

    public boolean isOpaque() {
        return true;
    }

}
