package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.State;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDragEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDropEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetFocusEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseWheelEvent;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

public class WholeDragAction implements WidgetAction {
    public WholeDragAction(DemoGraphScene scene) {
        this.scene = scene;
        this.area = new Widget(scene);
        area.setBorder(new SelectionBorder(0, 0, 0, 0, null));
        scene.setSelectedArea(area);
        selectedAreaStart = new Point(0, 0);
        selectedAreaEnd = new Point(0, 0);
        lastPosition = new Point(0, 0);
    }

    private DemoGraphScene scene;

    // ѡ������ʼʱ������λ��
    private Point selectedAreaStart;

    // ѡ���������ʱ������λ��
    private Point selectedAreaEnd;

    // ǰһ������¼���λ��
    private Point lastPosition;

    // ���ѡ�������
    private Widget area;

    // ��ѡ�������ڵĽ��
    private ArrayList<Widget> nodeList = new ArrayList<Widget>();

    // ��ѡ�������ڵ���Ч�ߣ��ߵ������յ㶼������������Ϊ��Ч��
    private ArrayList<ConnectionWidget> cwList = new ArrayList<ConnectionWidget>();

    // ��ѡ�������ڵĿ��Ƶ�
    private ArrayList<List<Point>> cpsList = new ArrayList<List<Point>>();

    // ��Ǳ�ѡ�����ڵĿ��Ƶ��Ƿ���Ч���ߵ������յ㶼������������Ϊ��Ч��
    private ArrayList<boolean[]> isCpsValid = new ArrayList<boolean[]>();

    // �������ѡ��������������ק����
    private boolean isSelecting = false;

    private Set selectedObjects = new TreeSet();

    public State mousePressed(Widget widget, WidgetMouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1
                && event.getClickCount() == 1) {
            if (!isInSelectedArea(event.getPoint())) {
                selectedAreaStart.x = event.getPoint().x;
                selectedAreaStart.y = event.getPoint().y;
                selectedAreaEnd.x = selectedAreaStart.x;
                selectedAreaEnd.y = selectedAreaStart.y;
                int w = 1;
                int h = 1;
                DemoGraphScene scene = (DemoGraphScene) widget;
                Dimension minimumSize = new Dimension(w, h);
                area.setMinimumSize(minimumSize);
                // area.setBorder(BorderFactory.createLineBorder (0));

                area.setPreferredLocation(selectedAreaStart);
                scene.addChild(area);
                isSelecting = true;

                // ��ԭѡ����������ݱ�غ�ɫ
                for (Widget lw: nodeList) {
                    Border border = lw.getBorder();
                    if (border instanceof LozengeBorder)
                        ((LozengeBorder) border).setColor(Color.BLACK);
                    if (border instanceof TriangleBorder)
                        ((TriangleBorder) border).setColor(Color.BLACK);
                    if (border instanceof RectangleBorder)
                        ((RectangleBorder) border).setColor(Color.BLACK);
                    lw.setForeground(null);
                    lw.repaint();
                }
                for (ConnectionWidget cw: cwList) {
                    cw.setLineColor(Color.black);
                    cw.setPaintControlPoints(false);
                }
                selectedObjects.clear();
                scene.setSelectedObjects(selectedObjects);
                nodeList.clear();
                cwList.clear();
                cpsList.clear();
                isCpsValid.clear();
                area.setVisible(false);
            } else {
                isSelecting = false;
            }
        }
        lastPosition.x = event.getPoint().x;
        lastPosition.y = event.getPoint().y;

