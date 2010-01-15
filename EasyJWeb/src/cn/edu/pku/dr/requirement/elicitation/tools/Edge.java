package cn.edu.pku.dr.requirement.elicitation.tools;

class Edge {
    public Edge(String name, String source, String target, String toolTipsText) {
        this.name = name;
        this.source = source;
        this.target = target;
        this.toolTipsText = toolTipsText;
    }

    public String name;

    public String source;

    public String target;

    public String toolTipsText;

    public int tag = 0; // ��Ǹ�ʵ��ߣ��Ƿ�����ṹ�ıߣ�0��ʾ���ǣ�1��ʾ��

}
