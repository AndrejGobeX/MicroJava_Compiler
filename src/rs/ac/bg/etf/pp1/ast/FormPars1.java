// generated with ast extension for cup
// version 0.8
// 19/0/2022 11:25:4


package rs.ac.bg.etf.pp1.ast;

public class FormPars1 extends FormPars {

    private Type Type;
    private VarAss VarAss;

    public FormPars1 (Type Type, VarAss VarAss) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.VarAss=VarAss;
        if(VarAss!=null) VarAss.setParent(this);
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(VarAss!=null) VarAss.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarAss!=null) VarAss.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarAss!=null) VarAss.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormPars1(\n");

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

        buffer.append(tab);
        buffer.append(") [FormPars1]");
        return buffer.toString();
    }
}
