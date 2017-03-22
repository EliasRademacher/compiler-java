package SyntacticAnalysis;

import javax.swing.tree.DefaultMutableTreeNode;

import LexicalAnalysis.*;
import Generic.*;

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

        switch (token.getType()) {
            case INT_TYPE:
                tree = new DefaultMutableTreeNode(new DeclarationStatement(token), true);
                break;
            case IF:
                tree = ifStmt();
                break;
            case WHILE:
                tree = whileStatement();
                break;
            case ID:
                tree = assignStmt();
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


    private DefaultMutableTreeNode stmtSequence() {

        DefaultMutableTreeNode tree = createStatementNodeFromToken();
        DefaultMutableTreeNode p = tree;

        while ((token.getType() != Token.Type.ENDFILE)
                && (token.getType() != Token.Type.END)
                && (token.getType() != Token.Type.ELSE)
                && (token.getType() != Token.Type.UNTIL)) {

            DefaultMutableTreeNode q;

            match(Token.Type.SEMI);
            q = createStatementNodeFromToken();

            if (q != null) {
                if (tree == null) {
                    tree = q;
                    p = q;
                }
                else /* now p cannot be NULL either */ {
                    //p->sibling = q;

                    DefaultMutableTreeNode rootNode = null;

                    if (p.getParent() == null) {
                        rootNode = new DefaultMutableTreeNode();
                        p.setParent(rootNode);
                    } else {
                        rootNode = (DefaultMutableTreeNode) p.getParent();
                    }

                    rootNode.add(q);
                    p = q;
                }
            }

            // System.out.println(token.getName());
        }

        return tree;
    }

    private DefaultMutableTreeNode assignStmt() {
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
            DefaultMutableTreeNode newChild = exp();
            tree.add(newChild);
        }
        return tree;
    }

    private DefaultMutableTreeNode ifStmt() {
        DefaultMutableTreeNode tree = newStmtNode(new IfStatement());
        match(Token.Type.IF);
        if (null != tree) {
            tree.add(exp());
        }
        match(Token.Type.THEN);

        if (null != tree) {
            tree.add(stmtSequence());
        }

        if (token.getType() == Token.Type.ELSE) {
            match(Token.Type.ELSE);
            if (null != tree) {
                tree.add(stmtSequence());
            }
        }

        match(Token.Type.END);
        return tree;
    }


    private DefaultMutableTreeNode whileStatement() {
        DefaultMutableTreeNode tree =
                new DefaultMutableTreeNode(new WhileStatement());
        match(Token.Type.WHILE);
        match(Token.Type.LBRACE_CURLY);
        if (tree != null) {
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

        return tree;
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
        return tree;
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
            default:
                syntaxError("unexpected token -> "
                        + Utils.tokenToString(token));
                token = getToken();
                break;
        }

        return tree;
    }

    /**
     * Returns a new tree node with the provided expression as its
     * userobject. And sets the line number field.
     */
    private DefaultMutableTreeNode newExpNode(Expression expression) {
        expression.setLineNumber(Globals.lineno);
        return new DefaultMutableTreeNode(expression);
    }

    /**
     * Returns a new tree node with the provided statement as its
     * userobject. And sets the line number field.
     */
    private DefaultMutableTreeNode newStmtNode(Statement statement) {
        statement.setLineNumber(Globals.lineno);
        return new DefaultMutableTreeNode(statement);
    }


    /**
     * This is the primary function of the parser.
     * It constructs the syntax tree.
     */
    public DefaultMutableTreeNode parse() {
        DefaultMutableTreeNode tree = null;
        token = scanner.getToken();


        tree = stmtSequence();  // Why is a semicolon expected here?


        if (token.getType() != Token.Type.ENDFILE) {
            syntaxError("Code ends before file\n");
        }
        return tree;
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
