package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class SemanticAnalyzer extends VisitorAdaptor {

	boolean returnFound = false;
	boolean errorDetected = false;
	boolean relopEq = false;
	Struct currentType = null;
	Struct currentMethodType = null;
	Obj currentDesignatorObj = null;
	Obj currentClassObj = null;
	Obj currentMethodObj = null;
	public int nVars;
	int formParCnt = 1;
	Queue<Obj> paramsQueue = null;
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	/* Helper */
	
	public static boolean assignable(Struct left, Struct right) {
		boolean check = right.assignableTo(left);
		if(!check){
			if(left.getKind() == right.getKind()){
				if(right.getKind() == Struct.Class) {
					while(right != null && right.getElemType() != left.getElemType())
						right = right.getElemType();
					if(right != null)
						check = true;
				}
				else if(right.getKind() == Struct.Array){
					if(right.getElemType() == left.getElemType())
						check = true;
				}
				else
					check = true;
			}
		}
		return check;
	}

	public static String objToStr(Obj typeNode) {
		if(typeNode == null)
			typeNode = Tab.noObj;
		String kind = "";
		switch (typeNode.getKind()) {
			case Obj.Con:  kind = "Con "; break;
			case Obj.Var:  kind = "Var "; break;
			case Obj.Type: kind = "Type "; break;
			case Obj.Meth: kind = "Meth "; break;
			case Obj.Fld:  kind = "Fld "; break;
			case Obj.Prog: kind = "Prog "; break;
		}
		String type = "";
		switch (typeNode.getType().getKind()) {
			case Struct.None:
				type = "notype";
				break;
			case Struct.Int:
				type = "int";
				break;
			case Struct.Char:
				type = "char";
				break;
			case Struct.Array:
				type = "Arr of ";

				switch (typeNode.getType().getElemType().getKind()) {
					case Struct.None:
						type += "notype";
						break;
					case Struct.Int:
						type += "int";
						break;
					case Struct.Char:
						type += "char";
						break;
					case Struct.Class:
						type += "Class";
						break;
				}
				break;
			case Struct.Class:
				type = "Class";
				break;
		}
		return "["+kind+" "+typeNode.getName()+": "+type+", "+typeNode.getAdr()+", "+typeNode.getLevel()+"]";
	}

	/* Promenljive */
	
	public void visit(VarAssBr varAss){
		if(Tab.currentScope.findSymbol(varAss.getVarName()) != null)
		{
			report_error("Promenljiva "+varAss.getVarName()+" ponovo deklarisana", varAss);
			return;
		}
		//report_info("Deklarisana promenljiva "+ varAss.getVarName()+ "[]", varAss);
		boolean fld = (currentClassObj != null && currentMethodObj == null);
		varAss.obj = Tab.insert(fld?Obj.Fld:Obj.Var, varAss.getVarName(), new Struct(Struct.Array, currentType));
	}
	
	public void visit(VarAssNo varAss) {
		if(Tab.currentScope.findSymbol(varAss.getVarName()) != null)
		{
			report_error("Promenljiva "+varAss.getVarName()+" ponovo deklarisana", varAss);
			return;
		}
		//report_info("Deklarisana promenljiva "+ varAss.getVarName(), varAss);
		boolean fld = (currentClassObj != null && currentMethodObj == null);
		varAss.obj = Tab.insert(fld?Obj.Fld:Obj.Var, varAss.getVarName(), currentType);
	}
	
    public void visit(ProgName progName){
    	Obj def = Tab.find("chr");
		Iterator<Obj> iterator = def.getLocalSymbols().iterator();
		iterator.next().setFpPos(1);
		def = Tab.find("ord");
		iterator = def.getLocalSymbols().iterator();
		iterator.next().setFpPos(1);
		def = Tab.find("len");
		iterator = def.getLocalSymbols().iterator();
		iterator.next().setFpPos(1);
		progName.obj = Tab.insert(Obj.Prog, progName.getProgramName(), Tab.noType);
    	Tab.openScope();
    }
    
    public void visit(Program program){
    	nVars = Tab.currentScope.getnVars();
		Obj mainMeth = Tab.find("main");
		if(Tab.noObj == mainMeth || null == mainMeth || mainMeth.getKind() != Obj.Meth)
			report_error("main funkcija nije pronadjena", null);
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(ConstAssNum constAss) {
    	if(Tab.currentScope.findSymbol(constAss.getConstName()) != null)
		{
			report_error("Konstanta "+constAss.getConstName()+" ponovo deklarisana", constAss);
			return;
		}
    	if(currentType.getKind() != Struct.Int)
    	{
    		report_error("Pogresan tip vrednosti za konstantu "+constAss.getConstName(), constAss);
    		return;
    	}
    	//report_info("Deklarisana konstanta "+ constAss.getConstName(), constAss);
		Obj varNode = Tab.insert(Obj.Con, constAss.getConstName(), currentType);
    }
    
    public void visit(ConstAssBool constAss) {
    	if(Tab.currentScope.findSymbol(constAss.getConstName()) != null)
		{
			report_error("Konstanta "+constAss.getConstName()+" ponovo deklarisana", constAss);
			return;
		}
    	if(currentType.getKind() != Struct.Bool)
    	{
    		report_error("Pogresan tip vrednosti za konstantu "+constAss.getConstName(), constAss);
    		return;
    	}
    	//report_info("Deklarisana konstanta "+ constAss.getConstName(), constAss);
		Obj varNode = Tab.insert(Obj.Con, constAss.getConstName(), currentType);
    }
    
    public void visit(ConstAssChar constAss) {
    	if(Tab.currentScope.findSymbol(constAss.getConstName()) != null)
		{
			report_error("Konstanta "+constAss.getConstName()+" ponovo deklarisana", constAss);
			return;
		}
    	if(currentType.getKind() != Struct.Char)
    	{
    		report_error("Pogresan tip vrednosti za konstantu "+constAss.getConstName(), constAss);
    		return;
    	}
    	//report_info("Deklarisana konstanta "+ constAss.getConstName(), constAss);
		Obj varNode = Tab.insert(Obj.Con, constAss.getConstName(), currentType);
    }
    
    public void visit(Type type){
    	Obj typeNode = Tab.find(type.getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", type);
    		type.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			type.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    	currentType = type.struct;
    }
    
    /* Promenljive */
    
    /* Klase */
    
    public void visit(ExtendsHelper1 extendsHelper) {
    	Obj typeNode = Tab.find(extendsHelper.getExtensionTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + extendsHelper.getExtensionTypeName() + " u tabeli simbola! ", extendsHelper);
    		extendsHelper.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			extendsHelper.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + extendsHelper.getExtensionTypeName() + " ne predstavlja tip!", extendsHelper);
    			extendsHelper.struct = Tab.noType;
    		}
    	}
    }
    
    public void visit(ClassNameNo className) {
    	className.obj = Tab.insert(Obj.Type, className.getClassName(), new Struct(Struct.Class));
    	Tab.openScope();
		currentClassObj = className.obj;
    }
    
    public void visit(ClassNameExtends className) {
    	Struct type = new Struct(Struct.Class);
    	type.setElementType(className.getExtendsHelper().struct);
    	className.obj = Tab.insert(Obj.Type, className.getClassName(), type);
    	Tab.openScope();
		currentClassObj = className.obj;
    }

    public void visit(ClassDeclNothing classDecl) {
    	Tab.chainLocalSymbols(classDecl.getClassName().obj.getType());
    	Tab.closeScope();
		currentClassObj = null;
    }
    
    public void visit(ClassDeclMethods classDecl) {
    	Tab.chainLocalSymbols(classDecl.getClassName().obj.getType());
    	Tab.closeScope();
		currentClassObj = null;
    }
    
    public void visit(ClassDeclConstructor classDecl) {
    	if(!classDecl.getConstructorName().equals(classDecl.getClassName().obj.getName()))
    	{
    		report_error("Konstruktor nema isti naziv kao klasa", classDecl);
    	}
    	Tab.chainLocalSymbols(classDecl.getClassName().obj.getType());
    	Tab.closeScope();
		currentClassObj = null;
    }

    public void visit(RecordName recordName) {
    	recordName.obj = Tab.insert(Obj.Type, recordName.getRecordName(), new Struct(Struct.Class));
    	Tab.openScope();
		currentClassObj = recordName.obj;
    }
    
    public void visit(RecordDecl classDecl) {
    	Tab.chainLocalSymbols(classDecl.getRecordName().obj.getType());
    	Tab.closeScope();
		currentClassObj = null;
    }
    
    /* Klase */
    /* Metode */
    
    public void visit(MethodType1 methodType) {
    	Obj typeNode = Tab.find(methodType.getType().getTypeName());
    	if(typeNode == Tab.noObj){
    		report_error("Nije pronadjen tip " + methodType.getType().getTypeName() + " u tabeli simbola! ", methodType);
    		methodType.struct = Tab.noType;
    	}else{
    		if(Obj.Type == typeNode.getKind()){
    			methodType.struct = typeNode.getType();
    		}else{
    			report_error("Greska: Ime " + methodType.getType().getTypeName() + " ne predstavlja tip!", methodType);
    			methodType.struct = Tab.noType;
    		}
    	}
    	currentMethodType = methodType.struct;
    }

    public void visit(MethodTypeVoid methodType) {
    	currentMethodType = Tab.noType;
    }
    
    public void visit(MethodName methodName) {
    	methodName.obj = Tab.insert(Obj.Meth, methodName.getMethodName(), currentMethodType);
    	Tab.openScope();
		formParCnt = 1;
		currentMethodObj = methodName.obj;
    }
    
    public void visit(MethodDeclType methodDecl) {
    	if(currentMethodType != Tab.noType && !returnFound) {
    		report_error("Return nije pronadjen za funkciju "+methodDecl.getMethodName().getMethodName(), methodDecl);
    	}
    	Tab.chainLocalSymbols(methodDecl.getMethodName().obj);
    	Tab.closeScope();
    	returnFound = false;
		currentMethodObj = null;
    }
    
    public void visit(MethodDeclTypeFormPars methodDecl) {
    	if(currentMethodType != Tab.noType && !returnFound) {
    		report_error("Return nije pronadjen za funkciju "+methodDecl.getMethodName().getMethodName(), methodDecl);
    	}
    	Tab.chainLocalSymbols(methodDecl.getMethodName().obj);
    	Tab.closeScope();
    	returnFound = false;
		currentMethodObj = null;
    }

	public void visit(FormPars1 formPars){
		formPars.getVarAss().obj.setFpPos(formParCnt++);
	}

	public void visit(FormPars2 formPars){
		formPars.getVarAss().obj.setFpPos(formParCnt++);
	}

	public void visit(ActPars1 actPars){
		paramsQueue = new LinkedList<>();
		paramsQueue.add(actPars.getExpr().obj);
	}

	public void visit(ActPars2 actPars){
		paramsQueue.add(actPars.getExpr().obj);
	}
    
    /* Metode */
    /* Ostalo */

    public void visit(SingleStatementReturnExpr singleStatement) {
    	if(currentMethodType == null) {
			report_error("return van funckije", singleStatement);
			return;
		}
		returnFound = true;
		if(!assignable(currentMethodType,  singleStatement.getExpr().obj.getType())){
			report_error("return izraz nije istog tipa kao povratna vrednost", singleStatement);
		}
    }

	public void visit(SingleStatementReturn singleStatement){
		if(currentMethodType == null){
			report_error("return van funckije", singleStatement);
			return;
		}
		returnFound = true;
		if(Tab.noType != currentMethodType){
			report_error("return izraz nije istog tipa kao povratna vrednost", singleStatement);
		}
	}

	public void visit(SingleStatementRead singleStatement){
		if(singleStatement.getDesignator().obj.getKind() != Obj.Var &&
				singleStatement.getDesignator().obj.getKind() != Obj.Elem ||
				(singleStatement.getDesignator().obj.getType().getKind() != Struct.Int
				&& singleStatement.getDesignator().obj.getType().getKind() != Struct.Bool
				&& singleStatement.getDesignator().obj.getType().getKind() != Struct.Char)){
			report_error("Parametar nije promenljiva tipa int, bool ili char", singleStatement);
		}
	}

	public void visit(SingleStatementPrint singleStatement){
		if(singleStatement.getExpr().obj.getKind() != Obj.Var ||
				(singleStatement.getExpr().obj.getType().getKind() != Struct.Int
						&& singleStatement.getExpr().obj.getType().getKind() != Struct.Bool
						&& singleStatement.getExpr().obj.getType().getKind() != Struct.Char)){
			report_error("Parametar nije promenljiva tipa int, bool ili char", singleStatement);
		}
	}

	public void visit(SingleStatementPrintNum singleStatement){
		if(singleStatement.getExpr().obj.getKind() != Obj.Var ||
				(singleStatement.getExpr().obj.getType().getKind() != Struct.Int
						&& singleStatement.getExpr().obj.getType().getKind() != Struct.Bool
						&& singleStatement.getExpr().obj.getType().getKind() != Struct.Char)){
			report_error("Parametar nije promenljiva tipa int, bool ili char", singleStatement);
		}
	}

	/* DesignatorStatements */

    public void visit(DesignatorStatementAssign designatorStatement){
		if(designatorStatement.getDesignator().obj == null
			|| designatorStatement.getExprError().obj == null)
			return;
        if(!assignable(designatorStatement.getDesignator().obj.getType(),
                designatorStatement.getExprError().obj.getType())){
            report_error("Tip izraza sa desne strane nije kompatibilan sa tipom promenljive sa leve strane", designatorStatement);
        }
    }

	public void visit(DesignatorStatementInc designatorStatementInc){
		if(designatorStatementInc.getDesignator().obj.getType().getKind() != Struct.Int){
			report_error("Tip promenljive nije int", designatorStatementInc);
		}
	}

	public void visit(DesignatorStatementDec designatorStatementInc){
		if(designatorStatementInc.getDesignator().obj.getType().getKind() != Struct.Int){
			report_error("Tip promenljive nije int", designatorStatementInc);
		}
	}

	public void visit(DesignatorStatementNo designatorStatement){
		if(designatorStatement.getDesignator().obj.getKind() != Obj.Meth){
			report_error("Izraz ne predstavlja funkciju", designatorStatement);
		}
	}

	public void visit(DesignatorStatementActPars designatorStatement){
		if(designatorStatement.getDesignator().obj.getKind() != Obj.Meth){
			report_error("Izraz ne predstavlja funkciju", designatorStatement);
		}
		Collection<Obj> members = designatorStatement.getDesignator().obj.getLocalSymbols();
		Iterator<Obj> iterator = members.iterator();
		if(members.size()<paramsQueue.size()){
			report_error("Broj parametara ne odgovara pozvanoj funkciji", designatorStatement);
			return;
		}
		int cnt = 1;
		while (!paramsQueue.isEmpty()){
			Obj currentPar = iterator.next();
			Obj currentArg = paramsQueue.poll();
			if(currentPar.getFpPos() != cnt++){
				report_error("Broj parametara ne odgovara pozvanoj funkciji", designatorStatement);
				return;
			}
			if(!assignable(currentPar.getType(), currentArg.getType())){
				report_error("Parametri nisu kompatibilni sa funkcijom", designatorStatement);
				return;
			}
		}
		if(iterator.hasNext())
			if(iterator.next().getFpPos() == cnt++){
				report_error("Broj parametara ne odgovara pozvanoj funkciji", designatorStatement);
				return;
			}
	}

    /* Designator */

	public void visit(DesignatorIdentIdent designatorIdent){
		Obj typeNode = null;
		Scope cScope = Tab.currentScope();
		while((typeNode == null || typeNode == Tab.noObj) && cScope != null){
			typeNode = cScope.findSymbol(designatorIdent.getDesignatorName());
			cScope = cScope.getOuter();
		}
		if(typeNode == null || typeNode == Tab.noObj)
			report_error("Nije pronadjena promenljiva " + designatorIdent.getDesignatorName() + " u tabeli simbola! ", designatorIdent);
		else{
			if(Obj.Var != typeNode.getKind() && Obj.Con != typeNode.getKind() && Obj.Fld != typeNode.getKind() && Obj.Fld != typeNode.getKind() && Obj.Meth != typeNode.getKind()){
				report_error("Greska: Ime " + designatorIdent.getDesignatorName() + " ne predstavlja promenljivu!", designatorIdent);
			}
			else if(typeNode.getFpPos() == 0)
				report_info("Iskoriscena "+(Obj.Var == typeNode.getKind() ? "promenljiva " : "konstanta ")+designatorIdent.getDesignatorName()+" "+objToStr(typeNode), designatorIdent);
			else
				report_info("Iskoriscen formalni parametar "+designatorIdent.getDesignatorName()+" "+objToStr(typeNode), designatorIdent);
		}
		designatorIdent.obj = typeNode;
		currentDesignatorObj = designatorIdent.obj;
	}

	public void visit(DesignatorIdentSuper designatorIdent){
		if(currentClassObj != null)
			if(currentClassObj.getType().getElemType() != null)
				designatorIdent.obj = new Obj(Obj.Var, "super", currentClassObj.getType().getElemType());
			else
				report_error("Ova klasa nije izvedena ni iz jedne druge klase", designatorIdent);
		else
			report_error("Super van klase", designatorIdent);
		currentDesignatorObj = designatorIdent.obj;
	}

	public void visit(DesignatorIdentThis designatorIdent){
		if(currentClassObj != null)
			designatorIdent.obj = currentClassObj;
		else
			report_error("This van klase", designatorIdent);
		currentDesignatorObj = designatorIdent.obj;
	}

	public void visit(DesignatorEIdent designatorElem){
		if(currentDesignatorObj == null)
			return;
		if(currentDesignatorObj.getType().getKind() != Struct.Class){
			report_error("Nije moguce pristupiti polju objekta koji nije klasa ili record", designatorElem);
		}
		Struct currentClassType = currentDesignatorObj.getType();
		Obj field = null;
		while(currentClassType != null) {
			field = currentClassType.getMembersTable().searchKey(designatorElem.getIdentName());
			if (field != null && field != Tab.noObj) {
				currentDesignatorObj = field;
				report_info("Pristup polju "+objToStr(currentDesignatorObj), designatorElem);
				return;
			}
			currentClassType = currentClassType.getElemType();
		}
		report_error("Nepoznato polje " + designatorElem.getIdentName(), designatorElem);
		currentDesignatorObj = null;
	}

	public void visit(DesignatorEExpr designatorElem){
		if(currentDesignatorObj == null)
			return;
		if(currentDesignatorObj.getType().getKind() != Struct.Array){
			report_error("Nije moguce indeksirati objekat koji nije niz", designatorElem);
		}
        report_info("Pristup elementu niza "+currentDesignatorObj.getName(), designatorElem);
		currentDesignatorObj = new Obj(Obj.Elem, "elem", currentDesignatorObj.getType().getElemType());
	}

	public void visit(Designator designator){
		designator.obj = currentDesignatorObj;
		currentDesignatorObj = null;
	}

	/* Expr */

	public void visit(ExprError1 exprError){
		exprError.obj = exprError.getExpr().obj;
	}

	public void visit(Expr1 expr){
		expr.obj = expr.getTerms().obj;
	}

	public void visit(Expr2 expr){
		if(expr.getTerms().obj.getType().getKind() != Struct.Int){
			report_error("Nije moguce primeniti - na objekat koji nije int", expr);
		}
		expr.obj = new Obj(Obj.Var, "nekiint", Tab.intType);
	}

	public void visit(CondFactE condFact){
		if(condFact.getExpr().obj.getType().getKind() != Struct.Bool){
			report_error("Uslov nije tipa bool", condFact);
		}
		condFact.obj = new Obj(Obj.Var, "", new Struct(Struct.Bool));
	}

	public void visit(CondFactR condFact){
		if(condFact.getExpr().obj.getType().getKind() != condFact.getExpr().obj.getType().getKind()){
			report_error("Izrazi nisu istog tipa", condFact);
		}
		else if(condFact.getExpr().obj.getType().getKind() != Struct.Int &&
			condFact.getExpr().obj.getType().getKind() != Struct.Char){
			if(condFact.getExpr().obj.getType().getKind() == Struct.Class
                || condFact.getExpr().obj.getType().getKind() == Struct.Array){
                if(!relopEq)
                    report_error("Nije moguce uporediti tipove", condFact);
            }
		}
		condFact.obj = new Obj(Obj.Var, "", new Struct(Struct.Bool));
		relopEq = false;
	}

	public void visit(RelopEq relop){
		relopEq = true;
	}

	public void visit(RelopNeq relop){
		relopEq = true;
	}

	public void visit(Terms1 terms){
		terms.obj = terms.getTerm().obj;
	}

	public void visit(Terms2 terms){
		if(terms.getTerms().obj.getType().getKind() != Struct.Int
			|| terms.getTerm().obj.getType().getKind() != Struct.Int){
			report_error("Nije moguce primeniti operator na objekat koji nije int", terms);
		}
		terms.obj = new Obj(Obj.Var, "nekiint", Tab.intType);
	}

	public void visit(Term1 term){
		term.obj = term.getFactor().obj;
	}

	public void visit(Term2 term){
		if(term.getTerm().obj.getType().getKind() != Struct.Int
				|| term.getFactor().obj.getType().getKind() != Struct.Int){
			report_error("Nije moguce primeniti operator na objekat koji nije int", term);
		}
		term.obj = new Obj(Obj.Var, "nekiint", Tab.intType);
	}

	public void visit(FactorDesignator factor){
		factor.obj = factor.getDesignator().obj;
	}

	public void visit(FactorDesignatorNo factor){
		if(factor.getDesignator().obj == null) {
			factor.obj = Tab.noObj;
			return;
		}
		if(factor.getDesignator().obj.getName().equals("super")){
            String methName = currentMethodObj.getName();
            Obj methodObj = factor.getDesignator().obj.getType().getMembersTable().searchKey(methName);
            if(methodObj == null || methodObj == Tab.noObj){
                report_error("Ova funkcija nije redefinisana", factor);
                return;
            }
            factor.obj = new Obj(Obj.Var, "nekioutput", methodObj.getType());
            return;
		}
		if(factor.getDesignator().obj.getKind() != Obj.Meth){
			report_error("Izraz ne predstavlja funkciju", factor);
			factor.obj = new Obj(Obj.Var, "nekiint", Tab.noType);
			return;
		}
		report_info("Poziv funkcije "+factor.getDesignator().getDesignatorIdent().obj.getName(), factor);
		factor.obj = new Obj(Obj.Var, "nekioutput", factor.getDesignator().obj.getType());
	}

	public void visit(FactorDesignatorActPars factor){
        if(factor.getDesignator().obj.getName().equals("super")){
            String methName = currentMethodObj.getName();
            Obj methodObj = factor.getDesignator().obj.getType().getMembersTable().searchKey(methName);
            if(methodObj == null || methodObj == Tab.noObj){
                report_error("Ova funkcija nije redefinisana", factor);
                return;
            }
            factor.getDesignator().obj = methodObj;
        }
		if(factor.getDesignator().obj.getKind() != Obj.Meth){
			report_error("Objekat nije funkcija", factor);
			factor.obj = new Obj(Obj.Var, "nekiint", Tab.intType);
			return;
		}
		Collection<Obj> members = factor.getDesignator().obj.getLocalSymbols();
		Iterator<Obj> iterator = members.iterator();
		if(members.size()<paramsQueue.size()){
			report_error("Broj parametara ne odgovara pozvanoj funkciji", factor);
			return;
		}
		int cnt = 1;
		while (!paramsQueue.isEmpty()){
			Obj currentPar = iterator.next();
			Obj currentArg = paramsQueue.poll();
			if(currentPar.getFpPos() != cnt++){
				report_error("Broj parametara ne odgovara pozvanoj funkciji", factor);
				return;
			}
			if(!assignable(currentPar.getType(), currentArg.getType())){
				report_error("Parametri nisu kompatibilni sa funkcijom", factor);
				return;
			}
		}
		if(iterator.hasNext())
			if(iterator.next().getFpPos() == cnt){
				report_error("Broj parametara ne odgovara pozvanoj funkciji", factor);
				return;
			}
		report_info("Poziv funkcije "+factor.getDesignator().getDesignatorIdent().obj.getName(), factor);
		factor.obj = new Obj(Obj.Var, "nekioutput", factor.getDesignator().obj.getType());
	}

	public void visit(FactorBoolConst factor){
		factor.obj = new Obj(Obj.Con, "", new Struct(Struct.Bool));
	}

	public void visit(FactorNumConst factor){
		factor.obj = new Obj(Obj.Con, "", new Struct(Struct.Int));
	}

	public void visit(FactorCharConst factor){
		factor.obj = new Obj(Obj.Con, "", new Struct(Struct.Char));
	}

	public void visit(FactorNew factor){
		if(factor.getType().struct.getKind() != Struct.Class)
			report_error("Nije moguce instancirati objekat koji nije klasa", factor);
		factor.obj = new Obj(Obj.Var, "", factor.getType().struct);
		report_info("Instanciran objekat "+objToStr(factor.obj), factor);
	}

	public void visit(FactorNewAr factor){
		factor.obj = new Obj(Obj.Var, "", new Struct(Struct.Array, factor.getType().struct));
		report_info("Instanciran niz "+objToStr(factor.obj), factor);
	}

	public void visit(FactorExpr factor){
		factor.obj = factor.getExpr().obj;
	}
	/* Ostalo */

    /* Errors */

    public void visit(ConstDeclError er)
    { report_error("Greska pri definisanju konstante", er); }

    public void visit(ConstAssError er)
    { report_error("Greska pri definisanju konstante ", er);}

    public void visit(VarDeclError er)
    { report_error("Greska pri definisanju promenljive ", er);}

    public void visit(VarAssError er)
    { report_error("Greska pri definisanju promenljive", er);}

    public void visit(ExtendsHelperError er)
    { report_error("Greska pri deklarisanju nadklase", er);}

    public void visit(FormParsError1 er)
    { report_error("Greska pri navodjenju parametara", er);}

    public void visit(FormParsError2 er)
    { report_error("Greska pri navodjenju parametara", er);}

    public void visit(ConditionError er)
    { report_error("Greska u logickom izrazu", er);}

    public void visit(ExprError2 er)
    { report_error("Greska pri dodeli vrednosti", er);}

    
    /*public void visit(MethodTypeName methodTypeName){
    	currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getType().struct);
    	methodTypeName.obj = currentMethod;
    	Tab.openScope();
		report_info("Obradjuje se funkcija " + methodTypeName.getMethName(), methodTypeName);
    }
    
    public void visit(MethodDecl methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){
			report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethod.getName() + " nema return iskaz!", null);
    	}
    	Tab.chainLocalSymbols(currentMethod);
    	Tab.closeScope();
    	
    	returnFound = false;
    	currentMethod = null;
    }
    
    public void visit(Designator designator){
    	Obj obj = Tab.find(designator.getName());
    	if(obj == Tab.noObj){
			report_error("Greska na liniji " + designator.getLine()+ " : ime "+designator.getName()+" nije deklarisano! ", null);
    	}
    	designator.obj = obj;
    }
    
    
    public void visit(FuncCall funcCall){
    	Obj func = funcCall.getDesignator().obj;
    	if(Obj.Meth == func.getKind()){
			report_info("Pronadjen poziv funkcije " + func.getName() + " na liniji " + funcCall.getLine(), null);
			funcCall.struct = func.getType();
    	}else{
			report_error("Greska na liniji " + funcCall.getLine()+" : ime " + func.getName() + " nije funkcija!", null);
			funcCall.struct = Tab.noType;
    	}
    }
    
    public void visit(Term term){
    	term.struct = term.getFactor().struct;
    }
    
    public void visit(TermExpr termExpr){
    	termExpr.struct = termExpr.getTerm().struct;
    }
    
    public void visit(AddExpr addExpr){
    	Struct te = addExpr.getExpr().struct;
    	Struct t = addExpr.getTerm().struct;
    	if(te.equals(t) && te == Tab.intType){
    		addExpr.struct = te;
    	}else{
			report_error("Greska na liniji "+ addExpr.getLine()+" : nekompatibilni tipovi u izrazu za sabiranje.", null);
			addExpr.struct = Tab.noType;
    	}
    }
    
    public void visit(Const cnst){
    	cnst.struct = Tab.intType;
    }
    
    public void visit(Var var){
    	var.struct = var.getDesignator().obj.getType();
    }
    
    public void visit(ReturnExpr returnExpr){
    	returnFound = true;
    	Struct currMethType = currentMethod.getType();
    	if(!currMethType.compatibleWith(returnExpr.getExpr().struct)){
			report_error("Greska na liniji " + returnExpr.getLine() + " : " + "tip izraza u return naredbi ne slaze se sa tipom povratne vrednosti funkcije " + currentMethod.getName(), null);
    	}
    }
    
    public void visit(Assignment assignment){
    	if(!assignment.getExpr().struct.assignableTo(assignment.getDesignator().obj.getType()))
    		report_error("Greska na liniji " + assignment.getLine() + " : " + "nekompatibilni tipovi u dodeli vrednosti! ", null);
    }
    */
    
    public boolean passed(){
    	return !errorDetected;
    }
    
}
