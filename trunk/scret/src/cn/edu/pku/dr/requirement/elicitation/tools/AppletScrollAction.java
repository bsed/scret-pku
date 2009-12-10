package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.event.MouseEvent;

import javax.swing.JApplet;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseWheelEvent;

/**
 * @author William Headrick, David Kaspar
 */
public final class AppletScrollAction extends WidgetAction.Adapter {
    private int scrollBarValue = 0;

    private JScrollBar scrollBar = null;

    public AppletScrollAction(final JScrollPane x) {
        this.scrollBar = x.getHorizontalScrollBar();
    }

    public State mousePressed(Widget widget, WidgetMouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1
                && event.getClickCount() == 2) {
            this.scrollBar.setValue(0);
            this.scrollBarValue = 0;
        }
        if (event.getButton() == MouseEvent.BUTTON3
                && event.getClickCount() == 2) {
            this.scrollBar.setValue(this.scrollBar.getMaximum());
            this.scrollBarValue = this.scrollBar.getMaximum();
        }

        return State.CONSUMED;
    }

    public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent event) {
        this.scrollBarValue += 50 * event.getWheelRotation();
        if (this.scrollBarValue < 0)
            this.scrollBarValue = 0;
        if (this.scrollBarValue > this.scrollBar.getMaximum())
            this.scrollBarValue = this.scrollBar.getMaximum();
        this.scrollBar.setValue(this.scrollBarValue);
        return State.CONSUMED;
    }

    public State keyPressed(Widget widget, WidgetKeyEvent event) {
        if (event.getKeyChar() == '=')
            this.scrollBarValue += 50;
        if (event.getKeyChar() == '-')
            this.scrollBarValue -= 50;
        if (this.scrollBarValue < 0)
            this.scrollBarValue = 0;
        if (this.scrollBarValue > this.scrollBar.getMaximum())
            this.scrollBarValue = this.scrollBar.getMaximum();
        this.scrollBar.setValue(this.scrollBarValue);
        return State.CONSUMED;
    }

}
