package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.util.GeomUtil;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class EditorWidget extends Widget {

    private String label;

    private boolean paintAsDisabled;

    /**
     * Creates a label widget.
     * 
     * @param scene
     *                the scene
     */
    public EditorWidget(Scene scene) {
        this(scene, null);
    }

    /**
     * Creates a label widget with a label.
     * 
     * @param scene
     *                the scene
     * @param label
     *                the label
     */
    public EditorWidget(Scene scene, String label) {
        super(scene);
        setOpaque(false);
        // setCursor (new Cursor (Cursor.TEXT_CURSOR));
        setLabel(label);
        setCheckClipping(false);
    }

    /**
     * Returns a label.
     * 
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets a label.
     * 
     * @param label
     *                the label
     */
    public void setLabel(String label) {
        if (GeomUtil.equals(this.label, label))
            return;
        this.label = label;
        revalidate();
    }

    /**
     * Returns whether the label is painted as disabled.
     * 
     * @return true, if the label is painted as disabled
     */
    public boolean isPaintAsDisabled() {
        return paintAsDisabled;
    }

    /**
     * Sets whether the label is painted as disabled.
     * 
     * @param paintAsDisabled
     *                if true, then the label is painted as disabled
     */
    public void setPaintAsDisabled(boolean paintAsDisabled) {
        boolean repaint = this.paintAsDisabled != paintAsDisabled;
        this.paintAsDisabled = paintAsDisabled;
        if (repaint)
            repaint();
    }

    /**
     * Calculates a client area for the label.
     * 
     * @return the client area
     */
    protected Rectangle calculateClientArea() {
        if (label == null)
            return super.calculateClientArea();
        Graphics2D gr = getGraphics();
        FontMetrics fontMetrics = gr.getFontMetrics(getFont());
        Rectangle2D rectangle = fontMetrics.getStringBounds(label, gr);
        int x1 = (int) Math.floor(rectangle.getX());
        int y1 = (int) Math.floor(rectangle.getY());
        int x2 = (int) Math.ceil(rectangle.getMaxX());
        int y2 = (int) Math.ceil(rectangle.getMaxY());
        return new Rectangle(x1, y1, y2 - y1, x2 - x1);

    }

    /**
     * Paints the label widget.
     */
    protected void paintWidget() {
        if (label == null)
            return;
        Graphics2D gr = getGraphics();
        gr.setFont(getFont());

        FontMetrics fontMetrics = gr.getFontMetrics();
        Rectangle clientArea = getClientArea();
        if (fontMetrics.stringWidth(label) > this.maxY) {
            while (fontMetrics.stringWidth(label) > this.maxY
                    && label.length() > 2)
                label = label.substring(0, label.length() - 2);
            label += "..";
        }
        int fix = clientArea.y + (clientArea.height - fontMetrics.getDescent())
                / 2;

        int x = -fontMetrics.stringWidth(label) / 2;
        int y = 18;

        gr.rotate(-Math.PI / 2);
        Paint background = getBackground();
        if (paintAsDisabled && background instanceof Color) {
            Color color = ((Color) background);
            gr.setColor(color.brighter());
            gr.drawString(label, x + 1, y + 1);
            gr.setColor(color.darker());
            gr.drawString(label, x, y);
        } else {
            gr.setColor(getForeground());
            gr.drawString(label, x, y);
        }

        gr.translate(fix, 0);
        gr.rotate(Math.PI / 2);
        // gr.drawRect(clientArea.x, clientArea.y, clientArea.width,
        // clientArea.height);
        this.revalidate();
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    private int maxY = 100;

}
