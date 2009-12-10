package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.RequestProcessor;

/**
 * @author didikiki
 */

public class FreeConnectTest extends javax.swing.JPanel {
    private DemoGraphScene scene;

    private javax.swing.JScrollPane viewPanel;

    protected JComponent view;

    public FreeConnectTest() {

        // parse();

        scene = new DemoGraphScene();
        // �����ɾ�� initComponents();
        // ��������
        jToolBar1 = new javax.swing.JToolBar();
        // ��������
        setLayout(new java.awt.BorderLayout());
        // ��������
        add(jToolBar1, java.awt.BorderLayout.NORTH);

        // ��parser��ͼ�ṹbegin
        /*
         * Point location = new Point (100,50); for(Myparser.Nd x:this.pNd){
         * Widget w = scene.addNode(x.id, x.type);
         * ((LabelWidget)w).setLabel(x.label); w.setPreferredLocation(new
         * Point(location)); location.y += 100; if (location.y > 500){
         * location.y = 100; location.x += 200; } }
         */
        // ��parser��ͼ�ṹend
        // *���ļ���ͼ�ṹ
        /*
         * �����ɾ�� try { URL url =
         * this.getClass().getResource("NodesFile.txt"); InputStreamReader
         * inReader = new InputStreamReader(url.openStream()); BufferedReader
         * inNodes = new BufferedReader(inReader); // BufferedReader inNodes =
         * new BufferedReader(new FileReader(AppletStart.nodesFile)); //
         * BufferedReader inNodes = new BufferedReader(new
         * FileReader("NodesFile.txt")); Point location = new Point (100,50);
         * String s; while((s = inNodes.readLine()) != null){ String label =
         * inNodes.readLine(); String fullText = inNodes.readLine(); String type =
         * inNodes.readLine(); Widget w = scene.addNode(s, type);
         * w.setToolTipText(fullText); ((LabelWidget)w).setLabel(label);
         * w.setPreferredLocation(new Point(location)); location.y += 100; if
         * (location.y > 500){ location.y = 100; location.x += 200; } } } catch
         * (FileNotFoundException e) { // TODO �Զ���� catch ��
         * e.printStackTrace(); } catch (IOException e) { // TODO �Զ���� catch ��
         * e.printStackTrace(); }
         */
        // */
        /*
         * String nodeID1 = "Node1"; String nodeID2 = "Node2"; String edge =
         * "edge"; Widget hello = scene.addNode(nodeID1,
         * DemoGraphScene.RECTANGLE_NODE); Widget world = scene.addNode(nodeID2,
         * DemoGraphScene.RECTANGLE_NODE); scene.addNode("node3",
         * DemoGraphScene.RECTANGLE_NODE); scene.addNode("node4",
         * DemoGraphScene.RECTANGLE_NODE); scene.addEdge(edge);
         * scene.setEdgeSource(edge, nodeID1); scene.setEdgeTarget(edge,
         * nodeID2); scene.addEdge("edge1"); scene.setEdgeSource("edge1",
         * nodeID1); scene.setEdgeTarget("edge1", "node3");
         * scene.addEdge("edge2"); scene.setEdgeSource("edge2", nodeID2);
         * scene.setEdgeTarget("edge2", "node3"); hello.setPreferredLocation(new
         * Point(100, 100)); world.setPreferredLocation(new Point(400, 200));
         */

        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        view = scene.createView();
        viewPanel = new javax.swing.JScrollPane(view);
        add(viewPanel, java.awt.BorderLayout.CENTER);

    }

    /*
     * �����ɾ�� private void adjust(){ Collection<String> nodes =
     * scene.getNodes(); Iterator e = nodes.iterator(); while(e.hasNext()){
     * String node = (String)e.next(); LabelWidget w =
     * (LabelWidget)scene.findWidget(node); if(w.getBounds()==null)
     * System.out.println(node+" bounds==null"); if(w.getClientArea()==null)
     * System.out.println(node+" clientArea==null"); int fix =
     * w.getClientArea().width/2; w.setPreferredLocation(new
     * Point(w.getPreferredLocation().x - fix,w.getPreferredLocation().y)); }
     */

