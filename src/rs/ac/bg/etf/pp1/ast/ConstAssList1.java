// generated with ast extension for cup
// version 0.8
// 15/0/2022 17:30:5


package rs.ac.bg.etf.pp1.ast;

public class ConstAssList1 extends ConstAssList {

    private ConstAssList ConstAssList;
    private ConstAss ConstAss;

    public ConstAssList1 (ConstAssList ConstAssList, ConstAss ConstAss) {
        this.ConstAssList=ConstAssList;
        if(ConstAssList!=null) ConstAssList.setParent(this);
        this.ConstAss=ConstAss;
        if(ConstAss!=null) ConstAss.setParent(this);
    }

    public ConstAssList getConstAssList() {
        return ConstAssList;
    }

    public void setConstAssList(ConstAssList ConstAssList) {
        this.ConstAssList=ConstAssList;
    }

    public ConstAss getConstAss() {
        return ConstAss;
    }

    public void setConstAss(ConstAss ConstAss) {
        this.ConstAss=ConstAss;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAssList!=null) ConstAssList.accept(visitor);
        if(ConstAss!=null) ConstAss.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAssList!=null) ConstAssList.traverseTopDown(visitor);
        if(ConstAss!=null) ConstAss.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAssList!=null) ConstAssList.traverseBottomUp(visitor);
        if(ConstAss!=null) ConstAss.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstAssList1(\n");

        if(ConstAssList!=null)
            buffer.append(ConstAssList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAss!=null)
            buffer.append(ConstAss.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstAssList1]");
        return buffer.toString();
    }
}
