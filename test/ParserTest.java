import Generic.Utils;
import LexicalAnalysis.IDToken;
import LexicalAnalysis.IntTypeToken;
import LexicalAnalysis.Token;
import SyntacticAnalysis.*;
import LexicalAnalysis.Scanner;
import junit.framework.TestCase;
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
public class ParserTest  extends TestCase {

    public void testParseSimpleAssignment() throws FileNotFoundException {
        FileReader simpleAssignmentFileReader =
                new FileReader("testResources/simpleAssignment.cm");
        Scanner scanner = new Scanner(simpleAssignmentFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        assertNotNull(actualTree);
    }


    public void testParseSimpleDeclaration() throws FileNotFoundException {
        FileReader simpleDeclarationFileReader =
                new FileReader("testResources/simpleDeclaration.cm");
        Scanner scanner = new Scanner(simpleDeclarationFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        DefaultMutableTreeNode IDNode = (DefaultMutableTreeNode) actualTree.getFirstChild();
        IDExpression idExpression = (IDExpression) IDNode.getUserObject();

        assertEquals(Token.Type.ID, idExpression.getToken().getType());
        assertEquals("myNewVariable", idExpression.getToken().getName());
        assertTrue(IDNode.isLeaf());
        assertNotNull(actualTree);
    }


    public void testParseSimpleIf() throws FileNotFoundException {
        FileReader simpleIfFileReader =
                new FileReader("testResources/simpleIf.cm");
        Scanner scanner = new Scanner(simpleIfFileReader);
        Parser parser = new Parser(scanner);

        DefaultMutableTreeNode actualTree = parser.parse();

        assertNotNull(actualTree);
    }


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

}