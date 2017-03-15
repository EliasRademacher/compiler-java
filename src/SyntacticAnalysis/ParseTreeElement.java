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
}
