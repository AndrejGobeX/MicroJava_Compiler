package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.mj.runtime.Code;

import java.util.*;

public class CodeGenerator extends VisitorAdaptor {
    Map<String, Integer> methodAdresses = new HashMap<>();
    Stack<List<Integer>> trueskips = new Stack<>();
    Stack<List<Integer>> falseskips = new Stack<>();
    Stack<Integer> loops = new Stack<>();
    Stack<List<Integer>> continueStack = new Stack<>();
    Stack<List<Integer>> breakStack = new Stack<>();
    public Stack<Obj> currentDesignatorObj = new Stack<Obj>();

    /* Util */

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

    private void call(String name) {
        int pc = Code.pc;
        Code.put(Code.call);
        Code.put2(methodAdresses.get(name) - pc);
    }

    private void backpatch(Stack<List<Integer>> stack){
        List<Integer> ll = stack.peek();
        for(Integer i:ll){
            Code.fixup(i);
        }
    }

    /* Prog */

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
        Struct designatorType = currentDesignatorObj.peek().getType();
        if (designatorType.getKind() == Struct.Int) {
            Code.put(Code.read);
        }
        else if (designatorType.getKind() == Struct.Char) {
            Code.put(Code.bread);
        }
        Code.store(currentDesignatorObj.pop());
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
        currentDesignatorObj.push(designatorIdent.obj);
    }

    public void visit(DesignatorIdentSuper designatorIdent){
        currentDesignatorObj.push(designatorIdent.obj);
    }

    public void visit(DesignatorIdentThis designatorIdent){
        currentDesignatorObj.push(designatorIdent.obj);
    }

    public void visit(DesignatorEExpr designatorEExpr){
        if(currentDesignatorObj.peek().getKind() == Obj.Fld){
            Code.put(Code.dup_x1);
            Code.put(Code.pop);
            Code.load(currentDesignatorObj.pop());
        }
        else
            Code.load(currentDesignatorObj.pop());
        Code.put(Code.dup_x1);
        Code.put(Code.pop);
        currentDesignatorObj.push(designatorEExpr.obj);
    }

    public void visit(DesignatorEIdent designatorEIdent){
        Code.load(currentDesignatorObj.pop());
        currentDesignatorObj.push(designatorEIdent.obj);
    }


    public void visit(DesignatorStatementAssign designatorStatement){
        Code.store(designatorStatement.getDesignator().obj);
        currentDesignatorObj.pop();
    }

    public void visit(DesignatorStatementInc d){
        if(currentDesignatorObj.peek().getKind() == Obj.Elem){
            Code.put(Code.dup2);
            if(currentDesignatorObj.peek().getType().getKind() == Struct.Char)
                Code.put(Code.baload);
            else
                Code.put(Code.aload);
            currentDesignatorObj.pop();
        }
        else if(currentDesignatorObj.peek().getKind() == Obj.Fld){
            Code.put(Code.dup);
            Code.load(d.getDesignator().obj);
            currentDesignatorObj.pop();
        }
        else if(currentDesignatorObj.peek().getKind() == Obj.Var){
            Code.load(currentDesignatorObj.pop());
        }

        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(d.getDesignator().obj);
    }

    public void visit(DesignatorStatementNo d){
        if(d.getDesignator().getDesignatorList() instanceof DesignatorListE){
            call(currentDesignatorObj.peek().getName());
            if(currentDesignatorObj.pop().getType() != Tab.noType){
                Code.put(Code.pop);
            }
        }
    }

    public void visit(DesignatorStatementActPars d){
        if(d.getDesignator().getDesignatorList() instanceof DesignatorListE){
            call(currentDesignatorObj.peek().getName());
            if(currentDesignatorObj.pop().getType() != Tab.noType){
                Code.put(Code.pop);
            }
        }
    }

    public void visit(DesignatorStatementDec d){
        if(currentDesignatorObj.peek().getKind() == Obj.Elem){
            Code.put(Code.dup2);
            if(currentDesignatorObj.peek().getType().getKind() == Struct.Char)
                Code.put(Code.baload);
            else
                Code.put(Code.aload);
            currentDesignatorObj.pop();
        }
        else if(currentDesignatorObj.peek().getKind() == Obj.Fld){
            Code.put(Code.dup);
            Code.load(d.getDesignator().obj);
            currentDesignatorObj.pop();
        }
        else if(currentDesignatorObj.peek().getKind() == Obj.Var){
            Code.load(currentDesignatorObj.pop());
        }

        Code.loadConst(1);
        Code.put(Code.sub);
        Code.store(d.getDesignator().obj);
    }

    /* Method */

    public void visit(MethodName methodName){
        methodAdresses.put(methodName.getMethodName(), Code.pc);
        Code.put(Code.enter);
        int ls=0, ps=0;
        for (Obj obj : methodName.obj.getLocalSymbols()) {
            if(obj.getFpPos()==-1)++ls;
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

    public void visit(FactorDesignator factor){
        Code.load(currentDesignatorObj.pop());
    }

    public void visit(FactorDesignatorNo factor){
        if(factor.getDesignator().getDesignatorList() instanceof DesignatorListE){
            call(currentDesignatorObj.pop().getName());
        }
    }

    public void visit(FactorDesignatorActPars factor){
        if(factor.getDesignator().getDesignatorList() instanceof DesignatorListE){
            call(currentDesignatorObj.pop().getName());
        }
    }

    public void visit(FactorNew factor){
        Code.put(Code.new_);
        Code.put2(factor.obj.getType().getNumberOfFields());
    }

    public void visit(FactorNewAr factor){
        Code.put(Code.newarray);
        if(factor.getType().struct.getKind() == Struct.Char)
            Code.put(0);
        else
            Code.put(1);
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

    public void visit(Expr2 expr){
        Code.put(Code.neg);
    }

    /* Conditions */

    public void visit(SingleStatementContinue singleStatementContinue){
        continueStack.peek().add(1 + Code.pc);
        Code.putJump(0);
    }

    public void visit(SingleStatementBreak singleStatementContinue){
        breakStack.peek().add(1 + Code.pc);
        Code.putJump(0);
    }

    public void visit(SingleStatementDoWhile singleStatementDoWhile){
        Code.putJump(loops.pop());
        backpatch(breakStack);
        backpatch(falseskips);
        breakStack.pop();
        falseskips.pop();
    }

    public void visit(DoWhile doWhile){
        breakStack.push(new LinkedList<>());
        continueStack.push(new LinkedList<>());
        loops.push(Code.pc);
    }

    public void visit(While w){
        backpatch(continueStack);
        continueStack.pop();
    }

    public void visit(SingleStatementIfElse single){
        backpatch(trueskips);
        trueskips.pop();
    }

    public void visit(IfEnd ifEnd){
        if(ifEnd.getParent() instanceof SingleStatementIfElse){
            trueskips.push(new LinkedList<>());
            trueskips.peek().add(1 + Code.pc);
            Code.putJump(0);
        }
        backpatch(falseskips);
        falseskips.pop();
    }

    public void visit(OrBreak orBreak){
        trueskips.peek().add(1 + Code.pc);
        Code.putJump(0);
        backpatch(falseskips);
        falseskips.peek().clear();
    }

    public void visit(CondFactR condFact){
        int r;
        if(condFact.getRelop() instanceof RelopNeq)
            r = Code.ne;
        else if(condFact.getRelop() instanceof RelopEq)
            r = Code.eq;
        else if(condFact.getRelop() instanceof RelopGE)
            r = Code.ge;
        else if(condFact.getRelop() instanceof RelopGT)
            r = Code.gt;
        else if(condFact.getRelop() instanceof RelopLE)
            r = Code.le;
        else
            r = Code.lt;

        falseskips.peek().add(1 + Code.pc);
        Code.putFalseJump(r, 0);
    }

    public void visit(CondFactE condFact){
        Code.loadConst(0);
        falseskips.peek().add(1 + Code.pc);
        Code.putFalseJump(Code.ne, 0);
    }

    public void visit(CBegin cbegin){
        trueskips.push(new LinkedList<>());
        falseskips.push(new LinkedList<>());
    }

    public void visit(CEnd cend){
        backpatch(trueskips);
        trueskips.pop();
    }

}
