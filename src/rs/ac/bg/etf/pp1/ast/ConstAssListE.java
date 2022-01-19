// generated with ast extension for cup
// version 0.8
// 19/0/2022 11:25:4


package rs.ac.bg.etf.pp1.ast;

public class ConstAssListE extends ConstAssList {

    public ConstAssListE () {
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
        buffer.append("ConstAssListE(\n");

        buffer.append(tab);
        buffer.append(") [ConstAssListE]");
        return buffer.toString();
    }
}
