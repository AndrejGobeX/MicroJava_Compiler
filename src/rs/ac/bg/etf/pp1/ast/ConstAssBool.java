// generated with ast extension for cup
// version 0.8
// 20/0/2022 17:46:30


package rs.ac.bg.etf.pp1.ast;

public class ConstAssBool extends ConstAss {

    private String constName;
    private Integer B1;

    public ConstAssBool (String constName, Integer B1) {
        this.constName=constName;
        this.B1=B1;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Integer getB1() {
        return B1;
    }

    public void setB1(Integer B1) {
        this.B1=B1;
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
        buffer.append("ConstAssBool(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+B1);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstAssBool]");
        return buffer.toString();
    }
}
