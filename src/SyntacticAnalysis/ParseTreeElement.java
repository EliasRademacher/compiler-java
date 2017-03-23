package SyntacticAnalysis;

import LexicalAnalysis.Token;


public class ParseTreeElement {

    public enum Type {
        VOID, INTEGER, BOOLEAN
    }

    protected int lineNumber;

    protected Token token;
    /* For type checking of expressions.*/
    protected Type type;

    public ParseTreeElement() {
    }

    public ParseTreeElement(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
