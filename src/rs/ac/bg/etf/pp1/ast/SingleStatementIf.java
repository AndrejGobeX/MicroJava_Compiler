// generated with ast extension for cup
// version 0.8
// 20/0/2022 17:46:30


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementIf extends SingleStatement {

    private CBegin CBegin;
    private ConditionHelper ConditionHelper;
    private CEnd CEnd;
    private Statement Statement;
    private IfEnd IfEnd;

    public SingleStatementIf (CBegin CBegin, ConditionHelper ConditionHelper, CEnd CEnd, Statement Statement, IfEnd IfEnd) {
        this.CBegin=CBegin;
        if(CBegin!=null) CBegin.setParent(this);
        this.ConditionHelper=ConditionHelper;
        if(ConditionHelper!=null) ConditionHelper.setParent(this);
        this.CEnd=CEnd;
        if(CEnd!=null) CEnd.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.IfEnd=IfEnd;
        if(IfEnd!=null) IfEnd.setParent(this);
    }

    public CBegin getCBegin() {
        return CBegin;
    }

    public void setCBegin(CBegin CBegin) {
        this.CBegin=CBegin;
    }

    public ConditionHelper getConditionHelper() {
        return ConditionHelper;
    }

    public void setConditionHelper(ConditionHelper ConditionHelper) {
        this.ConditionHelper=ConditionHelper;
    }

    public CEnd getCEnd() {
        return CEnd;
    }

    public void setCEnd(CEnd CEnd) {
        this.CEnd=CEnd;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public IfEnd getIfEnd() {
        return IfEnd;
    }

    public void setIfEnd(IfEnd IfEnd) {
        this.IfEnd=IfEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CBegin!=null) CBegin.accept(visitor);
        if(ConditionHelper!=null) ConditionHelper.accept(visitor);
        if(CEnd!=null) CEnd.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(IfEnd!=null) IfEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CBegin!=null) CBegin.traverseTopDown(visitor);
        if(ConditionHelper!=null) ConditionHelper.traverseTopDown(visitor);
        if(CEnd!=null) CEnd.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(IfEnd!=null) IfEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CBegin!=null) CBegin.traverseBottomUp(visitor);
        if(ConditionHelper!=null) ConditionHelper.traverseBottomUp(visitor);
        if(CEnd!=null) CEnd.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(IfEnd!=null) IfEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementIf(\n");

        if(CBegin!=null)
            buffer.append(CBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionHelper!=null)
            buffer.append(ConditionHelper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CEnd!=null)
            buffer.append(CEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IfEnd!=null)
            buffer.append(IfEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementIf]");
        return buffer.toString();
    }
}
