package SyntacticAnalysis;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import LexicalAnalysis.*;
import Generic.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import sun.security.util.DerEncoder;

public class Parser {

    /* holds current token */
    private Token token;

    private Scanner scanner;

    public Parser() {
        this.token = new Token();
    }

    public Parser(Scanner scanner) {
        this.scanner = scanner;
        this.token = new Token();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    private void syntaxError(String message) {
        Listing.getInstance().write(
                "\n>>> Syntax error at line " + Globals.lineno + ": " + message
        );
        Globals.Error = true;
    }

    /* TODO: pass in token as parameter to match()? */
    private void match(Token.Type expectedTokenType) {
        if (token.getType() == expectedTokenType) {
            token = scanner.getToken();
        } else {
            String tokenString = Utils.tokenToString(token);
            syntaxError("unexpected token type-> " + tokenString);
        }
    }

    /* Creates a new tree node based on the current token. */
    /* Corresponds to function "statement()" in "PARSE.C". */
    public DefaultMutableTreeNode createStatementNodeFromToken() {
        DefaultMutableTreeNode tree = null;

        if (token.getType() == null) {
            token.setType(Token.Type.ERROR);
        }

        switch (token.getType()) {
            case IF:
                tree = new DefaultMutableTreeNode(new IfStatement(), true);
                break;
            case WHILE:
                tree = new DefaultMutableTreeNode(new WhileStatement(), true);
                break;
            case ID:
                tree = new DefaultMutableTreeNode(new AssignmentStatement(), true);
                break;
            case READ:
                tree = new DefaultMutableTreeNode(new ReadStatement(), true);
                break;
            case WRITE:
                tree = new DefaultMutableTreeNode(new WriteStatement(), true);
                break;
            default:
                String tokenString = Utils.tokenToString(token);
                syntaxError("unexpected token -> " + tokenString);
                token = scanner.getToken();
                break;
        }

        return tree;
    }

    /* 1. Creates a new Statement node based on this.token
    *  2. If the current node is a semicolon, its sets the sibling of the
    *       new node to be the next node after the semicolon.
    *
    */
    public DefaultMutableTreeNode stmtSequence() {

        DefaultMutableTreeNode tree = createStatementNodeFromToken();
        DefaultMutableTreeNode p = tree;

        while ((token.getType() != Token.Type.ENDFILE)
                && (token.getType() != Token.Type.END)
                && (token.getType() != Token.Type.ELSE)
                && (token.getType() != Token.Type.UNTIL)) {

            DefaultMutableTreeNode q;
            match(Token.Type.SEMI); /* set this.token to LexicalAnalysis.Token.Type.SEMI. */
            q = createStatementNodeFromToken();

            if (q != null) {
                if (tree == null) {
                    tree = q;
                    p = q;
                }
                else /* now p cannot be NULL either */ {
                    //p->sibling = q;

                    if (p.getParent() == null) {
                        p.setParent(new DefaultMutableTreeNode());
                    }

                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) p.getParent();
                    parent.add(q);

                    p = q;
                }
            }

            System.out.println(token.getName());
        }

        return tree;
    }

    DefaultMutableTreeNode assignStmt() {
        DefaultMutableTreeNode tree =
                newStmtNode(new AssignmentStatement());

        if (null != tree && token.getType() == Token.Type.ID) {
            AssignmentStatement assignmentStatement =
                    new AssignmentStatement(token);
            tree.setUserObject(assignmentStatement);
        }

        match(Token.Type.ID);
        match(Token.Type.ASSIGN);

        if (null != tree) {
            tree.add(exp());
        }

        return tree;

    }

    private DefaultMutableTreeNode exp() {
        DefaultMutableTreeNode tree = simpleExp();

        if (token.getType() == Token.Type.LT ||
                token.getType() == Token.Type.EQ) {
            OperationExpression operationExpression =
                    new OperationExpression(token);
            DefaultMutableTreeNode p =
                    newExpNode(operationExpression);
            if (null != p) {
                p.add(tree);
                tree = p;
            }
            match(token.getType());
            if (null != tree) {
                tree.add(simpleExp());
            }
        }

        return null;
    }

    private DefaultMutableTreeNode simpleExp() {
        DefaultMutableTreeNode tree = term();
        while (token.getType() == Token.Type.PLUS ||
                token.getType() == Token.Type.MINUS) {
            DefaultMutableTreeNode p =
                    newExpNode(new OperationExpression(token));
            if (null != p) {
                p.add(tree);
                tree = p;
                match(token.getType());
                tree.add(term());
            }
        }

        return tree;
    }

    private DefaultMutableTreeNode term() {
        DefaultMutableTreeNode tree = factor();

        while (token.getType() == Token.Type.TIMES ||
                token.getType() == Token.Type.OVER) {

            OperationExpression expression = new OperationExpression(token);
            DefaultMutableTreeNode p = newExpNode(expression);

            if (null != p) {
                p.add(tree);
                tree = p;
                match(token.getType());
                p.add(factor());
            }
        }
        return null;
    }

    private DefaultMutableTreeNode factor() {
        DefaultMutableTreeNode tree = null;
        switch (token.getType()) {
            case INT:
                tree = newExpNode(new ConstExpression());
                if (null != tree &&
                        token.getType() == Token.Type.INT) {
                    ConstExpression expression =
                            (ConstExpression) tree.getUserObject();
                    expression.setToken(token);
                }
                match(Token.Type.INT);
                break;
            case ID:
                tree = newExpNode(new IDExpression());
                if (null != tree &&
                        token.getType() == Token.Type.ID) {
                    IDExpression expression =
                            (IDExpression) tree.getUserObject();
                    expression.setToken(token);
                }
                match(Token.Type.ID);
                break;
            case LPAREN:
                match(Token.Type.LPAREN);
                tree = exp();
                match(Token.Type.RPAREN);
                break;
        }

        return null;
    }

    private DefaultMutableTreeNode newExpNode(Expression expression) {
        return null;
    }

    private DefaultMutableTreeNode newStmtNode(Statement statement) {
        return null;
    }


    public DefaultMutableTreeNode createDeclarationNode() {

        token = new Token();

        DefaultMutableTreeNode treeNode = null;

        while(token.getType() != Token.Type.ENDFILE
                && token.getType() != Token.Type.ERROR) {

            token = scanner.getToken();
            DefaultMutableTreeNode newChild = token.toTreeNode();

            if (newChild == null) {
                break;
            }

            if (treeNode == null) {
                treeNode = newChild;
            } else {
                treeNode.add(newChild);
            }
        }



        return treeNode;
    }
}
