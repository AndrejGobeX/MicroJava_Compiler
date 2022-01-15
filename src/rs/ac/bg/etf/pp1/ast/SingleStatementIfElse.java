// generated with ast extension for cup
// version 0.8
// 15/0/2022 17:30:5


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementIfElse extends SingleStatement {

    private ConditionHelper ConditionHelper;
    private Statement Statement;
    private Statement Statement1;

    public SingleStatementIfElse (ConditionHelper ConditionHelper, Statement Statement, Statement Statement1) {
        this.ConditionHelper=ConditionHelper;
        if(ConditionHelper!=null) ConditionHelper.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public ConditionHelper getConditionHelper() {
        return ConditionHelper;
    }

    public void setConditionHelper(ConditionHelper ConditionHelper) {
        this.ConditionHelper=ConditionHelper;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionHelper!=null) ConditionHelper.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionHelper!=null) ConditionHelper.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionHelper!=null) ConditionHelper.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementIfElse(\n");

        if(ConditionHelper!=null)
            buffer.append(ConditionHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementIfElse]");
        return buffer.toString();
    }
}
