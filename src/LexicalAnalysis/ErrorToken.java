package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

/**
 * Created by Elias on 3/15/2017.
 */
public class ErrorToken extends Token {

    public ErrorToken() {
    }

    @Override
    public Type getType() {
        return Type.ERROR;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return null;
    }


}
