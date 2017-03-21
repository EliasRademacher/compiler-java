package SyntacticAnalysis;

import LexicalAnalysis.Token;


public class ParseTreeElement {

    protected int lineNumber;

    protected Token token;

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
}
