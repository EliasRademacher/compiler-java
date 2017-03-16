package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Elias on 3/15/2017.
 */
public class SemicolonToken extends Token {
    public SemicolonToken() {
    }

    @Override
    public Type getType() {
        return Type.SEMI;
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
