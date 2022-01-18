package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CodeGenerator extends VisitorAdaptor {
    Map<String, Integer> methodAdresses = new HashMap<>();

    private void methChr() {
        methodAdresses.put("chr", Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    private void methOrd() {
        methodAdresses.put("ord", Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    private void methLen() {
        methodAdresses.put("len", Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(ProgName progName){
        methOrd();
        methLen();
        methChr();
    }

    public void visit(Program program){
        Code.mainPc = methodAdresses.get("main");
    }

    /* SignleStatements */

    public void visit(SingleStatementRead singleStatement){
        Struct designatorType = singleStatement.getDesignator().obj.getType();
        if (designatorType.getKind() == Struct.Int) {
            Code.put(Code.read);
        }
        else if (designatorType.getKind() == Struct.Char) {
            Code.put(Code.bread);
        }
        Code.store(singleStatement.getDesignator().obj);
    }

    public void visit(SingleStatementPrint singleStatement){
        int kind = singleStatement.getExpr().obj.getType().getKind();
        int ins = kind == Struct.Char ? Code.bprint : Code.print;
        Code.loadConst(1);
        Code.put(ins);
    }

    public void visit(SingleStatementPrintNum singleStatement){
        int kind = singleStatement.getExpr().obj.getType().getKind();
        int width = singleStatement.getN2();
        Code.loadConst(width);
        if(kind == Struct.Char)
            Code.put(Code.bprint);
        else if(kind == Struct.Int)
            Code.put(Code.print);
    }

    /* Designator */

    public void visit(DesignatorIdentIdent designatorIdent){
        if(!(designatorIdent.getParent() instanceof DesignatorStatementAssign)){
            Code.load(designatorIdent.obj);
        }
    }

    public void visit(DesignatorIdentSuper designatorIdent){
        if(!(designatorIdent.getParent() instanceof DesignatorStatementAssign)){
            Code.load(designatorIdent.obj);
        }
    }

    public void visit(DesignatorIdentThis designatorIdent){
        if(!(designatorIdent.getParent() instanceof DesignatorStatementAssign)){
            Code.load(designatorIdent.obj);
        }
    }

    public void visit(DesignatorStatementAssign designatorStatement){
        Code.store(designatorStatement.getDesignator().obj);
    }

    /* Method */

    public void visit(MethodName methodName){
        methodAdresses.put(methodName.getMethodName(), Code.pc);
        Code.put(Code.enter);
        int ls=0, ps=0;
        for (Obj obj : methodName.obj.getLocalSymbols()) {
            if(obj.getFpPos()==0)++ls;
            else ps++;
        }
        Code.put(ps);
        Code.put(ls);
    }

    public void visit(MethodDeclType methodDecl){
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    public void visit(MethodDeclTypeFormPars methodDecl){
        Code.put(Code.exit);
        Code.put(Code.return_);
    }

    /* Factors */

    public void visit(FactorNumConst factor){
        Code.loadConst(factor.getN1());
    }

    public void visit(FactorCharConst factor){
        Code.loadConst(factor.getC1());
    }

    public void visit(FactorBoolConst factor){
        Code.loadConst(factor.getB1());
    }

    public void visit(FactorNew factor){

    }

    /* Terms */

    public void visit(Term2 term){
        if(term.getMulop() instanceof MulopS)
            Code.put(Code.mul);
        else if(term.getMulop() instanceof MulopD)
            Code.put(Code.div);
        else
            Code.put(Code.rem);
    }

    public void visit(Terms2 term){
        if(term.getAddop() instanceof AddopP)
            Code.put(Code.add);
        else
            Code.put(Code.sub);
    }


}
