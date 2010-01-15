package cn.edu.pku.dr.requirement.elicitation.tools;

/*******************************************************************************
 * U N R E G I S T E R E D C O P Y You are on day 71 of your 30 day trial
 * period. This file was produced by an UNREGISTERED COPY of Parser Generator.
 * It is for evaluation purposes only. If you continue to use Parser Generator
 * 30 days after installation then you are required to purchase a license. For
 * more information see the online help or go to the Bumble-Bee Software
 * homepage at: http://www.bumblebeesoftware.com This notice must remain present
 * in the file. It cannot be removed.
 ******************************************************************************/

/*******************************************************************************
 * Myparser.java Java source file generated from Myparser.y. Date: 07/29/08
 * Time: 16:40:47 AYACC Version: 2.07
 ******************************************************************************/

// line 1 ".\\Myparser.y"
/*******************************************************************************
 * myparser.y ParserWizard generated YACC file. Date: 2008��5��17��
 ******************************************************************************/
import java.util.*;
import java.io.FileWriter;
import java.io.*;

// line 40 "Myparser.java"
import yl.*;

// ///////////////////////////////////////////////////////////////////////////
// Myparser

public class Myparser extends yyfparser {
    // line 21 ".\\Myparser.y"

    // place any extra class members here
    protected ArrayList<Nd> nodes = new ArrayList<Nd>();

    protected ArrayList<Ed> edges = new ArrayList<Ed>();

    private static int counter = 0;

    class Nd {
        public Nd(String a, String b, String c, String d) {
            this.id = a;
            this.label = b;
            this.fullText = c;
            this.type = d;
        }

        public String id;

        public String label;

        public String fullText;

        public String type;
    }

    class Ed {
        public Ed(String x, Nd y, Nd z) {
            this.id = x;
            this.source = y;
            this.target = z;
            this.toolTipsText = null;
        }

        public String id;

        public Nd source;

        public Nd target;

        public String toolTipsText = null;
    }

    class HandT {
        Nd head;

        ArrayList<Nd> tail;
    }

    class HandT2 {
        ArrayList<Nd> headSet;

        ArrayList<Nd> tailSet;

        ArrayList<String> toolTipsTextSet;
    }

    class HandT3 {
        Nd head;

        ArrayList<Nd> tail;

        String toolTipsText = null;
    }

    // line 99 "Myparser.java"
    public Myparser() {
        yytables();
        // line 74 ".\\Myparser.y"

        // place any extra initialisation code here

        // line 106 "Myparser.java"
    }

    public class YYSTYPE extends yyattribute {
        // line 79 ".\\Myparser.y"

        public int value;

        public String str;

        public ArrayList<Nd> ndArray;

        public HandT hnt;

        public HandT2 hnt2;

        public HandT3 hnt3;

        public void yycopy(yyattribute source, boolean move) {
            YYSTYPE yy = (YYSTYPE) source;
            value = yy.value;
            str = yy.str;
            ndArray = yy.ndArray;
            hnt = yy.hnt;
            hnt2 = yy.hnt2;
            hnt3 = yy.hnt3;
        }

        // line 129 "Myparser.java"
    }

    public static final int L_BEGIN = 65537;

    public static final int L_END = 65538;

    public static final int SEPRATOR = 65539;

    public static final int IF = 65540;

    public static final int ELSE = 65541;

    public static final int OTHER = 65542;

    public static final int LINE_END = 65543;

    public static final int LS = 65544;

    public static final int RS = 65545;

    public static final int LB = 65546;

    public static final int RB = 65547;

    protected final YYSTYPE yyattribute(int index) {
        YYSTYPE attribute = (YYSTYPE) yyattributestackref[yytop + index];
        return attribute;
    }

    protected final yyattribute yynewattribute() {
        return new YYSTYPE();
    }

    protected final YYSTYPE[] yyinitdebug(int count) {
        YYSTYPE a[] = new YYSTYPE[count];
        for (int i = 0; i < count; i++) {
            a[i] = (YYSTYPE) yyattributestackref[yytop + i - (count - 1)];
        }
        return a;
    }

    protected static yyftables yytables = null;

