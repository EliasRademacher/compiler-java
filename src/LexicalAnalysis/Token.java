package LexicalAnalysis;

/**
 * Created by Elias on 3/14/2017.
 *
 */
public class Token {

    private String name;

    private int integerValue;

    public enum Type {
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

    private Type type;

    public Token() {
    }

    public Token(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        if (null == type) {
            this.type = Type.ERROR;
        } else {
            this.type = type;
        }
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
