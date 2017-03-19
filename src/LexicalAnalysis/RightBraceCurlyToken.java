package LexicalAnalysis;

import SyntacticAnalysis.ParseTreeElement;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by Elias on 3/15/2017.
 */
public class RightBraceCurlyToken extends Token {
    public RightBraceCurlyToken() {
    }

    @Override
    public Type getType() {
        return Type.RBRACE_CURLY;
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
