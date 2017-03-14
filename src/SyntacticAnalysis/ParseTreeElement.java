package SyntacticAnalysis;

import LexicalAnalysis.Token;

/**
 * Designed to correspond to the TreeNode struct in the Louden compiler.
 */
public class ParseTreeElement {

    protected int lineNumber;

    protected Token.TokenType token; /* Needed if the element is not an int type or variable? */
    protected int value; /* Needed if the element is a int type? */
    protected String name; /* Needed if the element is a variable? */


    protected enum ExpressionType {
        VOID, INTEGER, BOOLEAN
    }

    /* For type checking of expressions.*/
    /* TODO: Move to SyntacticAnalysis.Expression class? */
    protected ExpressionType expressionType;
}
