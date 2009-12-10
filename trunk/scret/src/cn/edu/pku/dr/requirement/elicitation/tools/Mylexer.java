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
 * Mylexer.java Java source file generated from Mylexer.l. Date: 07/29/08 Time:
 * 16:40:47 ALex Version: 2.07
 ******************************************************************************/

// line 1 ".\\Mylexer.l"
/*******************************************************************************
 * Mylexer.l ParserWizard generated Lex file. Date: 2008��5��17��
 ******************************************************************************/

// line 37 "Mylexer.java"
import yl.*;

// ///////////////////////////////////////////////////////////////////////////
// Mylexer

public class Mylexer extends yyflexer {
    // line 17 ".\\Mylexer.l"

    // place any extra class members here

    // line 48 "Mylexer.java"
    public Mylexer() {
        yytables();
        // line 22 ".\\Mylexer.l"

        // place any extra initialisation code here

        // line 55 "Mylexer.java"
    }

    public static final int INITIAL = 0;

    protected static yyftables yytables = null;

    public final int yyaction(int action) {
        // line 39 ".\\Mylexer.l"

        // extract yylval for use later on in actions
        Myparser.YYSTYPE yylval = (Myparser.YYSTYPE) yyparserref.yylvalref;

        // line 68 "Mylexer.java"
        yyreturnflg = true;
        switch (action) {
            case 1: {
                // line 46 ".\\Mylexer.l"
                break;
                // line 75 "Mylexer.java"
            }
            case 2: {
                // line 47 ".\\Mylexer.l"
                break;
                // line 81 "Mylexer.java"
            }
            case 3: {
                // line 48 ".\\Mylexer.l"
                break;
                // line 87 "Mylexer.java"
            }
            case 4: {
                // line 49 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.LS;
                // line 93 "Mylexer.java"
            }
            case 5: {
                // line 50 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.LS;
                // line 99 "Mylexer.java"
            }
            case 6: {
                // line 51 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.RS;
                // line 105 "Mylexer.java"
            }
            case 7: {
                // line 52 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.RS;
                // line 111 "Mylexer.java"
            }
            case 8: {
                // line 53 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.LB;
                // line 117 "Mylexer.java"
            }
            case 9: {
                // line 54 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.RB;
                // line 123 "Mylexer.java"
            }
            case 10: {
                // line 55 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.L_END;
                // line 129 "Mylexer.java"
            }
            case 11: {
                // line 56 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.L_BEGIN;
                // line 135 "Mylexer.java"
            }
            case 12: {
                // line 57 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.SEPRATOR;
                // line 141 "Mylexer.java"
            }
            case 13: {
                // line 58 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.IF;
                // line 147 "Mylexer.java"
            }
            case 14: {
                // line 59 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.ELSE;
                // line 153 "Mylexer.java"
            }
            case 15: {
                // line 60 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.LINE_END;
                // line 159 "Mylexer.java"
            }
            case 16: {
                // line 61 ".\\Mylexer.l"
                yylval.str = readStr();
                System.out.print(yylval.str);
                return Myparser.OTHER;
                // line 165 "Mylexer.java"
            }
            default:
                break;
        }
        yyreturnflg = false;
        return 0;
    }

    protected final void yytables() {
        yystext_size = 100;
        yysunput_size = 100;
        yytext_max = 0;
        yyunput_max = 0;

        yycreatetables();
        yymatch = yytables.yymatch;
        yytransition = yytables.yytransition;
        yystate = yytables.yystate;
        yybackup = yytables.yybackup;
    }

