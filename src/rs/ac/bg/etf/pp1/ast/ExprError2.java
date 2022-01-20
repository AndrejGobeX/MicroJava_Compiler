// generated with ast extension for cup
// version 0.8
// 20/0/2022 16:24:7


package rs.ac.bg.etf.pp1.ast;

public class ExprError2 extends ExprError {

    public ExprError2 () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExprError2(\n");

        buffer.append(tab);
        buffer.append(") [ExprError2]");
        return buffer.toString();
    }
}
