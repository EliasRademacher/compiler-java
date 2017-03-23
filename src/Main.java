import LexicalAnalysis.Scanner;
import SemanticAnalysis.SemanticAnalyzer;
import SyntacticAnalysis.Parser;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {

        FileReader simpleFunctionFileReader = null;

        try {
            simpleFunctionFileReader = new FileReader(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("could not find the file specified.");
            return;
        }

        Scanner scanner = new Scanner(simpleFunctionFileReader);

        Parser parser = new Parser(scanner);
        DefaultMutableTreeNode AST = parser.parse();

        SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(AST);

        /* Build Symbol Table Tree. */
        semanticAnalyzer.checkIdentifiers();

        /* Perform Type Checking. */
        semanticAnalyzer.traverseTypeCheck();

    }
}
