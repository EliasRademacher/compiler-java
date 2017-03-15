import LexicalAnalysis.Token;
import SyntacticAnalysis.*;
import LexicalAnalysis.Scanner;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by Elias on 3/11/2017.
 */
public class ParserTest {


    @Test
    public void stmtSequence() throws FileNotFoundException {
        FileReader program1FileReader = new FileReader("testResources/program1.cm");
        Scanner scanner = new Scanner(program1FileReader);
        Parser parser = new Parser(scanner);
//        parser.setToken(LexicalAnalysis.Token.Type.IF);

        DefaultMutableTreeNode tree = parser.stmtSequence();

        assertNotNull(tree);

    }

    @Test
    public void testCreateStatementNodeFromToken() throws FileNotFoundException {
        FileReader program1FileReader = new FileReader("testResources/program1.cm");
        Scanner scanner = new Scanner(program1FileReader);
        Parser parser = new Parser(scanner);

        parser.setToken(new Token(Token.Type.IF));
        DefaultMutableTreeNode tree = parser.createStatementNodeFromToken();
        assertTrue(tree.getUserObject().getClass().equals(IfStatement.class));

        parser.setToken(new Token(Token.Type.WHILE));
        tree = parser.createStatementNodeFromToken();
        assertTrue(tree.getUserObject().getClass().equals(WhileStatement.class));

        parser.setToken(new Token(Token.Type.ID));
        tree = parser.createStatementNodeFromToken();
        assertTrue(tree.getUserObject().getClass().equals(AssignmentStatement.class));

        parser.setToken(new Token(Token.Type.READ));
        tree = parser.createStatementNodeFromToken();
        assertTrue(tree.getUserObject().getClass().equals(ReadStatement.class));

        parser.setToken(new Token(Token.Type.WRITE));
        tree = parser.createStatementNodeFromToken();
        assertTrue(tree.getUserObject().getClass().equals(WriteStatement.class));

        parser.setToken(new Token(Token.Type.ERROR));
        tree = parser.createStatementNodeFromToken();
        assertTrue(tree == null);
    }



}