import LexicalAnalysis.IDToken;
import LexicalAnalysis.IntTypeToken;
import LexicalAnalysis.Scanner;
import SemanticAnalysis.Pair;
import SemanticAnalysis.SemanticAnalyzer;
import SemanticAnalysis.SymTable;
import LexicalAnalysis.Token;

import SemanticAnalysis.SymTable;
import SyntacticAnalysis.Parser;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertTrue;

/**
 * Created by Markus Woltjer.
 */
public class CheckerTest {
    @Test
    public void intSymbolTest() throws FileNotFoundException {

        FileReader program1FileReader = new FileReader("testResources/simpleDeclaration.cm");
        Scanner scanner = new Scanner(program1FileReader);
        Parser parser = new Parser(scanner);

        Token intTypeToken = new IntTypeToken();
        intTypeToken.setName("int");
        DefaultMutableTreeNode myTree = intTypeToken.toTreeNode();

        Token tokenForX = new IDToken();
        tokenForX.setName("x");
        DefaultMutableTreeNode IDNodeForX = tokenForX.toTreeNode();

        myTree.add(IDNodeForX);
//
//        Token firstToken = new Token();
//        firstToken.setType(Token.Type.INT_TYPE);
//        firstToken.setName("one");
//        firstToken.setIntegerValue(1);
//
//        Token secondToken = new Token();
//        secondToken.setType(Token.Type.INT);
//        secondToken.setName("two");
//        secondToken.setIntegerValue(2);
//
//        Token thirdToken = new Token();
//        thirdToken.setType(Token.Type.INT);
//        thirdToken.setName("three");
//        thirdToken.setIntegerValue(3);
//
//        Token fourthToken = new Token();
//        fourthToken.setType(Token.Type.LBRACE);
//        fourthToken.setName("four");
//        fourthToken.setIntegerValue(4);
//
//        Token fifthToken = new Token();
//        fifthToken.setType(Token.Type.INT_TYPE);
//        fifthToken.setName("five");
//        fifthToken.setIntegerValue(5);
//
//        Token sixthToken = new Token();
//        sixthToken.setType(Token.Type.INT_TYPE);
//        sixthToken.setName("six");
//        sixthToken.setIntegerValue(6);

        //building the tree with INT_TYPE parent of INT and INT parent of INT and INT parent of LBRACE and LBRACE parent of INT_TYPE and INT_TYPE parent of INT_TYPE
        //expecting one int with name saveMe to be saved

//        DefaultMutableTreeNode myAST = new DefaultMutableTreeNode(firstToken);
        SemanticAnalyzer intTest = new SemanticAnalyzer(myTree);
//        intTest.addASTNode(secondToken, myAST);
//        intTest.addASTNode(thirdToken, (DefaultMutableTreeNode) myAST.getLastChild());
//        intTest.addASTNode(fourthToken, (DefaultMutableTreeNode) myAST.getLastChild());
//        intTest.addASTNode(fifthToken, (DefaultMutableTreeNode) myAST.getLastChild());
//        intTest.addASTNode(sixthToken, (DefaultMutableTreeNode) myAST.getLastChild());

        System.out.println("Printing AST and Symbol Table before:");
        intTest.printAST(intTest.getAST());
        intTest.printSymbolTable(intTest.getSymbolTableTree());

        intTest.checkIdentifiers();//fills the symbol table

        System.out.println("Printing AST and Symbol Table after:");
        intTest.printAST(intTest.getAST());
        intTest.printSymbolTable(intTest.getSymbolTableTree());

    }
}
