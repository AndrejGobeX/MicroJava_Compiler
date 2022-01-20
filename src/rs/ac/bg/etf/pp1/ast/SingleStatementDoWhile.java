// generated with ast extension for cup
// version 0.8
// 20/0/2022 16:24:7


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementDoWhile extends SingleStatement {

    private DoWhile DoWhile;
    private Statement Statement;
    private While While;
    private CBegin CBegin;
    private Condition Condition;
    private CEnd CEnd;

    public SingleStatementDoWhile (DoWhile DoWhile, Statement Statement, While While, CBegin CBegin, Condition Condition, CEnd CEnd) {
        this.DoWhile=DoWhile;
        if(DoWhile!=null) DoWhile.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.While=While;
        if(While!=null) While.setParent(this);
        this.CBegin=CBegin;
        if(CBegin!=null) CBegin.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CEnd=CEnd;
        if(CEnd!=null) CEnd.setParent(this);
    }

    public DoWhile getDoWhile() {
        return DoWhile;
    }

    public void setDoWhile(DoWhile DoWhile) {
        this.DoWhile=DoWhile;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public While getWhile() {
        return While;
    }

    public void setWhile(While While) {
        this.While=While;
    }

    public CBegin getCBegin() {
        return CBegin;
    }

    public void setCBegin(CBegin CBegin) {
        this.CBegin=CBegin;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CEnd getCEnd() {
        return CEnd;
    }

    public void setCEnd(CEnd CEnd) {
        this.CEnd=CEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoWhile!=null) DoWhile.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(While!=null) While.accept(visitor);
        if(CBegin!=null) CBegin.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(CEnd!=null) CEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoWhile!=null) DoWhile.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(While!=null) While.traverseTopDown(visitor);
        if(CBegin!=null) CBegin.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CEnd!=null) CEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoWhile!=null) DoWhile.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(While!=null) While.traverseBottomUp(visitor);
        if(CBegin!=null) CBegin.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CEnd!=null) CEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementDoWhile(\n");

        if(DoWhile!=null)
            buffer.append(DoWhile.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(While!=null)
            buffer.append(While.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CBegin!=null)
            buffer.append(CBegin.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CEnd!=null)
            buffer.append(CEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementDoWhile]");
        return buffer.toString();
    }
}
