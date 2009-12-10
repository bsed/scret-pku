package cn.edu.pku.dr.requirement.elicitation.tools;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.layout.Layout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.netbeans.modules.visual.action.ZoomAction;
import org.netbeans.modules.visual.anchor.TriangleAnchorShape;
import org.openide.util.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * @author didikiki
 */
public class DemoGraphScene extends GraphScene.StringGraph {

    // private static final Image IMAGE = Utilities.loadImage("TRIANGLE.png");
    // // NOI18N

    // private static final Image RECTANGLE_IMAGE = null;
    // private static final Image LOZENGE_IMAGE =
    // Utilities.loadImage("LOZENGE.png");
    // private static final Image TRIANGLE_IMAGE =
    // Utilities.loadImage("TRIANGLE.png");
    public static final String RECTANGLE_NODE = "rectangle_node";

    public static final String LOZENGE_NODE = "lozenge_node";

    public static final String TRIANGLE_NODE = "triangle_node";

    public static final String EDITOR_NODE = "editor_node";

    public static final String EDGETIP_NODE = "edgetip_node";

    protected LayerWidget mainLayer;

    protected LayerWidget connectionLayer;

    protected LayerWidget interractionLayer = new LayerWidget(this);

    protected LayerWidget backgroundLayer = new LayerWidget(this);

    // private WidgetAction moveAction = new MoveAction();
    private WidgetAction moveAction = ActionFactory.createMoveAction();

    private Router router = RouterFactory.createFreeRouter();

    private WidgetAction connectAction = ActionFactory
            .createExtendedConnectAction(interractionLayer,
                    new SceneConnectProvider(this));

    private WidgetAction reconnectAction = ActionFactory
            .createReconnectAction(new SceneReconnectProvider(this));

    private WidgetAction moveControlPointAction = ActionFactory
            .createFreeMoveControlPointAction();

    // private WidgetAction selectAction = ActionFactory.createSelectAction(new
    // ObjectSelectProvider());
    private WidgetAction editorAction = ActionFactory
            .createInplaceEditorAction(new LabelTextFieldEditor());

    private NodeMenu nodeMenu = new NodeMenu(this);

    private EdgeMenu edgeMenu = new EdgeMenu(this);

    public DemoGraphScene() {
        mainLayer = new LayerWidget(this);
        addChild(mainLayer);
        connectionLayer = new LayerWidget(this);
        addChild(connectionLayer);
        addChild(interractionLayer);
        // getActions().addAction(ActionFactory.createRectangularSelectAction(this,
        // backgroundLayer));
        // �����ɾ�� this.getActions().addAction(new WholeDragAction(this));
        // �����ɾ��
        // getActions().addAction(ActionFactory.createPopupMenuAction(new
        // SceneMainMenu(this)));

        // getActions().addAction(new PopupMenuAction(new SceneMainMenu(this)));
        // �����ɾ�� getActions().addAction (ActionFactory.createZoomAction ());
        // �����ɾ�� getActions().addAction(ActionFactory.createPanAction());
        // �����ɾ�� initGrids();

        // mainLayer.getActions().addAction(new WholeDragAction());
        // connectionLayer.getActions().addAction(new WholeDragAction());
    }

    /*
     * public Widget addNode(String node, String nodeType){ MyIconNodeWidget
     * widget = (MyIconNodeWidget)super.addNode(node); if
     * (nodeType.equals(RECTANGLE_NODE)){ widget.setImage(RECTANGLE_IMAGE);
     * widget.setBorder(BorderFactory.createLineBorder (10)); } else if
     * (nodeType.equals(LOZENGE_NODE)){ // widget.setImage(LOZENGE_IMAGE);
     * widget.setBorder(new LozengeBorder(10,null)); } else if
     * (nodeType.equals(TRIANGLE_NODE)) widget.setImage(TRIANGLE_IMAGE); return
     * widget; }
     */

    /*
     * protected Widget attachNodeWidget(String node) { MyIconNodeWidget label =
     * new MyIconNodeWidget(this); label.setToolTipText("�����:'Ctrl'+������");
     * label.setLabel(node); //label.setBorder(BorderFactory.createLineBorder
     * (10)); label.setImage(RECTANGLE_IMAGE);
     * label.getActions().addAction(connectAction);
     * label.getActions().addAction(moveAction);
     * label.getLabelWidget().getActions ().addAction (editorAction);
     * mainLayer.addChild(label);
     * label.getActions().addAction(ActionFactory.createPopupMenuAction(nodeMenu));
     * return label; }
     */
    public Widget addNode(String node, String nodeType) {
        if (nodeType.equals(EDITOR_NODE))
            isEditor = true;
        else
            isEditor = false;

        Widget widget = super.addNode(node);
        if (nodeType.equals(RECTANGLE_NODE)) {
            widget.setFont(new Font("", 0, 15));
            widget.setBorder(new RectangleBorder(5, 5, 5, 5, backgroundColor));
            // widget.setBorder(BorderFactory.createLineBorder (10));
        } else if (nodeType.equals(LOZENGE_NODE)) {
            // int len = ((LabelWidget)widget).getLabel().length();
            widget.setFont(new Font("", Font.ITALIC, 15));
            widget
                    .setBorder(new LozengeBorder(10, 10, 10, 10,
                            backgroundColor));
        } else if (nodeType.equals(TRIANGLE_NODE)) {
            widget.setFont(new Font("", Font.BOLD, 15));
            widget
                    .setBorder(new TriangleBorder(20, 30, 20, 30,
                            backgroundColor));
        } else if (nodeType.equals(EDITOR_NODE)) {
            widget.setFont(new Font("", Font.BOLD, 15));
            widget.setBorder(new EditorBorder(5, 5, 5, 5, backgroundColor));
            // widget.setBorder(new RectangleBorder(5,5,5,5,null));
        } else if (nodeType.equals(EDGETIP_NODE)) {
            widget.setFont(new Font("", 0, 10));
            widget.setBorder(new EditorBorder(5, 5, 5, 5, backgroundColor));
            // widget.setBorder(BorderFactory.createLineBorder (10));
        }
        return widget;
    }

