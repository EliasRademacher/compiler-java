import Generic.Utils;
import LexicalAnalysis.IDToken;
import LexicalAnalysis.IntTypeToken;
import LexicalAnalysis.Token;
import SyntacticAnalysis.*;
import LexicalAnalysis.Scanner;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Elias on 3/11/2017.
 */
public class ParserTest {
    @Test
    public void testParseSimpleAssignment() throws FileNotFoundException {
        FileReader simpleAssignmentFileReader =
                new FileReader("testResources/simpleAssignment.cm");
        Scanner scanner = new Scanner(simpleAssignmentFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        assertNotNull(actualTree);
    }

    @Test
    public void testParseSimpleDeclaration() throws FileNotFoundException {
        FileReader simpleDeclarationFileReader =
                new FileReader("testResources/simpleDeclaration.cm");
        Scanner scanner = new Scanner(simpleDeclarationFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        assertNotNull(actualTree);
    }

    @Test
    public void testParseSimpleIf() throws FileNotFoundException {
        FileReader simpleIfFileReader =
                new FileReader("testResources/simpleIf.cm");
        Scanner scanner = new Scanner(simpleIfFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        assertNotNull(actualTree);
    }

    @Test
    public void testParseSimpleWhile() throws FileNotFoundException {
        FileReader simpleWhileFileReader =
                new FileReader("testResources/simpleWhile.cm");
        Scanner scanner = new Scanner(simpleWhileFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        Enumeration treeEnumeration = actualTree.depthFirstEnumeration();

        while (treeEnumeration.hasMoreElements()) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeEnumeration.nextElement();
            ParseTreeElement parseTreeElement = (ParseTreeElement) child.getUserObject();

            Token token = parseTreeElement.getToken();
            String tokenString = "(null token)";
            if (null != token) {
                tokenString = Utils.tokenToString(token);
            }

//            System.out.println("Node: " + parseTreeElement.toString() + "\n" + "Token: " + tokenString);
        }


        assertNotNull(actualTree);
    }





//    @Test
//    public void stmtSequence() throws FileNotFoundException {
//        FileReader program1FileReader = new FileReader("testResources/program1.cm");
//        Scanner scanner = new Scanner(program1FileReader);
//        Parser parser = new Parser(scanner);
////        parser.setToken(LexicalAnalysis.Token.Type.IF);
//
//        DefaultMutableTreeNode tree = parser.stmtSequence();
//
//        assertNotNull(tree);
//
//    }

//    @Test
//    public void testCreateStatementNodeFromToken() throws FileNotFoundException {
//        FileReader program1FileReader = new FileReader("testResources/program1.cm");
//        Scanner scanner = new Scanner(program1FileReader);
//        Parser parser = new Parser(scanner);
//
//        parser.setToken(new Token(Token.Type.IF));
//        DefaultMutableTreeNode tree = parser.createStatementNodeFromToken();
//        assertTrue(tree.getUserObject().getClass().equals(IfStatement.class));
//
//        parser.setToken(new Token(Token.Type.WHILE));
//        tree = parser.createStatementNodeFromToken();
//        assertTrue(tree.getUserObject().getClass().equals(WhileStatement.class));
//
//        parser.setToken(new Token(Token.Type.ID));
//        tree = parser.createStatementNodeFromToken();
//        assertTrue(tree.getUserObject().getClass().equals(AssignmentStatement.class));
//
//        parser.setToken(new Token(Token.Type.READ));
//        tree = parser.createStatementNodeFromToken();
//        assertTrue(tree.getUserObject().getClass().equals(ReadStatement.class));
//
//        parser.setToken(new Token(Token.Type.WRITE));
//        tree = parser.createStatementNodeFromToken();
//        assertTrue(tree.getUserObject().getClass().equals(WriteStatement.class));
//
//        parser.setToken(new Token(Token.Type.ERROR));
//        tree = parser.createStatementNodeFromToken();
//        assertTrue(tree == null);
//    }



    /* Create a testParse tree for "int x;" */
    @Test
    public void testCreateDeclarationNode() throws FileNotFoundException {
        FileReader program1FileReader = new FileReader("testResources/simpleDeclaration.cm");
        Scanner scanner = new Scanner(program1FileReader);
        Parser parser = new Parser(scanner);
        DefaultMutableTreeNode actualTree = parser.createDeclarationNode();


        Token intTypeToken = new IntTypeToken();
        DefaultMutableTreeNode expectedTree = intTypeToken.toTreeNode();

        Token tokenForX = new IDToken();
        tokenForX.setName("x");
        DefaultMutableTreeNode IDNodeForX = tokenForX.toTreeNode();

        expectedTree.add(IDNodeForX);


        Class expectedClass = expectedTree.getUserObject().getClass();
        Class<?> actualClass = actualTree.getUserObject().getClass();
        assertEquals(expectedClass, actualClass);

        ParseTreeElement expectedElement =
                (ParseTreeElement) expectedTree.getUserObject();
        ParseTreeElement actualElement =
                (ParseTreeElement) actualTree.getUserObject();
        String expectedName = expectedElement.getToken().getName();
        String actualName = actualElement.getToken().getName();
        assertEquals(expectedName, actualName);




        Enumeration expectedChildren = expectedTree.children();
        Enumeration actualChildren = actualTree.children();

        while(expectedChildren.hasMoreElements()) {

            DefaultMutableTreeNode expectedChild =
                    (DefaultMutableTreeNode) expectedChildren.nextElement();
            DefaultMutableTreeNode actualChild =
                    (DefaultMutableTreeNode) actualChildren.nextElement();
            expectedClass = expectedChild.getUserObject().getClass();
            actualClass = actualChild.getUserObject().getClass();
            assertEquals(expectedClass, actualClass);

            System.out.println("Expected Node Class: " + expectedClass.getName() + ";\t" +
                    "Actual Node Class: " + actualClass.getName());


            expectedElement = (ParseTreeElement) expectedChild.getUserObject();
            actualElement = (ParseTreeElement) actualChild.getUserObject();
            expectedName = expectedElement.getToken().getName();
            actualName = actualElement.getToken().getName();
            assertEquals(expectedName, actualName);


            System.out.println("Expected Token Name: " + expectedName + ";\t" +
                    "Actual Token Name: " + actualName + "\n");
        }


    }

}