// generated with ast extension for cup
// version 0.8
// 20/0/2022 17:46:30


package rs.ac.bg.etf.pp1.ast;

public class VarAssList1 extends VarAssList {

    private VarAssList VarAssList;
    private VarAss VarAss;

    public VarAssList1 (VarAssList VarAssList, VarAss VarAss) {
        this.VarAssList=VarAssList;
        if(VarAssList!=null) VarAssList.setParent(this);
        this.VarAss=VarAss;
        if(VarAss!=null) VarAss.setParent(this);
    }

    public VarAssList getVarAssList() {
        return VarAssList;
    }

    public void setVarAssList(VarAssList VarAssList) {
        this.VarAssList=VarAssList;
    }

    public VarAss getVarAss() {
        return VarAss;
    }

    public void setVarAss(VarAss VarAss) {
        this.VarAss=VarAss;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarAssList!=null) VarAssList.accept(visitor);
        if(VarAss!=null) VarAss.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarAssList!=null) VarAssList.traverseTopDown(visitor);
        if(VarAss!=null) VarAss.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarAssList!=null) VarAssList.traverseBottomUp(visitor);
        if(VarAss!=null) VarAss.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarAssList1(\n");

        if(VarAssList!=null)
            buffer.append(VarAssList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarAss!=null)
            buffer.append(VarAss.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarAssList1]");
        return buffer.toString();
    }
}
