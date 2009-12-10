package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class AppletSize {
    private String nodeFileName;

    private String edgeFileName;

    public AppletSize(String nodeFileName, String edgeFileName) {
        this.nodeFileName = nodeFileName;
        this.edgeFileName = edgeFileName;
    }

    public AppletSize() {
        this.nodeFileName = "NodesFile.txt";
        this.edgeFileName = "EdgesFile.txt";
    }

    public static void main(String arg[]) {
        AppletSize x = new AppletSize();
        System.out.println(x.getAppletHeight());
    }

    public int getAppletHeight() {
        if (appletHeight != -1)
            return appletHeight;
        node.clear();
        edge.clear();
        rootSet.clear();
        struct.clear();
        createNodes();
        computeLayout();
        this.appletHeight = computeAppletHeight();
        return this.appletHeight;
    }

    private int appletHeight = -1;

    private int computeAppletHeight() {
        // ��ʼ����Ľ�㲼�֣�������һ��Ҷ������Ҳ�ƫ��ʾ����
        for (Node cur: node)
            cur.location = new Point();

        // ��Ҷ������Ҳ�ƫ��ʾ����ĳɾ�����ʾ����
        // �����У��ӵ���ڶ��㿪ʼ���Ե����ϣ��������ң�ÿһ��������һ������������ʾ
        ArrayList<Node> levelNodes = new ArrayList<Node>(); // ĳ����ļ���
        int farMostY = 0;
        int adjustY = 0;
        for (int ch = 0; ch < rootSet.size(); ++ch) {
            adjustY = farMostY + deltaY + deltaChannel;
            if (ch == 0)
                adjustY = deltaChannel / 2 + deltaY;
            farMostY = 0;
            for (int i = order - 1; i >= 0; --i) {
                // �õ���i����ļ��ϣ���levelNodes��¼
                levelNodes.clear();
                Iterator e = node.iterator();
                while (e.hasNext()) {
                    Node cur = (Node) e.next();
                    if (cur.order == i && cur.channel == ch)
                        levelNodes.add(cur);
                }

                // ��pre,�Ե�i��Ľ���������
                for (int m = 0; m < levelNodes.size(); ++m)
                    for (int n = m + 1; n < levelNodes.size(); ++n)
                        if (levelNodes.get(n).pre < levelNodes.get(m).pre) {
                            Node tmp = levelNodes.get(n);
                            levelNodes.set(n, levelNodes.get(m));
                            levelNodes.set(m, tmp);

                        }

                // �Ե�i��Ľ����о��в���
                if (i != 0) {
                    for (int m = 0; m < levelNodes.size(); ++m) {
                        Node cur = levelNodes.get(m);
                        cur.location.x = cur.order * deltaX;
                        if (cur.sons.size() == 0) {
                            // curû�ж��ӣ����cur����ߵĵ�һ���ֵܶ�cur���в���
                            if (m > 0) {
                                Node preNode = levelNodes.get(m - 1);
                                if (cur.location.y - preNode.location.y <= deltaY)
                                    cur.location.y = preNode.location.y
                                            + deltaY;
                            } else
                                cur.location.y = cur.pre * deltaY + adjustY;
                        } else {
                            Node firstSon = null;
                            Node lastSon = null;
                            for (Node son: node) {
                                if (son.equals(cur.sons.get(0)))
                                    firstSon = son;
                                if (son.equals(cur.sons
                                        .get(cur.sons.size() - 1)))
                                    lastSon = son;
                            }
                            cur.location.y = (firstSon.location.y + lastSon.location.y) / 2;
                            if (m > 0) {
                                Node preNode = levelNodes.get(m - 1);
                                if (cur.location.y - preNode.location.y <= deltaY)
                                    cur.location.y = preNode.location.y
                                            + deltaY;
                            }
                        }
                    }
                } else {
                    for (int m = 0; m < levelNodes.size(); ++m) {
                        Node cur = levelNodes.get(m);
                        cur.location.x = deltaX / 4;
                    }
                }

                if (!levelNodes.isEmpty()
                        && levelNodes.get(levelNodes.size() - 1).location.y > farMostY)
                    farMostY = levelNodes.get(levelNodes.size() - 1).location.y;
            }

        }

        int result = farMostY + deltaChannel;
        return result;

    }

    private void computeLayout() {
        for (Edge x: edge) {
            ++getNode(x.source).outDegree;
            ++getNode(x.target).inDegree;
        }

        // ��¼��ڵ㣬����Ӿ��ʱʹ��
        for (Node x: node)
            if (x.inDegree == 0) {
                rootSet.add(x);
                // ��һ���㣬��Ӿ�5ĸ��㣬�Ǹ�Ӿ�5�editor
                x.type = DemoGraphScene.EDITOR_NODE;
            }

        // ��������
        ArrayList<Node> nodeCopy = new ArrayList<Node>();
        for (Node x: node)
            nodeCopy.add(x);

        ArrayList<Edge> edgeCopy = new ArrayList<Edge>();
        ArrayList<Edge> edgeToBeDel = new ArrayList<Edge>();
        for (Edge x: edge)
            edgeCopy.add(x);

        order = 0;
        while (!nodeCopy.isEmpty()) {
            ArrayList<Node> curOrderNode = new ArrayList<Node>();
            edgeToBeDel.clear();
            for (Node x: nodeCopy)
                if (x.inDegree == 0) {
                    x.order = order;
                    curOrderNode.add(x);
                    for (Edge y: edgeCopy)
                        if (y.source.equals(x.name))
                            edgeToBeDel.add(y);
                }
            for (Edge yy: edgeToBeDel) {
                for (Node z: nodeCopy)
                    if (yy.target.equals(z.name))
                        --z.inDegree;
                edgeCopy.remove(yy);
            }

            if (curOrderNode.isEmpty()) {
                int minInDegree = 1000;
                Node minInDegreeNode = null;
                for (Node x: nodeCopy)
                    if (x.inDegree < minInDegree) {
                        minInDegree = x.inDegree;
                        minInDegreeNode = x;
                    }
                curOrderNode.add(minInDegreeNode);
                edgeToBeDel.clear();
                for (Edge y: edgeCopy) {
                    if (y.source.equals(minInDegreeNode.name)) {
                        for (Node z: nodeCopy)
                            if (y.target.equals(z.name))
                                --z.inDegree;
                        edgeToBeDel.add(y);
                    }
                }
                for (Edge yy: edgeToBeDel)
                    edgeCopy.remove(yy);
            }

            for (Node xx: curOrderNode)
                nodeCopy.remove(xx);
            struct.add(curOrderNode);
            order++;
        }

        // ��ȡ�������е���ṹ������ṹˢ��struct�ṹ
        for (int i = 0; i < struct.size() - 1; ++i) {
            ArrayList<Node> curOrderNode = struct.get(i);
            ArrayList<Node> nextOrderNode = struct.get(i + 1);
            for (Node x: nextOrderNode) {
                for (Node y: curOrderNode) {
                    Edge e = findEdge(y.name, x.name);
                    if (e != null) {
                        y.sons.add(x);
                        e.tag = 1;
                        break;
                    }

                }
            }
            nextOrderNode.clear();
            for (Node x: curOrderNode)
                for (Node y: x.sons)
                    nextOrderNode.add(y);
        }

        // Ϊÿ�����sons���ҵ���ͻ���ٵ�˳��
        // Ϊ�ˣ����ȼ���ÿ����Ŀɴ��㼯��
        for (Node nn: node)
            computeReachSet(nn);

        ArrayList<Integer> sonsTmp = new ArrayList<Integer>();
        ArrayList<Node> sonsResult = new ArrayList<Node>();
        int resultConflictCount = 10000;
        for (Node nn: node) {
            if (nn.sons.size() <= 2)
                continue;
            sonsResult.clear();
            resultConflictCount = 10000;
            boolean conflictRecord[][][] = new boolean[nn.sons.size()][][];
            for (int x = 0; x < nn.sons.size(); ++x)
                conflictRecord[x] = new boolean[nn.sons.size()][];
            for (int x = 0; x < nn.sons.size(); ++x)
                for (int y = 0; y < nn.sons.size(); ++y)
                    conflictRecord[x][y] = new boolean[nn.sons.size()];

            for (int x = 0; x < nn.sons.size(); ++x)
                for (int y = 0; y < nn.sons.size(); ++y)
                    for (int z = 0; z < nn.sons.size(); ++z)
                        if (x != y && y != z && z != x)
                            conflictRecord[x][y][z] = computeConflict(x, y, z,
                                    nn.sons);

            int choiceCount = (int) Math.pow(nn.sons.size(), nn.sons.size());
            for (int i = 0; i < choiceCount; ++i) {
                sonsTmp.clear();
                int choiceTmp = i;
                // ��һ����ӽڵ������
                for (int p = 0; p < nn.sons.size(); ++p) {
                    int choiceWeight = choiceTmp - choiceTmp / nn.sons.size()
                            * nn.sons.size();
                    choiceTmp /= nn.sons.size();
                    sonsTmp.add(choiceWeight);
                    // sonsTmp.add(nn.sons.get(choiceWeight));
                }

                // ���������������ظ���Ԫ�أ������������Ч
                boolean tag = true;
                for (int j = 0; j < sonsTmp.size() - 1; ++j)
                    for (int k = j + 1; k < sonsTmp.size(); ++k)
                        if (sonsTmp.get(j) == sonsTmp.get(k))
                            tag = false;
                if (tag == false)
                    continue;

                // ����Ч��������д���
                int conflictCount = 0;
                for (int x = 0; x < sonsTmp.size() - 2; ++x)
                    for (int y = x + 1; y < sonsTmp.size() - 1; ++y)
                        for (int z = y + 1; z < sonsTmp.size(); ++z)
                            if (conflictRecord[sonsTmp.get(x)][sonsTmp.get(y)][sonsTmp
                                    .get(z)])
                                ++conflictCount;

                if (conflictCount < resultConflictCount) {
                    resultConflictCount = conflictCount;
                    sonsResult.clear();
                    for (Integer x: sonsTmp)
                        sonsResult.add(nn.sons.get(x));
                }
            }
            nn.sons.clear();
            nn.sons.addAll(sonsResult);
        }

        // ˢ��struct�ṹ
        for (int i = 0; i < struct.size() - 1; ++i) {
            ArrayList<Node> curOrderNode = struct.get(i);
            ArrayList<Node> nextOrderNode = struct.get(i + 1);
            nextOrderNode.clear();
            for (Node x: curOrderNode)
                for (Node y: x.sons)
                    nextOrderNode.add(y);
        }

        // ����Ӿ��
        ArrayList<Node> curOrderNode = new ArrayList<Node>();
        ArrayList<Node> nextOrderNode = new ArrayList<Node>();
        // int farMostPre = 0;
        for (int i = 0; i < rootSet.size(); ++i) {
            Node root = rootSet.get(i);
            // root.pre = farMostPre;
            root.pre = 0;
            root.channel = i;
            // farMostPre = 0;
            curOrderNode.add(root);
            while (!curOrderNode.isEmpty()) {
                int p = 0;
                for (Node x: curOrderNode) {
                    if (p < x.pre)
                        p = x.pre;
                    for (Node y: x.sons) {
                        y.pre = p;
                        y.channel = i;
                        ++p;
                        nextOrderNode.add(y);
                    }
                }
                ArrayList<Node> tmp = curOrderNode;
                curOrderNode = nextOrderNode;
                nextOrderNode = tmp;
                nextOrderNode.clear();
                // if(p > farMostPre) farMostPre = p;
            }
        }

    }

    private boolean computeConflict(int x, int y, int z, ArrayList<Node> sons) {
        int xy = 1000, xz = 1000, yz = 1000;

        for (Node nn: sons.get(x).reachSet)
            for (Node nm: sons.get(y).reachSet)
                if (nn == nm && nn.order < xy)
                    xy = nn.order;

        for (Node nn: sons.get(x).reachSet)
            for (Node nm: sons.get(z).reachSet)
                if (nn == nm && nn.order < xz)
                    xz = nn.order;

        for (Node nn: sons.get(y).reachSet)
            for (Node nm: sons.get(z).reachSet)
                if (nn == nm && nn.order < yz)
                    yz = nn.order;

        if (xy > xz || yz > xz)
            return true;
        return false;

    }

    private void computeReachSet(Node x) {
        x.reachSet.add(x);
        ArrayList<Node> newNode = new ArrayList<Node>();
        while (true) {
            newNode.clear();
            for (Node nn: x.reachSet)
                for (Edge e: edge)
                    if (nn.name.equals(e.source))
                        newNode.add(getNode(e.target));

            if (newNode.isEmpty())
                break;
            int countBeforeAdd = x.reachSet.size();
            x.reachSet.addAll(newNode);
            int countAfterAdd = x.reachSet.size();
            if (countBeforeAdd == countAfterAdd)
                break;
        }
        x.reachSet.remove(x);
    }

    private void createNodes() {
        try {
            File file = new File(this.nodeFileName);
            InputStreamReader inReader = new InputStreamReader(
                    new FileInputStream(file));
            BufferedReader inNodes = new BufferedReader(inReader);

            // BufferedReader inNodes = new BufferedReader(new
            // FileReader("NodesFile.txt"));
            String s;
            while ((s = inNodes.readLine()) != null) {
                String label = inNodes.readLine();
                String fullText = inNodes.readLine();
                String type = inNodes.readLine();
                Node n = new Node(s, type);
                n.label = label;
                n.fullText = fullText;
                node.add(n);
            }
            inNodes.close();

            file = new File(this.edgeFileName);
            inReader = new InputStreamReader(new FileInputStream(file));
            BufferedReader inEdges = new BufferedReader(inReader);
            // BufferedReader inEdges = new BufferedReader(new
            // FileReader("EdgesFile.txt"));
            while ((s = inEdges.readLine()) != null)
                edge.add(new Edge(s, inEdges.readLine(), inEdges.readLine(),
                        inEdges.readLine()));
            inEdges.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Edge findEdge(String source, String target) {
        for (Edge x: edge)
            if (x.source.equals(source) && x.target.equals(target))
                return x;
        return null;
    }

    private Node getNode(String name) {
        for (Node x: node)
            if (x.name.equals(name))
                return x;
        return null;
    }

    private ArrayList<ArrayList<Node>> struct = new ArrayList<ArrayList<Node>>();

    private ArrayList<Node> rootSet = new ArrayList<Node>();

    private ArrayList<Node> node = new ArrayList<Node>();

    private ArrayList<Edge> edge = new ArrayList<Edge>();

    private int order; // ��ߣ��ֲ�ͼ�ṹ�Ĳ���

    private final static int deltaX = 135;

    private final static int deltaY = 35;

    private final static int deltaChannel = 60;
}
