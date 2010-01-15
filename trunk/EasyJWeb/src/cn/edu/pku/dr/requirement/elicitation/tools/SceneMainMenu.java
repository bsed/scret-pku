package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.Widget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author didikiki
 */

public class SceneMainMenu implements PopupMenuProvider, ActionListener {

    private static final String ADD_NEW_RECTANGLE_NODE_ACTION = "addNewRectangleNodeAction";

    private static final String ADD_NEW_LOZENGE_NODE_ACTION = "addNewLozengeNodeAction";

    private static final String ADD_NEW_TRIANGLE_NODE_ACTION = "addNewTriangleNodeAction";

    private DemoGraphScene scene;

    private JPopupMenu menu;

    private Point point;

    // private static int nodeCount=3;

    public SceneMainMenu(DemoGraphScene scene) {
        this.scene = scene;
        menu = new JPopupMenu("Scene Menu");
        JMenuItem item1, item2, item3;

        item1 = new JMenuItem("������ν��");
        item1.setActionCommand(ADD_NEW_RECTANGLE_NODE_ACTION);
        item1.addActionListener(this);
        menu.add(item1);

        item2 = new JMenuItem("�������ν��");
        item2.setActionCommand(ADD_NEW_LOZENGE_NODE_ACTION);
        item2.addActionListener(this);
        menu.add(item2);

        item3 = new JMenuItem("������Բ�ν��");
        item3.setActionCommand(ADD_NEW_TRIANGLE_NODE_ACTION);
        item3.addActionListener(this);
        menu.add(item3);
    }

    public JPopupMenu getPopupMenu(Widget widget, Point point) {
        this.point = point;
        return menu;
    }

    public void actionPerformed(ActionEvent e) {
        if (ADD_NEW_RECTANGLE_NODE_ACTION.equals(e.getActionCommand())) {
            String hm = "Node" + System.currentTimeMillis();
            Widget newNode = scene.addNode(hm, DemoGraphScene.RECTANGLE_NODE);
            newNode.setPreferredLocation(point);
            // scene.getSceneAnimator().animatePreferredLocation(newNode,point);
            scene.validate();
        } else if (ADD_NEW_LOZENGE_NODE_ACTION.equals(e.getActionCommand())) {
            String hm = "Node" + System.currentTimeMillis();;
            Widget newNode = scene.addNode(hm, DemoGraphScene.LOZENGE_NODE);
            newNode.setPreferredLocation(point);
            // scene.getSceneAnimator().animatePreferredLocation(newNode,point);
            scene.validate();
        } else if (ADD_NEW_TRIANGLE_NODE_ACTION.equals(e.getActionCommand())) {
            String hm = "Node" + System.currentTimeMillis();;
            Widget newNode = scene.addNode(hm, DemoGraphScene.TRIANGLE_NODE);
            newNode.setPreferredLocation(point);
            // scene.getSceneAnimator().animatePreferredLocation(newNode,point);
            scene.validate();
        }
    }
}