    public final void yyaction(int action) {
        switch (action) {
            case 0: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 117 ".\\Myparser.y"

                try {
                    DataOutputStream oN = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(
                                    nodeFileName)));

                    for (Nd s: nodes) {
                        oN.writeBytes("NODE" + s.id + '\n');
                        s.label = new String(s.label.getBytes("GB2312"),
                                "iso-8859-1");
                        oN.writeBytes(s.label + '\n');
                        if (s.fullText != null) {
                            s.fullText = new String(s.fullText
                                    .getBytes("GB2312"), "iso-8859-1");
                            oN.writeBytes(s.fullText + '\n');
                        } else
                            oN.writeBytes(s.label + '\n');
                        oN.writeBytes(s.type + '\n');
                    }
                    oN.close();

                    DataOutputStream oE = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(
                                    edgeFileName)));
                    for (Ed e: edges) {
                        oE.writeBytes("EDGE" + e.id + '\n');
                        oE.writeBytes("NODE" + e.source.id + '\n');
                        oE.writeBytes("NODE" + e.target.id + '\n');
                        if (e.toolTipsText != null)
                            e.toolTipsText = new String(e.toolTipsText
                                    .getBytes("GB2312"), "iso-8859-1");
                        oE.writeBytes(e.toolTipsText + '\n');
                    }
                    oE.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // line 207 "Myparser.java"
            }
                break;
            case 1: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(3);
                }
                // line 169 ".\\Myparser.y"

                // System.out.println("$$:"+$$);
                // System.out.println("$1:"+$1);
                // System.out.println("$4:"+$4);
                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.head = yyattribute(1 - 2).hnt.head;
                ((YYSTYPE) yyvalref).hnt.tail = yyattribute(2 - 2).hnt.tail;

                for (Nd source: yyattribute(1 - 2).hnt.tail) {
                    Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                            + counter++), source, yyattribute(2 - 2).hnt.head);
                    if (source.type.equals("lozenge_node")) {
                        nE.toolTipsText = "N";
                    }
                    edges.add(nE);
                }

                // line 234 "Myparser.java"
            }
                break;
            case 2: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 188 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).hnt = yyattribute(1 - 1).hnt;

                // line 246 "Myparser.java"
            }
                break;
            case 3: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 193 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).hnt = yyattribute(1 - 1).hnt;
                System.out.println("**��Լtype1");
                // line 257 "Myparser.java"
            }
                break;
            case 4: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 194 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).hnt = yyattribute(1 - 1).hnt;
                System.out.println("**��Լtype2");
                // line 268 "Myparser.java"
            }
                break;
            case 5: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 195 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).hnt = yyattribute(1 - 1).hnt;
                System.out.println("**��Լtype3");
                // line 279 "Myparser.java"
            }
                break;
            case 6: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(7);
                }
                // line 200 ".\\Myparser.y"

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(1 - 6).str),
                        new String(yyattribute(3 - 6).str), "rectangle_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.head = nd;
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail.add(nd);

                // line 297 "Myparser.java"
            }
                break;
            case 7: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(4);
                }
                // line 210 ".\\Myparser.y"

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(1 - 3).str), null,
                        "rectangle_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.head = nd;
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail.add(nd);

                // line 315 "Myparser.java"
            }
                break;
            case 8: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(3);
                }
                // line 226 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).str = yyattribute(1 - 2).str
                        + yyattribute(2 - 2).str;
                // line 326 "Myparser.java"
            }
                break;
            case 9: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 228 ".\\Myparser.y"
                ((YYSTYPE) yyvalref).str = yyattribute(1 - 1).str;
                // line 337 "Myparser.java"
            }
                break;
            case 10: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(21);
                }
                // line 237 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 20).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(18 - 20).hnt.tail);
                // System.out.println($$+$3);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 20).str),
                        new String(yyattribute(5 - 20).str), "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(11 - 20).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(18 - 20).hnt.head);
                nE.toolTipsText = "N";
                edges.add(nE);
                System.out.println("  *��Լ1");

                // line 366 "Myparser.java"
            }
                break;
            case 11: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(14);
                }
                // line 259 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 13).hnt.tail);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 13).str),
                        new String(yyattribute(5 - 13).str), "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(11 - 13).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);

                ((YYSTYPE) yyvalref).hnt.tail.add(nd);
                System.out.println("  *��Լ2");

                // line 392 "Myparser.java"
            }
                break;
            case 12: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(18);
                }
                // line 280 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(8 - 17).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(15 - 17).hnt.tail);
                // System.out.println($$+$3);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 17).str),
                        null, "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(8 - 17).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(15 - 17).hnt.head);
                nE.toolTipsText = "N";
                edges.add(nE);
                System.out.println("  *��Լ3");

                // line 421 "Myparser.java"
            }
                break;
            case 13: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(11);
                }
                // line 302 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(8 - 10).hnt.tail);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 10).str),
                        null, "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(8 - 10).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);

                ((YYSTYPE) yyvalref).hnt.tail.add(nd);
                System.out.println("  *��Լ4");

                // line 447 "Myparser.java"
            }
                break;
            case 14: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(22);
                }
                // line 326 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 21).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(14 - 21).hnt2.tailSet);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(19 - 21).hnt.tail);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 21).str),
                        new String(yyattribute(5 - 21).str), "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(11 - 21).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                Iterator i = yyattribute(14 - 21).hnt2.headSet.iterator();
                Iterator j = yyattribute(14 - 21).hnt2.toolTipsTextSet
                        .iterator();
                while (i.hasNext() && j.hasNext()) {
                    nE = new Ed(String.valueOf(System.currentTimeMillis()
                            + counter++), nd, (Nd) i.next());
                    nE.toolTipsText = (String) j.next();
                    edges.add(nE);
                }

                nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(19 - 21).hnt.head);
                nE.toolTipsText = "N";
                edges.add(nE);
                System.out.println("  *��Լ5");

                // line 484 "Myparser.java"
            }
                break;
            case 15: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(16);
                }
                // line 357 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 15).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(14 - 15).hnt2.tailSet);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 15).str),
                        new String(yyattribute(5 - 15).str), "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(11 - 15).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                Iterator i = yyattribute(14 - 15).hnt2.headSet.iterator();
                Iterator j = yyattribute(14 - 15).hnt2.toolTipsTextSet
                        .iterator();
                while (i.hasNext() && j.hasNext()) {
                    nE = new Ed(String.valueOf(System.currentTimeMillis()
                            + counter++), nd, (Nd) i.next());
                    nE.toolTipsText = (String) j.next();
                    edges.add(nE);
                }

                ((YYSTYPE) yyvalref).hnt.tail.add(nd);
                System.out.println("  *��Լ6");

                // line 518 "Myparser.java"
            }
                break;
            case 16: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(19);
                }
                // line 387 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(8 - 18).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 18).hnt2.tailSet);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(16 - 18).hnt.tail);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 18).str),
                        null, "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(8 - 18).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                Iterator i = yyattribute(11 - 18).hnt2.headSet.iterator();
                Iterator j = yyattribute(11 - 18).hnt2.toolTipsTextSet
                        .iterator();
                while (i.hasNext() && j.hasNext()) {
                    nE = new Ed(String.valueOf(System.currentTimeMillis()
                            + counter++), nd, (Nd) i.next());
                    nE.toolTipsText = (String) j.next();
                    edges.add(nE);
                }
                nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(16 - 18).hnt.head);
                nE.toolTipsText = "N";
                edges.add(nE);
                System.out.println("  *��Լ7");

                // line 554 "Myparser.java"
            }
                break;
            case 17: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(13);
                }
                // line 417 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt = new HandT();
                ((YYSTYPE) yyvalref).hnt.tail = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(8 - 12).hnt.tail);
                ((YYSTYPE) yyvalref).hnt.tail
                        .addAll(yyattribute(11 - 12).hnt2.tailSet);

                Nd nd = new Nd(String.valueOf(System.currentTimeMillis()
                        + counter++), new String(yyattribute(3 - 12).str),
                        null, "lozenge_node");
                nodes.add(nd);
                ((YYSTYPE) yyvalref).hnt.head = nd;

                Ed nE = new Ed(String.valueOf(System.currentTimeMillis()
                        + counter++), nd, yyattribute(8 - 12).hnt.head);
                nE.toolTipsText = "Y";
                edges.add(nE);
                Iterator i = yyattribute(11 - 12).hnt2.headSet.iterator();
                Iterator j = yyattribute(11 - 12).hnt2.toolTipsTextSet
                        .iterator();
                while (i.hasNext() && j.hasNext()) {
                    nE = new Ed(String.valueOf(System.currentTimeMillis()
                            + counter++), nd, (Nd) i.next());
                    nE.toolTipsText = (String) j.next();
                    edges.add(nE);
                }

                ((YYSTYPE) yyvalref).hnt.tail.add(nd);
                System.out.println("  *��Լ8");

                // line 588 "Myparser.java"
            }
                break;
            case 18: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(4);
                }
                // line 445 ".\\Myparser.y"

                yyattribute(1 - 3).hnt2.headSet
                        .add(yyattribute(3 - 3).hnt3.head);
                yyattribute(1 - 3).hnt2.tailSet
                        .addAll(yyattribute(3 - 3).hnt3.tail);
                yyattribute(1 - 3).hnt2.toolTipsTextSet
                        .add(yyattribute(3 - 3).hnt3.toolTipsText);
                ((YYSTYPE) yyvalref).hnt2 = yyattribute(1 - 3).hnt2;
                System.out.println(" someElseIf someLineEnd elseIf");

                // line 605 "Myparser.java"
            }
                break;
            case 19: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(2);
                }
                // line 453 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt2 = new HandT2();
                ((YYSTYPE) yyvalref).hnt2.headSet = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt2.tailSet = new ArrayList<Nd>();
                ((YYSTYPE) yyvalref).hnt2.toolTipsTextSet = new ArrayList<String>();
                ((YYSTYPE) yyvalref).hnt2.headSet
                        .add(yyattribute(1 - 1).hnt3.head);
                ((YYSTYPE) yyvalref).hnt2.tailSet
                        .addAll(yyattribute(1 - 1).hnt3.tail);
                ((YYSTYPE) yyvalref).hnt2.toolTipsTextSet
                        .add(yyattribute(1 - 1).hnt3.toolTipsText);
                System.out.println(" elseIf");

                // line 625 "Myparser.java"
            }
                break;
            case 20: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(11);
                }
                // line 468 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt3 = new HandT3();
                ((YYSTYPE) yyvalref).hnt3.head = yyattribute(9 - 10).hnt.head;
                ((YYSTYPE) yyvalref).hnt3.tail = yyattribute(9 - 10).hnt.tail;
                ((YYSTYPE) yyvalref).hnt3.toolTipsText = new String("else if "
                        + yyattribute(4 - 10).str);

                // line 641 "Myparser.java"
            }
                break;
            case 21: {
                YYSTYPE yya[];
                if (YYDEBUG) {
                    yya = yyinitdebug(14);
                }
                // line 477 ".\\Myparser.y"

                ((YYSTYPE) yyvalref).hnt3 = new HandT3();
                ((YYSTYPE) yyvalref).hnt3.head = yyattribute(12 - 13).hnt.head;
                ((YYSTYPE) yyvalref).hnt3.tail = yyattribute(12 - 13).hnt.tail;
                ((YYSTYPE) yyvalref).hnt3.toolTipsText = new String("else if "
                        + yyattribute(4 - 13).str);
                System.out.println("  *��Լ9");

                // line 658 "Myparser.java"
            }
                break;
            default:
                break;
        }
    }

    protected final void yytables() {
        yysstack_size = 100;
        yystack_max = 0;

        yycreatetables();
        yysymbol = yytables.yysymbol;
        yyrule = yytables.yyrule;
        yyreduction = yytables.yyreduction;
        yytokenaction = yytables.yytokenaction;
        yystateaction = yytables.yystateaction;
        yynontermgoto = yytables.yynontermgoto;
        yystategoto = yytables.yystategoto;
        yydestructorref = yytables.yydestructorref;
        yytokendestref = yytables.yytokendestref;
        yytokendestbaseref = yytables.yytokendestbaseref;
    }

    public static synchronized final void yycreatetables() {
        if (yytables == null) {
            yytables = new yyftables();

            if (YYDEBUG) {
                final yysymbol symbol[] = {
                    new yysymbol("$end", 0), new yysymbol("error", 65536),
                    new yysymbol("L_BEGIN", 65537),
                    new yysymbol("L_END", 65538),
                    new yysymbol("SEPRATOR", 65539), new yysymbol("IF", 65540),
                    new yysymbol("ELSE", 65541), new yysymbol("OTHER", 65542),
                    new yysymbol("LINE_END", 65543), new yysymbol("LS", 65544),
                    new yysymbol("RS", 65545), new yysymbol("LB", 65546),
                    new yysymbol("RB", 65547), new yysymbol(null, 0)
                };
                yytables.yysymbol = symbol;

                final String rule[] = {
                    "$accept: text",
                    "text: bodies",
                    "bodies: bodies SEPRATOR body",
                    "bodies: body",
                    "body: someLineEnd actions",
                    "body: someLineEnd",
                    "actions: actions action",
                    "actions: action",
                    "action: type1",
                    "action: type2",
                    "action: type3",
                    "type1: manyOther L_BEGIN manyOther L_END LINE_END someLineEnd",
                    "type1: manyOther LINE_END someLineEnd",
                    "someLineEnd: someLineEnd LINE_END",
                    "someLineEnd:",
                    "manyOther: manyOther OTHER",
                    "manyOther: OTHER",
                    "type2: IF LS manyOther L_BEGIN manyOther L_END RS someLineEnd LB someLineEnd actions RB someLineEnd ELSE someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type2: IF LS manyOther L_BEGIN manyOther L_END RS someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type2: IF LS manyOther RS someLineEnd LB someLineEnd actions RB someLineEnd ELSE someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type2: IF LS manyOther RS someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type3: IF LS manyOther L_BEGIN manyOther L_END RS someLineEnd LB someLineEnd actions RB someLineEnd someElseIf ELSE someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type3: IF LS manyOther L_BEGIN manyOther L_END RS someLineEnd LB someLineEnd actions RB someLineEnd someElseIf someLineEnd",
                    "type3: IF LS manyOther RS someLineEnd LB someLineEnd actions RB someLineEnd someElseIf ELSE someLineEnd LB someLineEnd actions RB someLineEnd",
                    "type3: IF LS manyOther RS someLineEnd LB someLineEnd actions RB someLineEnd someElseIf someLineEnd",
                    "someElseIf: someElseIf someLineEnd elseIf",
                    "someElseIf: elseIf",
                    "elseIf: ELSE IF LS manyOther RS someLineEnd LB someLineEnd actions RB",
                    "elseIf: ELSE IF LS manyOther L_BEGIN manyOther L_END RS someLineEnd LB someLineEnd actions RB"
                };
                yytables.yyrule = rule;
            }

            final yyreduction reduction[] = {
                new yyreduction(0, 1, -1), new yyreduction(1, 1, 0),
                new yyreduction(2, 3, -1), new yyreduction(2, 1, -1),
                new yyreduction(3, 2, -1), new yyreduction(3, 1, -1),
                new yyreduction(4, 2, 1), new yyreduction(4, 1, 2),
                new yyreduction(5, 1, 3), new yyreduction(5, 1, 4),
                new yyreduction(5, 1, 5), new yyreduction(6, 6, 6),
                new yyreduction(6, 3, 7), new yyreduction(7, 2, -1),
                new yyreduction(7, 0, -1), new yyreduction(8, 2, 8),
                new yyreduction(8, 1, 9), new yyreduction(9, 20, 10),
                new yyreduction(9, 13, 11), new yyreduction(9, 17, 12),
                new yyreduction(9, 10, 13), new yyreduction(10, 21, 14),
                new yyreduction(10, 15, 15), new yyreduction(10, 18, 16),
                new yyreduction(10, 12, 17), new yyreduction(11, 3, 18),
                new yyreduction(11, 1, 19), new yyreduction(12, 10, 20),
                new yyreduction(12, 13, 21)
            };
            yytables.yyreduction = reduction;

            final yytokenaction tokenaction[] = {
                new yytokenaction(58, YYAT_SHIFT, 64),
                new yytokenaction(14, YYAT_SHIFT, 18),
                new yytokenaction(21, YYAT_SHIFT, 24),
                new yytokenaction(93, YYAT_SHIFT, 8),
                new yytokenaction(95, YYAT_SHIFT, 6),
                new yytokenaction(58, YYAT_SHIFT, 19),
                new yytokenaction(14, YYAT_SHIFT, 19),
                new yytokenaction(14, YYAT_SHIFT, 20),
                new yytokenaction(58, YYAT_SHIFT, 65),
                new yytokenaction(94, YYAT_SHIFT, 8),
                new yytokenaction(21, YYAT_SHIFT, 25),
                new yytokenaction(95, YYAT_SHIFT, 96),
                new yytokenaction(4, YYAT_SHIFT, 6),
                new yytokenaction(94, YYAT_ERROR, 0),
                new yytokenaction(4, YYAT_SHIFT, 7),
                new yytokenaction(4, YYAT_SHIFT, 8),
                new yytokenaction(70, YYAT_SHIFT, 76),
                new yytokenaction(63, YYAT_SHIFT, 54),
                new yytokenaction(87, YYAT_SHIFT, 8),
                new yytokenaction(63, YYAT_SHIFT, 8),
                new yytokenaction(70, YYAT_SHIFT, 19),
                new yytokenaction(87, YYAT_SHIFT, 91),
                new yytokenaction(50, YYAT_SHIFT, 56),
                new yytokenaction(49, YYAT_SHIFT, 54),
                new yytokenaction(50, YYAT_SHIFT, 8),
                new yytokenaction(49, YYAT_SHIFT, 8),
                new yytokenaction(40, YYAT_SHIFT, 42),
                new yytokenaction(9, YYAT_SHIFT, 6),
                new yytokenaction(40, YYAT_SHIFT, 8),
                new yytokenaction(9, YYAT_SHIFT, 7),
                new yytokenaction(89, YYAT_SHIFT, 8),
                new yytokenaction(88, YYAT_SHIFT, 92),
                new yytokenaction(86, YYAT_SHIFT, 90),
                new yytokenaction(84, YYAT_SHIFT, 8),
                new yytokenaction(80, YYAT_SHIFT, 85),
                new yytokenaction(78, YYAT_SHIFT, 8),
                new yytokenaction(76, YYAT_SHIFT, 82),
                new yytokenaction(73, YYAT_SHIFT, 79),
                new yytokenaction(71, YYAT_SHIFT, 77),
                new yytokenaction(69, YYAT_SHIFT, 75),
                new yytokenaction(66, YYAT_SHIFT, 72),
                new yytokenaction(64, YYAT_SHIFT, 7),
                new yytokenaction(61, YYAT_SHIFT, 68),
                new yytokenaction(57, YYAT_SHIFT, 62),
                new yytokenaction(56, YYAT_SHIFT, 46),
                new yytokenaction(54, YYAT_SHIFT, 46),
                new yytokenaction(53, YYAT_SHIFT, 60),
                new yytokenaction(47, YYAT_SHIFT, 52),
                new yytokenaction(46, YYAT_SHIFT, 51),
                new yytokenaction(43, YYAT_SHIFT, 48),
                new yytokenaction(42, YYAT_SHIFT, 46),
                new yytokenaction(41, YYAT_SHIFT, 45),
                new yytokenaction(36, YYAT_SHIFT, 38),
                new yytokenaction(35, YYAT_SHIFT, 37),
                new yytokenaction(32, YYAT_SHIFT, 8),
                new yytokenaction(30, YYAT_SHIFT, 33),
                new yytokenaction(28, YYAT_SHIFT, 31),
                new yytokenaction(27, YYAT_SHIFT, 30),
                new yytokenaction(26, YYAT_SHIFT, 29),
                new yytokenaction(23, YYAT_SHIFT, 8),
                new yytokenaction(22, YYAT_SHIFT, 26),
                new yytokenaction(6, YYAT_SHIFT, 16),
                new yytokenaction(2, YYAT_SHIFT, 5),
                new yytokenaction(1, YYAT_ACCEPT, 0)
            };
            yytables.yytokenaction = tokenaction;

            final yystateaction stateaction[] = {
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(63, true, YYAT_ERROR, 0),
                new yystateaction(-65477, true, YYAT_REDUCE, 1),
                new yystateaction(0, false, YYAT_REDUCE, 3),
                new yystateaction(-65528, true, YYAT_REDUCE, 5),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65483, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 16),
                new yystateaction(0, false, YYAT_REDUCE, 13),
                new yystateaction(-65513, true, YYAT_REDUCE, 4),
                new yystateaction(0, false, YYAT_REDUCE, 7),
                new yystateaction(0, false, YYAT_REDUCE, 8),
                new yystateaction(0, false, YYAT_REDUCE, 9),
                new yystateaction(0, false, YYAT_REDUCE, 10),
                new yystateaction(-65536, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 2),
                new yystateaction(0, false, YYAT_DEFAULT, 64),
                new yystateaction(0, false, YYAT_REDUCE, 6),
                new yystateaction(0, false, YYAT_DEFAULT, 64),
                new yystateaction(0, false, YYAT_REDUCE, 15),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65535, true, YYAT_DEFAULT, 58),
                new yystateaction(-65478, true, YYAT_DEFAULT, 70),
                new yystateaction(-65484, true, YYAT_REDUCE, 12),
                new yystateaction(0, false, YYAT_DEFAULT, 64),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65485, true, YYAT_ERROR, 0),
                new yystateaction(-65481, true, YYAT_DEFAULT, 70),
                new yystateaction(-65490, true, YYAT_DEFAULT, 87),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65490, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65489, true, YYAT_REDUCE, 11),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(-65493, true, YYAT_DEFAULT, 87),
                new yystateaction(-65495, true, YYAT_DEFAULT, 95),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(-65515, true, YYAT_REDUCE, 20),
                new yystateaction(-65496, true, YYAT_DEFAULT, 95),
                new yystateaction(-65490, true, YYAT_REDUCE, 14),
                new yystateaction(-65492, true, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_REDUCE, 26),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65496, true, YYAT_ERROR, 0),
                new yystateaction(-65499, true, YYAT_DEFAULT, 87),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65518, true, YYAT_REDUCE, 24),
                new yystateaction(-65519, true, YYAT_REDUCE, 18),
                new yystateaction(0, false, YYAT_DEFAULT, 64),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65500, true, YYAT_DEFAULT, 87),
                new yystateaction(-65495, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 25),
                new yystateaction(-65496, true, YYAT_REDUCE, 14),
                new yystateaction(-65498, true, YYAT_REDUCE, 14),
                new yystateaction(-65537, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65504, true, YYAT_DEFAULT, 87),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65524, true, YYAT_REDUCE, 22),
                new yystateaction(-65501, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65507, true, YYAT_DEFAULT, 95),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65507, true, YYAT_DEFAULT, 87),
                new yystateaction(-65522, true, YYAT_ERROR, 0),
                new yystateaction(-65508, true, YYAT_DEFAULT, 87),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65510, true, YYAT_DEFAULT, 95),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65509, true, YYAT_ERROR, 0),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65508, true, YYAT_REDUCE, 19),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65513, true, YYAT_DEFAULT, 95),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_DEFAULT, 94),
                new yystateaction(-65510, true, YYAT_REDUCE, 23),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(-65515, true, YYAT_DEFAULT, 95),
                new yystateaction(-65525, true, YYAT_ERROR, 0),
                new yystateaction(-65516, true, YYAT_DEFAULT, 95),
                new yystateaction(-65513, true, YYAT_REDUCE, 17),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_REDUCE, 14),
                new yystateaction(0, false, YYAT_REDUCE, 27),
                new yystateaction(-65540, true, YYAT_REDUCE, 21),
                new yystateaction(-65534, true, YYAT_DEFAULT, 95),
                new yystateaction(-65536, true, YYAT_DEFAULT, 64),
                new yystateaction(0, false, YYAT_REDUCE, 28)
            };
            yytables.yystateaction = stateaction;

            final yynontermgoto nontermgoto[] = {
                new yynontermgoto(95, 17), new yynontermgoto(95, 11),
                new yynontermgoto(91, 94), new yynontermgoto(95, 14),
                new yynontermgoto(95, 12), new yynontermgoto(95, 13),
                new yynontermgoto(0, 1), new yynontermgoto(0, 2),
                new yynontermgoto(0, 3), new yynontermgoto(5, 15),
                new yynontermgoto(94, 95), new yynontermgoto(94, 10),
                new yynontermgoto(90, 93), new yynontermgoto(5, 4),
                new yynontermgoto(50, 57), new yynontermgoto(50, 44),
                new yynontermgoto(85, 89), new yynontermgoto(83, 88),
                new yynontermgoto(82, 87), new yynontermgoto(81, 86),
                new yynontermgoto(79, 84), new yynontermgoto(77, 83),
                new yynontermgoto(75, 81), new yynontermgoto(74, 80),
                new yynontermgoto(72, 78), new yynontermgoto(68, 74),
                new yynontermgoto(67, 73), new yynontermgoto(65, 71),
                new yynontermgoto(64, 70), new yynontermgoto(63, 55),
                new yynontermgoto(62, 69), new yynontermgoto(60, 67),
                new yynontermgoto(59, 66), new yynontermgoto(57, 63),
                new yynontermgoto(56, 61), new yynontermgoto(52, 59),
                new yynontermgoto(51, 58), new yynontermgoto(48, 53),
                new yynontermgoto(45, 50), new yynontermgoto(43, 49),
                new yynontermgoto(42, 47), new yynontermgoto(40, 43),
                new yynontermgoto(39, 41), new yynontermgoto(38, 40),
                new yynontermgoto(37, 39), new yynontermgoto(34, 36),
                new yynontermgoto(33, 35), new yynontermgoto(31, 34),
                new yynontermgoto(29, 32), new yynontermgoto(25, 28),
                new yynontermgoto(24, 27), new yynontermgoto(20, 23),
                new yynontermgoto(18, 22), new yynontermgoto(16, 21),
                new yynontermgoto(4, 9)
            };
            yytables.yynontermgoto = nontermgoto;

            final yystategoto stategoto[] = {
                new yystategoto(5, 5), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(50, 94), new yystategoto(6, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, 95),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(45, -1), new yystategoto(0, -1),
                new yystategoto(44, -1), new yystategoto(0, -1),
                new yystategoto(44, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(42, -1), new yystategoto(42, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(41, -1),
                new yystategoto(0, -1), new yystategoto(40, -1),
                new yystategoto(0, -1), new yystategoto(39, -1),
                new yystategoto(41, 94), new yystategoto(0, -1),
                new yystategoto(0, 95), new yystategoto(37, -1),
                new yystategoto(36, -1), new yystategoto(38, 94),
                new yystategoto(30, 50), new yystategoto(0, 95),
                new yystategoto(33, -1), new yystategoto(32, -1),
                new yystategoto(0, -1), new yystategoto(31, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(30, -1), new yystategoto(0, 63),
                new yystategoto(3, -1), new yystategoto(28, -1),
                new yystategoto(28, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(27, -1), new yystategoto(26, -1),
                new yystategoto(0, -1), new yystategoto(28, 94),
                new yystategoto(24, -1), new yystategoto(0, -1),
                new yystategoto(23, -1), new yystategoto(17, -1),
                new yystategoto(20, -1), new yystategoto(20, -1),
                new yystategoto(0, 95), new yystategoto(22, 94),
                new yystategoto(18, -1), new yystategoto(0, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(17, -1), new yystategoto(0, 95),
                new yystategoto(19, 94), new yystategoto(15, -1),
                new yystategoto(0, -1), new yystategoto(14, -1),
                new yystategoto(0, -1), new yystategoto(13, -1),
                new yystategoto(0, 95), new yystategoto(15, 94),
                new yystategoto(11, -1), new yystategoto(13, 94),
                new yystategoto(0, -1), new yystategoto(9, -1),
                new yystategoto(0, 95), new yystategoto(0, -1),
                new yystategoto(0, 95), new yystategoto(0, -1),
                new yystategoto(5, -1), new yystategoto(-5, -1),
                new yystategoto(0, -1), new yystategoto(0, -1),
                new yystategoto(6, 95), new yystategoto(-5, -1),
                new yystategoto(0, -1)
            };
            yytables.yystategoto = stategoto;

            yytables.yydestructorref = null;

            yytables.yytokendestref = null;

            yytables.yytokendestbaseref = null;
        }
    }

    // line 486 ".\\Myparser.y"

    public static void parseFile(String inputFileName, String nodeFileName,
            String edgeFileName) {
        try {
            System.setIn(new FileInputStream(inputFileName));
        } catch (FileNotFoundException e) {
            // TODO �Զ���� catch ��
            e.printStackTrace();
        }
        int n = 1;
        Mylexer lexer = new Mylexer();
        Myparser parser = new Myparser();
        parser.nodeFileName = nodeFileName;
        parser.edgeFileName = edgeFileName;
        if (parser.yycreate(lexer)) {
            if (lexer.yycreate(parser)) {
                n = parser.yyparse();
            }
        }
        // System.exit(n);
        /*
         * ����debug String a = "123"; InputStream in = new
         * java.io.StringBufferInputStream(a); System.setIn(in);
         */

    }

    public static void parseString(String inputString, String nodeFileName,
            String edgeFileName) {
        System.setIn(new java.io.StringBufferInputStream(inputString));
        int n = 1;
        Mylexer lexer = new Mylexer();
        Myparser parser = new Myparser();
        parser.nodeFileName = nodeFileName;
        parser.edgeFileName = edgeFileName;
        if (parser.yycreate(lexer)) {
            if (lexer.yycreate(parser)) {
                n = parser.yyparse();
            }
        }
        // System.exit(n);
        /*
         * ����debug String a = "123"; InputStream in = new
         * java.io.StringBufferInputStream(a); System.setIn(in);
         */

    }

    private String nodeFileName = "NodesFile.txt";

    private String edgeFileName = "EdgesFile.txt";

}