    private boolean isEditor = false;

    protected Widget attachNodeWidget(String node) {
        if (isEditor) {
            EditorWidget editor = new EditorWidget(this, node);
            // �����ɾ�� editor.getActions().addAction(connectAction);
            // �����ɾ�� editor.getActions().addAction(moveAction);
            // �����ɾ�� editor.getActions ().addAction (editorAction);
            mainLayer.addChild(editor);
            // �����ɾ��
            // editor.getActions().addAction(ActionFactory.createPopupMenuAction(nodeMenu));
            editor.setForeground(foregroundColor);
            return editor;
        } else {
            LabelWidget label = new LabelWidget(this, node);
            // label.setToolTipText("�����:'Ctrl'+������");
            // �����ɾ�� label.getActions().addAction(connectAction);
            // �����ɾ�� label.getActions().addAction(moveAction);
            // �����ɾ�� label.getActions ().addAction (editorAction);
            mainLayer.addChild(label);
            // �����ɾ��
            // label.getActions().addAction(ActionFactory.createPopupMenuAction(nodeMenu));
            label.setForeground(foregroundColor);
            return label;
        }
    }

    protected Widget attachEdgeWidget(String edge) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setRouter(router);
        // �����ɾ�� connection.setToolTipText("������Ƶ㣺���˫��");
        connection.setTargetAnchorShape(new TriangleAnchorShape(6, true, false,
                false, 6.0));
        connection.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.setLineColor(backgroundColor); // ������ɫ
        /*
         * float[] dash1 = { 10.0f }; //������ connection.setStroke(new
         * BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
         * 10.0f, dash1, 0.0f));
         */

        connectionLayer.addChild(connection);
        // �����ɾ�� connection.getActions().addAction(reconnectAction);
        // �����ɾ�� connection.getActions().addAction(createSelectAction());
        // �����ɾ��
        // connection.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
        // �����ɾ�� connection.getActions().addAction(moveControlPointAction);
        // �����ɾ��
        // connection.getActions().addAction(ActionFactory.createPopupMenuAction(edgeMenu));
        return connection;
    }

    protected void attachEdgeSourceAnchor(String edge, String oldSourceNode,
            String sourceNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget sourceNodeWidget = findWidget(sourceNode);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory
                .createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }

    protected void attachEdgeTargetAnchor(String edge, String oldTargetNode,
            String targetNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget targetNodeWidget = findWidget(targetNode);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory
                .createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }

    /*
     * private class ObjectSelectProvider implements SelectProvider { public
     * boolean isAimingAllowed(Widget widget, Point localLocation, boolean
     * invertSelection) { return false; } public boolean
     * isSelectionAllowed(Widget widget, Point localLocation, boolean
     * invertSelection) { return true; } public void select(Widget widget, Point
     * localLocation, boolean invertSelection) { Object object =
     * findObject(widget); if (object != null) { if
     * (getSelectedObjects().contains(object))return;
     * userSelectionSuggested(Collections.singleton(object), invertSelection); }
     * else userSelectionSuggested(Collections.emptySet(), invertSelection); } }
     */
    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        public boolean isEnabled(Widget widget) {
            return true;
        }

        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
        }

    }

    /*
     * //�����ɾ�� public void initGrids(){ Image sourceImage =
     * Utilities.icon2Image(new
     * ImageIcon(getClass().getResource("background.png"))); // NOI18N int width =
     * sourceImage.getWidth(null); int height = sourceImage.getHeight(null);
     * BufferedImage image = new BufferedImage(width, height,
     * BufferedImage.TYPE_INT_RGB); Graphics2D graphics =
     * image.createGraphics(); graphics.drawImage(sourceImage, 0, 0, null);
     * graphics.dispose(); TexturePaint PAINT_BACKGROUND = new
     * TexturePaint(image, new Rectangle(0, 0, width, height));
     * setBackground(PAINT_BACKGROUND); repaint(); revalidate(false);
     * validate(); }
     */

    public void clearSeparator() {
        for (IconNodeWidget x: separator) {
            this.removeChild(x);
        }
        separator.clear();
    }

    public void setSelectedArea(Widget w) {
        this.selectedArea = w;
    }

    public void setSelectedAreaVisible(boolean isVisible) {
        if (selectedArea != null)
            selectedArea.setVisible(isVisible);
    }

    // Ӿ�7ָ���
    protected ArrayList<IconNodeWidget> separator = new ArrayList<IconNodeWidget>();

    // Ӿ�7ָ��ߵ�ͼ��
    protected final Image separatorIcon = Utilities.icon2Image(new ImageIcon(
            getClass().getResource("separatorIcon.png")));

    protected final Image separatorIcon2 = Utilities.icon2Image(new ImageIcon(
            getClass().getResource("separatorIcon2.png")));

    //
    private Widget selectedArea;

    public final static Color foregroundColor = new Color(100, 0, 150);

    public final static Color backgroundColor = new Color(150, 150, 150);
}
