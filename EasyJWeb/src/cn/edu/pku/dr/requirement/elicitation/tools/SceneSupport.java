package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.widget.Scene;
import org.openide.util.RequestProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author didikiki
 */
public class SceneSupport {

    public static void show(final Scene scene) {
        JComponent sceneView = scene.getView();
        if (sceneView == null)
            sceneView = scene.createView();
        show(sceneView);
    }

    public static void show(final JComponent sceneView) {
        JScrollPane panel = new JScrollPane(sceneView);
        panel.getHorizontalScrollBar().setUnitIncrement(32);
        panel.getHorizontalScrollBar().setBlockIncrement(256);
        panel.getVerticalScrollBar().setUnitIncrement(32);
        panel.getVerticalScrollBar().setBlockIncrement(256);
        showCore(panel);
    }

    public static void showCore(final JComponent view) {
        int width = 800, height = 600;
        frame = new JFrame();// new JDialog (), true);
        /*
         * view.addMouseListener(new MouseAdapter(){ public void
         * mousePressed(MouseEvent ev){ System.out.println("***"); } public void
         * mouseReleased(MouseEvent ev){ System.out.println("***"); } public
         * void mouseEntered(MouseEvent ev){ System.out.println("***"); } });
         * frame.addMouseListener(new MouseAdapter(){ public void
         * mousePressed(MouseEvent ev){ System.out.println("***"); } public void
         * mouseReleased(MouseEvent ev){ System.out.println("***"); } public
         * void mouseEntered(MouseEvent ev){ System.out.println("***"); } });
         * System.out.println("****");
         */
        frame.add(view, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
                .getScreenSize();
        frame.setBounds((screenSize.width - width) / 2,
                (screenSize.height - height) / 2, width, height);
        frame.setVisible(true);
    }

    public static void repaint() {
        frame.repaint();
    }

    public static int randInt(int max) {
        return (int) (Math.random() * max);
    }

    public static void invokeLater(final Runnable runnable, int delay) {
        RequestProcessor.getDefault().post(new Runnable() {
            public void run() {
                SwingUtilities.invokeLater(runnable);
            }
        }, delay);
    }

    public static JFrame frame;

}
