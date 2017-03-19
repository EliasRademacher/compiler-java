package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Elias on 3/15/2017.
 */
public class LeftParenToken extends Token {
    public LeftParenToken() {
    }

    @Override
    public Type getType() {
        return Type.LPAREN;
    }

    @Override
    public ParseTreeElement toParseTreeElement() {
        return null;
    }

    @Override
    public DefaultMutableTreeNode toTreeNode() {
        return null;
    }
}