    /*
     * /��parser��ͼ�ṹbegin for(Myparser.Ed x:this.pEd){ scene.addEdge(x.id);
     * scene.setEdgeSource(x.id, x.source.id); scene.setEdgeTarget(x.id,
     * x.target.id); } //��parser��ͼ�ṹend
     */
    // *���ļ���ͼ�ṹ
    /*
     * �����ɾ�� try { URL url = this.getClass().getResource("EdgesFile.txt");
     * InputStreamReader inReader = new InputStreamReader(url.openStream());
     * BufferedReader inEdges = new BufferedReader(inReader); // BufferedReader
     * inEdges = new BufferedReader(new FileReader(AppletStart.edgesFile)); //
     * BufferedReader inEdges = new BufferedReader(new
     * FileReader("EdgesFile.txt")); String s; while((s = inEdges.readLine()) !=
     * null){ String edge = s; scene.addEdge(edge); scene.setEdgeSource(edge,
     * inEdges.readLine()); scene.setEdgeTarget(edge, inEdges.readLine()); } }
     * catch (FileNotFoundException e1) { // TODO �Զ���� catch ��
     * e1.printStackTrace(); } catch (IOException e1) { // TODO �Զ���� catch ��
     * e1.printStackTrace(); } }
     */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code
    // ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jToolBar1 = new javax.swing.JToolBar();
        setLayout(new java.awt.BorderLayout());
        add(jToolBar1, java.awt.BorderLayout.NORTH);
        jToolBar1.setFloatable(true);

