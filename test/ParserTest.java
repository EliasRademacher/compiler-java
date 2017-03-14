import SyntacticAnalysis.Parser;
import LexicalAnalysis.Scanner;
import org.junit.Test;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileReader;

import static org.junit.Assert.*;

/**
 * Created by Elias on 3/11/2017.
 */
public class ParserTest {


    @Test
    public void stmtSequence() throws Exception {
        FileReader program1FileReader = new FileReader("testResources/program1.cm");
        Scanner scanner = new Scanner(program1FileReader);
        Parser parser = new Parser(scanner);
//        parser.setToken(LexicalAnalysis.Token.Type.IF);

        DefaultMutableTreeNode tree = parser.stmtSequence();

        assertNotNull(tree);

    }

}