package LexicalAnalysis;

/**
 * Created by Elias on 3/14/2017.
 *
 */
public class Token {

    private String name;

    private int integerValue;

    public enum TokenType {
        /* book-keeping tokens */
        ENDFILE, ERROR,
        /* reserved words */
        IF, THEN, ELSE, END, WHILE, UNTIL, READ, WRITE,
        INT_TYPE, VOID,
        /* multicharacter tokens */
        ID, INT,
        /* special symbols */
        ASSIGN, EQ, LT, PLUS, MINUS, TIMES, OVER, LPAREN,
        RPAREN, LBRACE, RBRACE, LBRACE_CURLY, RBRACE_CURLY, SEMI
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }
}
