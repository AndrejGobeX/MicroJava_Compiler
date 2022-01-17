package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.ProgName;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

import java.util.HashMap;
import java.util.Map;

public class CodeGenerator extends VisitorAdaptor {
    Map<String, Integer> methodAdresses = new HashMap<>();

    public void visit(ProgName progName){

    }
}
