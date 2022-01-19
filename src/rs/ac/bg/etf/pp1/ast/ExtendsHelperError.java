// generated with ast extension for cup
// version 0.8
// 19/0/2022 11:25:4


package rs.ac.bg.etf.pp1.ast;

public class ExtendsHelperError extends ExtendsHelper {

    public ExtendsHelperError () {
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
        buffer.append("ExtendsHelperError(\n");

        buffer.append(tab);
        buffer.append(") [ExtendsHelperError]");
        return buffer.toString();
    }
}