        SaveButton = new javax.swing.JButton();
        SaveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "save.gif")));
        // SaveButton.setSelected(true);
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    SaveButtonActionPerformed(evt);
                } catch (Exception e) {
                    // TODO �Զ���� catch ��
                    e.printStackTrace();
                }
            }
        });
        SaveButton.setToolTipText("����");
        SaveButton.setBorderPainted(false);
        jToolBar1.add(SaveButton);

        LoadButton = new javax.swing.JButton();
        LoadButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "load.gif")));
        // LoadButton.setSelected(true);
        LoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    LoadButtonActionPerformed(evt);
                } catch (Exception e) {
                    // TODO �Զ���� catch ��
                    e.printStackTrace();
                }
            }
        });
        LoadButton.setToolTipText("��ȡ");
        jToolBar1.add(LoadButton);
        LoadButton.setBorderPainted(false);

        ClearButton = new javax.swing.JButton();
        ClearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "new.gif")));
        // ClearButton.setSelected(true);
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        ClearButton.setToolTipText("����");
        ClearButton.setBorderPainted(false);
        jToolBar1.add(ClearButton);

        LayoutButton = new javax.swing.JButton();
        LayoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                "layout.gif")));
        // LayoutButton.setSelected(true);
        LayoutButton.addActionListener(new LayoutAction(scene));
        LayoutButton.setToolTipText("�����Զ�����");
        LayoutButton.setBorderPainted(false);
        jToolBar1.add(LayoutButton);

        HorizontalLayoutButton = new javax.swing.JButton();
        HorizontalLayoutButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("horizontalLayout.gif")));
        // HorizontalLayoutButton.setSelected(true);
        HorizontalLayoutButton.addActionListener(new HorizontalLayoutAction(
                scene));
        HorizontalLayoutButton.setToolTipText("�����Զ�����");
        HorizontalLayoutButton.setBorderPainted(false);
        jToolBar1.add(HorizontalLayoutButton);

        GridsToggleButton = new javax.swing.JToggleButton();
        GridsToggleButton.setIcon(new javax.swing.ImageIcon(getClass()
                .getResource("changeBackground.gif")));
        GridsToggleButton.setSelected(true);
        GridsToggleButton
                .addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        GridsToggleButtonActionPerformed(evt);
                    }
                });
        GridsToggleButton.setToolTipText("���ͼ��");
        GridsToggleButton.setBorderPainted(false);
        // GridsToggleButton.setSelected(false);
        jToolBar1.add(GridsToggleButton);

    }

    private void GridsToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GridsToggleButtonActionPerformed
    // �����ɾ�� if(GridsToggleButton.isSelected())
    // �����ɾ�� {
    // �����ɾ�� scene.initGrids();
    // �����ɾ�� }
    // �����ɾ�� else {
        scene.setBackground(Color.WHITE);
        scene.validate();
        // �����ɾ�� }
        System.out.println("VIEW:" + view.getWidth() + view.getHeight() + "  "
                + this.getWidth() + "  " + this.getHeight());
    }

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt)
            throws Exception {
        ObjectOutputStream outEdges = new ObjectOutputStream(
                new FileOutputStream("EdgesFile.obj"));
        ObjectOutputStream outNodes = new ObjectOutputStream(
                new FileOutputStream("NodesFile.obj"));
        Collection<String> edges = scene.getEdges();
        outEdges.writeInt(edges.size());
        Iterator e = edges.iterator();
        while (e.hasNext()) {
            String edge = (String) e.next();
            outEdges.writeObject(edge);
            outEdges.writeObject(scene.getEdgeSource(edge));
            outEdges.writeObject(scene.getEdgeTarget(edge));

            ConnectionWidget we = (ConnectionWidget) scene.findWidget(edge);
            Collection<Point> cp = we.getControlPoints();
            int cpNum = cp.size();
            outEdges.writeInt(cpNum);
            Iterator i = cp.iterator();
            while (i.hasNext()) {
                Point x = (Point) i.next();
                outEdges.writeObject(x);
            }
            System.out.println("edge: " + edge + "  "
                    + scene.getEdgeSource(edge) + "  "
                    + scene.getEdgeTarget(edge) + "  " + cpNum);
        }

        Collection<String> nodes = scene.getNodes();
        outNodes.writeInt(nodes.size());
        e = nodes.iterator();
        while (e.hasNext()) {
            String node = (String) e.next();
            outNodes.writeObject(node);

            Widget w = scene.findWidget(node);
            String str = null;
            if (w instanceof LabelWidget)
                str = new String((((LabelWidget) w).getLabel())
                        .getBytes("GB2312"), "8859_1");
            else if (w instanceof EditorWidget)
                str = new String((((EditorWidget) w).getLabel())
                        .getBytes("GB2312"), "8859_1");
            outNodes.writeObject(str);
            str = new String((w.getToolTipText()).getBytes("GB2312"), "8859_1");
            outNodes.writeObject(str);

            outNodes.writeObject(w.getLocation());
            if (w.getBorder() instanceof LozengeBorder)
                outNodes.writeObject(DemoGraphScene.LOZENGE_NODE);
            else if (w.getBorder() instanceof TriangleBorder)
                outNodes.writeObject(DemoGraphScene.TRIANGLE_NODE);
            else if (w.getBorder() instanceof RectangleBorder)
                outNodes.writeObject(DemoGraphScene.RECTANGLE_NODE);
            else
                // if(w.getBorder() == null)
                outNodes.writeObject(DemoGraphScene.EDITOR_NODE);

            System.out.println("node: " + node + "  "
                    + scene.findWidget(node).getLocation());
        }
        outEdges.close();
        outNodes.close();

    }

    private void LoadButtonActionPerformed(java.awt.event.ActionEvent evt)
            throws Exception {
        scene.clearSeparator();
        scene.setSelectedAreaVisible(false);
        Collection<String> edges = scene.getEdges();
        Collection<String> edgesToBeDel = new ArrayList<String>();
        Iterator iterator = edges.iterator();
        while (iterator.hasNext()) {
            String edge = (String) iterator.next();
            edgesToBeDel.add(edge);
            System.out.println(edge);
        }
        iterator = edgesToBeDel.iterator();
        while (iterator.hasNext())
            scene.removeEdge((String) iterator.next());

        Collection<String> nodes = scene.getNodes();
        Collection<String> nodesToBeDel = new ArrayList<String>();
        iterator = nodes.iterator();
        while (iterator.hasNext()) {
            String node = (String) iterator.next();
            nodesToBeDel.add(node);
            System.out.println(node);
        }
        iterator = nodesToBeDel.iterator();
        while (iterator.hasNext())
            scene.removeNode((String) iterator.next());

        ObjectInputStream inEdges = new ObjectInputStream(new FileInputStream(
                "EdgesFile.obj"));
        ObjectInputStream inNodes = new ObjectInputStream(new FileInputStream(
                "NodesFile.obj"));

        int nodeNum = inNodes.readInt();
        for (int i = 0; i < nodeNum; ++i) {
            String node = (String) inNodes.readObject();
            String label = (String) inNodes.readObject();
            label = new String(label.getBytes("8859_1"), "GB2312");
            String fullText = (String) inNodes.readObject();
            fullText = new String(fullText.getBytes("8859_1"), "GB2312");
            Point location = (Point) inNodes.readObject();
            String border = (String) inNodes.readObject();
            Widget w;
            if (border.equals(DemoGraphScene.LOZENGE_NODE))
                w = scene.addNode(node, DemoGraphScene.LOZENGE_NODE);
            else if (border.equals(DemoGraphScene.TRIANGLE_NODE))
                w = scene.addNode(node, DemoGraphScene.TRIANGLE_NODE);
            else if (border.equals(DemoGraphScene.EDITOR_NODE)) {
                w = scene.addNode(node, DemoGraphScene.EDITOR_NODE);
            } else
                w = scene.addNode(node, DemoGraphScene.RECTANGLE_NODE);
            if (w instanceof LabelWidget)
                ((LabelWidget) w).setLabel(label);
            else if (w instanceof EditorWidget)
                ((EditorWidget) w).setLabel(label);
            w.setToolTipText(fullText);
            w.setPreferredLocation(location);
            System.out.println("readnode: " + node + "  " + location);

        }

        int edgeNum = inEdges.readInt();
        for (int i = 0; i < edgeNum; ++i) {
            String edge = (String) inEdges.readObject();
            String source = (String) inEdges.readObject();
            String target = (String) inEdges.readObject();
            ConnectionWidget e = (ConnectionWidget) scene.addEdge(edge);
            scene.setEdgeSource(edge, source);
            scene.setEdgeTarget(edge, target);
            int cpNum = inEdges.readInt();
            System.out.println("readedge: " + edge + "  " + source + "  "
                    + target + "  " + cpNum);
            Collection<Point> cp = new ArrayList<Point>();
            for (int j = 0; j < cpNum; ++j) {
                Point p = (Point) inEdges.readObject();
                cp.add(p);
            }
            e.setControlPoints(cp, true);
        }
        inEdges.close();
        inNodes.close();
        scene.validate();
    }

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_GridsToggleButtonActionPerformed
        scene.clearSeparator();
        scene.setSelectedAreaVisible(false);
        Collection<String> edges = scene.getEdges();
        Collection<String> edgesToBeDel = new ArrayList<String>();
        Iterator e = edges.iterator();
        while (e.hasNext()) {
            String edge = (String) e.next();
            edgesToBeDel.add(edge);
            System.out.println(edge);
        }
        e = edgesToBeDel.iterator();
        while (e.hasNext())
            scene.removeEdge((String) e.next());

        Collection<String> nodes = scene.getNodes();
        Collection<String> nodesToBeDel = new ArrayList<String>();
        e = nodes.iterator();
        while (e.hasNext()) {
            String node = (String) e.next();
            nodesToBeDel.add(node);
            System.out.println(node);
        }
        e = nodesToBeDel.iterator();
        while (e.hasNext())
            scene.removeNode((String) e.next());
        scene.validate();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                FreeConnectTest fct = new FreeConnectTest();

                SceneSupport.showCore(fct);
                fct.view.requestFocusInWindow();
                // �����ɾ�� fct.adjust();
                // ��������

                FreeConnectTest editorFct = new FreeConnectTest();

                SceneSupport.showCore(editorFct);
                editorFct.view.requestFocusInWindow();
                Draw draw = new Draw(fct.scene, editorFct.scene,
                        "NodesFile.txt", "EdgesFile.txt");
                // ��������
                draw.start();
                // �����ɾ�� fct.LayoutButton.doClick();
            }
        });
    }

    public static void runAsApplet(final AppletStart frame) {
        // java.awt.EventQueue.invokeLater(new Runnable() {
        // public void run(){
        fct = new FreeConnectTest();
        int width = 800, height = 700;
        frame.add(fct, BorderLayout.CENTER);

        // �����ɾ�� fct.adjust();

        fct.viewPanel
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        fct.scene.getActions().addAction(new AppletScrollAction(fct.viewPanel));
        // ��������

        DemoGraphScene editorScene = new DemoGraphScene();
        JComponent editorView = editorScene.createView();
        editorView.setBorder(new javax.swing.border.LineBorder(Color.gray, 1));
        fct.add(editorView, java.awt.BorderLayout.WEST);

        // FreeConnectTest editorFct = new FreeConnectTest();
        // editorFct.viewPanel.getVerticalScrollBar().setVisible(false);
        // frame.add (editorFct, BorderLayout.WEST);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
                .getScreenSize();
        frame.setBounds((screenSize.width - width) / 2,
                (screenSize.height - height) / 2, width, height);
        frame.setVisible(true);
        fct.view.requestFocusInWindow();

        draw = new Draw(fct.scene, editorScene, frame.nodeFileName,
                frame.edgeFileName);
        // ��������
        draw.start();
        // �����ɾ�� fct.HorizontalLayoutButton.doClick();
        // }
        // });
        frame.setSize(500, 655);

    }

    private static FreeConnectTest fct;

    private static Draw draw;

    public static void refresh(final AppletStart frame) {
        // draw.test();
        draw.start();
    }

    /*
     * public void parse(){ try { System.setIn(new FileInputStream("test.txt")); }
     * catch (FileNotFoundException e) { // TODO �Զ���� catch ��
     * e.printStackTrace(); } Mylexer lexer = new Mylexer(); Myparser parser =
     * new Myparser(); if (parser.yycreate(lexer)) { if (lexer.yycreate(parser)) {
     * parser.yyparse(); } } }
     */
    private javax.swing.JToggleButton GridsToggleButton;

    private javax.swing.JButton SaveButton;

    private javax.swing.JButton LoadButton;

    private javax.swing.JButton ClearButton;

    private javax.swing.JButton LayoutButton;

    private javax.swing.JButton HorizontalLayoutButton;

    private javax.swing.JToolBar jToolBar1;

}
