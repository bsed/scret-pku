package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class AppletStart extends JApplet {
    public void init() {
        // AppletStart mainFrame = new AppletStart();
        // FreeConnectTest.main(null);
        // this.nodeFileName = this.getParameter("node");
        // this.edgeFileName = this.getParameter("edge");

        MyMouseWheel l = new MyMouseWheel();
        this.addMouseWheelListener(l);
        MyKey k = new MyKey();
        this.addKeyListener(k);
        FreeConnectTest.runAsApplet(this);
        this.setLocation(new Point(0, 0));
    }

    public void refresh() {
        FreeConnectTest.refresh(this);

    }

    public String nodeFileName = "NodesFile.txt";

    public String edgeFileName = "EdgesFile.txt";

    class MyKey implements KeyListener {

        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyChar());

        }

        public void keyReleased(KeyEvent e) {
            System.out.println(e.getKeyChar());

        }

        public void keyTyped(KeyEvent e) {
            System.out.println(e.getKeyChar());

        }

    }

    class MyMouseWheel implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            System.out.println(e.getWheelRotation());
        }

    }

    public static String nodesFile, edgesFile;
}
