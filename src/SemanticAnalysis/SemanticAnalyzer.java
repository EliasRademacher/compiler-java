package SemanticAnalysis;

import Generic.Utils;
import LexicalAnalysis.Token;
import SyntacticAnalysis.ParseTreeElement;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import static javafx.application.Platform.exit;
//import java.util.List;

/**
 * Created by Markus Woltjer.
 */


public class SemanticAnalyzer {

    private DefaultMutableTreeNode AST;//passed in from parser

    private DefaultMutableTreeNode symbolTableTree;//tree of symbol tables

    public SemanticAnalyzer(DefaultMutableTreeNode AST) {
        this.AST = AST;
        symbolTableTree = new DefaultMutableTreeNode(new SymTable());
    }

    public DefaultMutableTreeNode getAST() {
        return AST;
    }

    public void addASTNode(Token tokenToAdd, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tokenToAdd);
        parent.add(newNode);
    }
    public DefaultMutableTreeNode getSymbolTableTree() {
        return symbolTableTree;
    }

    public void addSymbolTableTreeNode(DefaultMutableTreeNode parent){
        DefaultMutableTreeNode child = new DefaultMutableTreeNode();
        parent.add(child);
    }

    public void printAST(DefaultMutableTreeNode myAST) {
        ParseTreeElement temp = (ParseTreeElement) myAST.getUserObject();
        String tokenString = Utils.tokenToString(temp.getToken());
        System.out.println("AST Node: " + temp.toString());
        System.out.println("AST Token: " + tokenString);
        for(int i = 0; i < myAST.getChildCount(); i++) {
            printAST((DefaultMutableTreeNode) myAST.getChildAt(i));
        }
    }

    public void printSymbolTable(DefaultMutableTreeNode mySymbolTable) {
        SymTable temp = (SymTable) mySymbolTable.getUserObject();
        String tableString = temp.getTable().toString();
        System.out.println("Symbol Table: " + tableString);
        for(int i = 0; i < mySymbolTable.getChildCount(); i++) {
            printSymbolTable((DefaultMutableTreeNode) mySymbolTable.getChildAt(i));
        }
    }

    public void checkIdentifiers() {
        Enumeration dfs = AST.depthFirstEnumeration();
        DefaultMutableTreeNode currSymbolTree = symbolTableTree;
        while (dfs.hasMoreElements()) {
            DefaultMutableTreeNode currentASTNode = (DefaultMutableTreeNode) dfs.nextElement();
            ParseTreeElement parseTreeElement = (ParseTreeElement) currentASTNode.getUserObject();
            SymTable currentTable = (SymTable) currentASTNode.getUserObject();
            if(parseTreeElement.getToken().getType() == Token.Type.INT_TYPE) {
                DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) currentASTNode.getFirstChild();
                ParseTreeElement childElement = (ParseTreeElement) childNode.getUserObject();
                if(childElement.getToken().getType() == Token.Type.ID) {
                    //insert into symbol table
                    //System.out.println("Identified an Int");
                    currentTable.addPair(new Pair<Token.Type,String>(childElement.getToken().getType(), childElement.getToken().getName()));
                }
                else {
                    System.out.println("Int type must be followed by an identifier.");
                    exit();
                }
            }
            else if(parseTreeElement.getToken().getType() == Token.Type.LBRACE_CURLY) {
                this.addSymbolTableTreeNode(currSymbolTree);
            }
        }
    }

//    public void typeCheck() {
//        Enumeration traverse = AST.depthFirstEnumeration();
//        SymTable currentTable = (SymTable) symbolTableTree.getUserObject();
//        DefaultMutableTreeNode currentASTNode = (DefaultMutableTreeNode) AST.getRoot();
//        while (dfs.hasMoreElements()) {
//    }

}

//check for misuse of on identifier, check for misuse and save into table
//traverse AST recursively, checking:
    //switch on operators and predefined functions, type checking, including actual parameters
    //else, check symbols tabel tree for variable (break with error if undeclared in scope)