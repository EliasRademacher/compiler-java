package SemanticAnalysis;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;

/**
 * Created by Elias on 3/4/2017.
 */


public class SemanticAnalyzer {


    public DefaultMutableTreeNode AST;//passed in from parser

    //global symbol table
    public DefaultMutableTreeNode createGlobals(Object SymbolTable) {
        ArrayList<Pair<String,String>> symTable = new ArrayList<Pair<String, String>>();
        return new DefaultMutableTreeNode(symTable);
    };

    public void addSymbolTable(DefaultMutableTreeNode parent){

    }
}

//check for misuse of identifiers
    //add error string and remove node
//create symbols tables in tree
    //checking if symbol is already in scope
//traverse AST recursively, checking:
    //switch on operators and predefined functions, type checking, including actual parameters
    //else, check symbols tabel tree for variable (break with error if undeclared in scope)

        Actual and formal parameter mismatch.