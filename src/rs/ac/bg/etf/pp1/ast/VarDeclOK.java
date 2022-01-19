// generated with ast extension for cup
// version 0.8
// 19/0/2022 11:25:4


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOK extends VarDecl {

    private Type Type;
    private VarAss VarAss;
    private VarAssList VarAssList;

    public VarDeclOK (Type Type, VarAss VarAss, VarAssList VarAssList) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarAss=VarAss;
        if(VarAss!=null) VarAss.setParent(this);
        this.VarAssList=VarAssList;
        if(VarAssList!=null) VarAssList.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public VarAss getVarAss() {
        return VarAss;
    }

    public void setVarAss(VarAss VarAss) {
        this.VarAss=VarAss;
    }

    public VarAssList getVarAssList() {
        return VarAssList;
    }

    public void setVarAssList(VarAssList VarAssList) {
        this.VarAssList=VarAssList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarAss!=null) VarAss.accept(visitor);
        if(VarAssList!=null) VarAssList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarAss!=null) VarAss.traverseTopDown(visitor);
        if(VarAssList!=null) VarAssList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarAss!=null) VarAss.traverseBottomUp(visitor);
        if(VarAssList!=null) VarAssList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOK(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarAss!=null)
            buffer.append(VarAss.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarAssList!=null)
            buffer.append(VarAssList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOK]");
        return buffer.toString();
    }
}