        return State.CONSUMED;
    }

    private boolean isInSelectedArea(Point p) {
        if (((selectedAreaStart.x <= p.x && p.x <= selectedAreaEnd.x) || (selectedAreaStart.x >= p.x && p.x >= selectedAreaEnd.x))
                && ((selectedAreaStart.y <= p.y && p.y <= selectedAreaEnd.y) || (selectedAreaStart.y >= p.y && p.y >= selectedAreaEnd.y)))
            return true;
        return false;
    }

    public State mouseReleased(Widget widget, WidgetMouseEvent event) {
        if (isSelecting) {
            selectedAreaEnd.x = event.getPoint().x;
            selectedAreaEnd.y = event.getPoint().y;
            Collection<String> nodes = scene.getNodes();
            for (String id: nodes) {
                Widget lw = scene.findWidget(id);
                Rectangle rect = lw.getPreferredBounds();
                Point location = lw.getPreferredLocation();
                Point pa = new Point(location.x + rect.x, location.y + rect.y);
                Point pb = new Point(location.x + rect.x + rect.width,
                        location.y + rect.y + rect.height);
                if (isInSelectedArea(pa) && isInSelectedArea(pb)) {
                    nodeList.add(lw);
                }
            }

            Collection<String> edges = scene.getEdges();
            for (String id: edges) {
                ConnectionWidget cw = (ConnectionWidget) scene.findWidget(id);
                boolean isSourceIn;
                boolean isTargetIn;

                LabelWidget source = (LabelWidget) scene.findWidget(scene
                        .getEdgeSource(id));
                Rectangle rect = source.getPreferredBounds();
                Point location = source.getPreferredLocation();
                Point pa = new Point(location.x + rect.x, location.y + rect.y);
                Point pb = new Point(location.x + rect.x + rect.width,
                        location.y + rect.y + rect.height);
                isSourceIn = isInSelectedArea(pa) && isInSelectedArea(pb);

                LabelWidget target = (LabelWidget) scene.findWidget(scene
                        .getEdgeTarget(id));
                rect = target.getPreferredBounds();
                location = target.getPreferredLocation();
                pa = new Point(location.x + rect.x, location.y + rect.y);
                pb = new Point(location.x + rect.x + rect.width, location.y
                        + rect.y + rect.height);
                isTargetIn = isInSelectedArea(pa) && isInSelectedArea(pb);

                if (!isSourceIn && !isTargetIn)
                    continue;
                cwList.add(cw);
                List<Point> cps = cw.getControlPoints();
                boolean isCpIn[] = new boolean[cps.size()];
                cpsList.add(cps);
                for (int i = 0; i < cps.size(); ++i) {
                    if (isInSelectedArea(cps.get(i)))
                        isCpIn[i] = true;
                    else
                        isCpIn[i] = false;
                }
                if (!isSourceIn)
                    isCpIn[0] = false;
                if (!isTargetIn)
                    isCpIn[cps.size() - 1] = false;

                isCpsValid.add(isCpIn);
            }

            for (Widget lw: nodeList) {
                Border border = lw.getBorder();
                if (border instanceof LozengeBorder)
                    ((LozengeBorder) border).setColor(Color.blue);
                if (border instanceof TriangleBorder)
                    ((TriangleBorder) border).setColor(Color.blue);
                if (border instanceof RectangleBorder)
                    ((RectangleBorder) border).setColor(Color.blue);
                lw.setForeground(Color.blue);
                lw.repaint();
            }
            for (ConnectionWidget cw: cwList) {
                cw.setLineColor(Color.blue);
                cw.setPaintControlPoints(true);
            }

        }

        isSelecting = false;
        return State.REJECTED;
    }

    public State mouseClicked(Widget widget, WidgetMouseEvent event) {
        return State.CONSUMED;
    }

    public State mouseDragged(Widget widget, WidgetMouseEvent event) {
        // �ж��û��Ƿ�����ѡ��ͼԪ����
        if (isSelecting) {
            area.setVisible(true);
            selectedAreaEnd.x = event.getPoint().x;
            selectedAreaEnd.y = event.getPoint().y;
            DemoGraphScene scene = (DemoGraphScene) widget;
            int w = selectedAreaEnd.x - selectedAreaStart.x;
            int h = selectedAreaEnd.y - selectedAreaStart.y;
            if (w >= 0 && h >= 0) {
                Dimension minimumSize = new Dimension(w, h);
                area.setMinimumSize(minimumSize);
                area.setPreferredLocation(selectedAreaStart);
            } else if (w >= 0 && h <= 0) {
                Dimension minimumSize = new Dimension(w, -h);
                area.setMinimumSize(minimumSize);
                area.setPreferredLocation(new Point(selectedAreaStart.x,
                        selectedAreaEnd.y));
            } else if (w <= 0 && h >= 0) {
                Dimension minimumSize = new Dimension(-w, h);
                area.setMinimumSize(minimumSize);
                area.setPreferredLocation(new Point(selectedAreaEnd.x,
                        selectedAreaStart.y));
            } else {// w<=0 && h<=0
                Dimension minimumSize = new Dimension(-w, -h);
                area.setMinimumSize(minimumSize);
                area.setPreferredLocation(selectedAreaEnd);
            }
            scene.addChild(area);
        }
        // �����ƶ�ѡ�������ڵ�ͼԪ
        else {
            int x = area.getPreferredLocation().x += event.getPoint().x
                    - lastPosition.x;
            int y = area.getPreferredLocation().y += event.getPoint().y
                    - lastPosition.y;
            area.setPreferredLocation(new Point(x, y));

            for (Widget lw: nodeList) {
                int xx = lw.getPreferredLocation().x + event.getPoint().x
                        - lastPosition.x;
                int yy = lw.getPreferredLocation().y + event.getPoint().y
                        - lastPosition.y;
                lw.setPreferredLocation(new Point(xx, yy));

            }
            for (int i = 0; i < cwList.size(); ++i) {
                ConnectionWidget cw = cwList.get(i);
                List<Point> cps = cpsList.get(i);
                boolean[] isCpIn = isCpsValid.get(i);
                for (int j = 0; j < cps.size(); ++j)
                    if (isCpIn[j]) {
                        cps.get(j).x += event.getPoint().x - lastPosition.x;
                        cps.get(j).y += event.getPoint().y - lastPosition.y;
                    }
                cw.setControlPoints(cps, true);
            }
            selectedAreaStart.x += event.getPoint().x - lastPosition.x;
            selectedAreaStart.y += event.getPoint().y - lastPosition.y;
            selectedAreaEnd.x += event.getPoint().x - lastPosition.x;
            selectedAreaEnd.y += event.getPoint().y - lastPosition.y;
            /*
             * for(List<Point> cps:cpsList){ cps.x += event.getPoint().x -
             * lastPosition.x; cps.y += event.getPoint().y - lastPosition.y; }
             */

            /*
             * Collection<String> nodes = scene.getNodes(); for(String
             * label:nodes){ LabelWidget lw =
             * (LabelWidget)scene.findWidget(label);
             * if(isInSelectedArea(lw.getPreferredLocation())){ int xx =
             * lw.getPreferredLocation().x + event.getPoint().x -
             * lastPosition.x; int yy = lw.getPreferredLocation().y +
             * event.getPoint().y - lastPosition.y; lw.setPreferredLocation(new
             * Point(xx, yy)); } }
             */
        }

        // scene.validate();
        lastPosition.x = event.getPoint().x;
        lastPosition.y = event.getPoint().y;

        return State.CONSUMED;
    }

    public State mouseMoved(Widget widget, WidgetMouseEvent event) {
        return State.CHAIN_ONLY;
    }

    public State mouseEntered(Widget widget, WidgetMouseEvent event) {
        return State.CHAIN_ONLY;
    }

    public State mouseExited(Widget widget, WidgetMouseEvent event) {
        return State.CHAIN_ONLY;
    }

    public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent event) {
        return State.CHAIN_ONLY;
    }

    public State keyTyped(Widget widget, WidgetKeyEvent event) {
        return State.CHAIN_ONLY;
    }

    public State keyPressed(Widget widget, WidgetKeyEvent event) {
        return State.CHAIN_ONLY;
    }

    public State keyReleased(Widget widget, WidgetKeyEvent event) {
        return State.CHAIN_ONLY;
    }

    public State focusGained(Widget widget, WidgetFocusEvent event) {
        return State.CHAIN_ONLY;
    }

    public State focusLost(Widget widget, WidgetFocusEvent event) {
        return State.CHAIN_ONLY;
    }

    public State dragEnter(Widget widget, WidgetDropTargetDragEvent event) {
        return State.CHAIN_ONLY;
    }

    public State dragOver(Widget widget, WidgetDropTargetDragEvent event) {
        return State.CHAIN_ONLY;
    }

    public State dropActionChanged(Widget widget,
            WidgetDropTargetDragEvent event) {
        return State.CHAIN_ONLY;
    }

    public State dragExit(Widget widget, WidgetDropTargetEvent event) {
        return State.CHAIN_ONLY;
    }

    public State drop(Widget widget, WidgetDropTargetDropEvent event) {
        return State.CHAIN_ONLY;
    }
}