    public static synchronized final void yycreatetables() {
        if (yytables == null) {
            yytables = new yyftables();

            final short match[] = {
                0
            };
            yytables.yymatch = match;

            final yytransition transition[] = {
                new yytransition(0, 0), new yytransition(4, 1),
                new yytransition(5, 1), new yytransition(22, 19),
                new yytransition(18, 16), new yytransition(6, 1),
                new yytransition(19, 17), new yytransition(23, 19),
                new yytransition(14, 56), new yytransition(15, 56),
                new yytransition(7, 7), new yytransition(21, 18),
                new yytransition(16, 10), new yytransition(25, 20),
                new yytransition(26, 22), new yytransition(27, 23),
                new yytransition(28, 24), new yytransition(24, 19),
                new yytransition(29, 26), new yytransition(30, 27),
                new yytransition(31, 28), new yytransition(32, 29),
                new yytransition(33, 30), new yytransition(34, 31),
                new yytransition(7, 1), new yytransition(35, 32),
                new yytransition(36, 33), new yytransition(37, 34),
                new yytransition(38, 35), new yytransition(39, 37),
                new yytransition(40, 38), new yytransition(41, 39),
                new yytransition(8, 1), new yytransition(9, 1),
                new yytransition(42, 41), new yytransition(20, 17),
                new yytransition(43, 42), new yytransition(44, 43),
                new yytransition(45, 44), new yytransition(46, 45),
                new yytransition(47, 46), new yytransition(48, 47),
                new yytransition(49, 48), new yytransition(50, 49),
                new yytransition(51, 50), new yytransition(52, 51),
                new yytransition(53, 52), new yytransition(54, 53),
                new yytransition(55, 54), new yytransition(17, 11),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(10, 1), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(11, 1),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(0, 0),
                new yytransition(0, 0), new yytransition(12, 1),
                new yytransition(0, 0), new yytransition(13, 1)
            };
            yytables.yytransition = transition;

            final yystate state[] = {
                new yystate(0, 0, 0), new yystate(56, -8, 0),
                new yystate(1, 0, 0), new yystate(0, 0, 16),
                new yystate(0, 0, 3), new yystate(0, 0, 15),
                new yystate(0, 0, 2), new yystate(0, -22, 1),
                new yystate(0, 0, 4), new yystate(0, 0, 7),
                new yystate(0, -52, 16), new yystate(0, -46, 16),
                new yystate(0, 0, 8), new yystate(0, 0, 9),
                new yystate(0, 0, 5), new yystate(0, 0, 6),
                new yystate(0, -91, 0), new yystate(0, -29, 0),
                new yystate(0, -84, 0), new yystate(0, -66, 0),
                new yystate(0, -51, 0), new yystate(0, 0, 10),
                new yystate(0, -62, 0), new yystate(0, -55, 0),
                new yystate(0, -103, 0), new yystate(0, 0, 11),
                new yystate(0, -65, 0), new yystate(0, -16, 0),
                new yystate(0, -85, 0), new yystate(0, -48, 0),
                new yystate(0, -73, 0), new yystate(0, -86, 0),
                new yystate(0, -10, 0), new yystate(0, -69, 0),
                new yystate(0, -81, 0), new yystate(0, -67, 0),
                new yystate(0, 0, 13), new yystate(0, -68, 0),
                new yystate(0, -65, 0), new yystate(0, -79, 0),
                new yystate(0, 0, 14), new yystate(0, -67, 0),
                new yystate(0, -59, 0), new yystate(0, -46, 0),
                new yystate(0, -63, 0), new yystate(0, -73, 0),
                new yystate(0, -57, 0), new yystate(0, -73, 0),
                new yystate(0, -55, 0), new yystate(0, -73, 0),
                new yystate(0, -67, 0), new yystate(0, -69, 0),
                new yystate(0, 11, 0), new yystate(0, -48, 0),
                new yystate(0, -47, 0), new yystate(0, 0, 12),
                new yystate(-3, -65280, 0)
            };
            yytables.yystate = state;

            final boolean backup[] = {
                false, false, false, false, false, false, false, false, false,
                false, false, false, false, false, false, false, false
            };
            yytables.yybackup = backup;
        }
    }

    // line 64 ".\\Mylexer.l"

    // ///////////////////////////////////////////////////////////////////////////
    // programs section
    protected String readStr() {
        String r = new String();
        for (int i = 0; i < yyleng; ++i)
            r += yytext[i];
        return r;
    }
}
