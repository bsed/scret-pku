package cn.edu.pku.dr.requirement.elicitation.tools;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

class Node {
    public Node(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String name; // ����id

    public String label; // �������ݱ�ǩ

    public String fullText;// ����ȫ������

    public String type; // �������ͣ������Ρ���ǡ���Բ��

    public int order = 0; // �����ڷֲ�ͼ�ṹ��λ�ڵڼ���

    public int pre = 1; // �����ͬһ�������ֵܽ�����ŵڼ�

    public ArrayList<Node> sons = new ArrayList<Node>();

    public HashSet<Node> reachSet = new HashSet<Node>();

    public Point location;

    public int inDegree = 0;

    public int outDegree = 0;

    public int channel = 0;

    public int maxLength = 0;

}
