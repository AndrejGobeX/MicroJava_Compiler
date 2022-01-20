// generated with ast extension for cup
// version 0.8
// 20/0/2022 16:24:7


package rs.ac.bg.etf.pp1.ast;

public class ConstAssChar extends ConstAss {

    private String constName;
    private Character C1;

    public ConstAssChar (String constName, Character C1) {
        this.constName=constName;
        this.C1=C1;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Character getC1() {
        return C1;
    }

    public void setC1(Character C1) {
        this.C1=C1;
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
        buffer.append("ConstAssChar(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+C1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstAssChar]");
        return buffer.toString();
    }
}
