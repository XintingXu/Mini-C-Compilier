package bit.xuxinting;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.SAXException;

import bit.minisys.minicc.parser.IMiniCCParser;

public class myParser implements IMiniCCParser{
	@Override
	public void run(String iFile, String oFile) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		try {
			xml2class x2c = new xml2class(iFile);
			LL1AnalysisTable ll1AnalysisTable = new LL1AnalysisTable();
			LL1Stack ll1Stack = new LL1Stack();
			LL1Control ll1Control = new LL1Control(ll1Stack, x2c, ll1AnalysisTable,oFile);
			ll1Control.run();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class LL1Stack{
	private int stack[];
	private int topPosition;
	public LL1Stack(){
		stack = new int[1000];
		stack[0] = 0;
		topPosition = 1;//栈顶指针指向空的位置
	}
	
	public boolean push(int data[]) {
		if(topPosition + data.length > stack.length){
			return false;
		}else{
			int tempPosition = topPosition;
			topPosition += data.length;
			for(int i = 0 ;tempPosition < topPosition;tempPosition++,i++){
				stack[tempPosition] = data[i];
			}
			return true;
		}
	}
	
	public int pop(){
		topPosition--;
		return stack[topPosition];
	}
	
	public int getPosition(){
		return topPosition;
	}
	
	public void showStack(){
		System.out.print("#");
		for(int i = 1 ; i < topPosition ; i ++){
			System.out.print(stack[i] + " ");
		}
	}
}

class xml2class{
	private Document document = null;
	private Element element = null;
	private SAXBuilder saxBuilder = null;
	public List<Element> list = null;
	
	public xml2class(String iFile) throws JDOMException, IOException{
		saxBuilder = new SAXBuilder();
		document = saxBuilder.build(iFile);
		element = document.getRootElement();
		element = element.getChild("tokens");
		list = element.getChildren("token");
		//showData();
	}
	
	private void showData() {
		for(int i = 0 ; i < list.size(); i++){
			System.out.println("value " + i + " = " + list.get(i).getChildText("value"));
		}
	}
}

class LL1AnalysisTable{
	private int LL1Table[][];
	private String[][] VtTable;
	private String[] VnTable;
	private int[][] MakeRules;
	
	public LL1AnalysisTable(){
		LL1Table = new int[33][18];
		VtTable = new String[18][6];
		VnTable = new String[33];
		MakeRules = new int[57][10];//产生式从1开始编号
		LL1Init1();
		LL1init2();
		LL1init3();
		LL1init4();
	}
	
	private void LL1Init1(){
		LL1Table[0][0] = 1;
		LL1Table[1][0] = 2;
		LL1Table[2][0] = 5;
		LL1Table[3][0] = 3;LL1Table[3][17] = 4;
		LL1Table[4][0] = 6;LL1Table[4][6] = 7;
		LL1Table[5][6] = 9;LL1Table[5][12] = 8;
		LL1Table[6][0] = LL1Table[6][5] = LL1Table[6][7] = LL1Table[6][9] = LL1Table[6][14] = LL1Table[6][15] = LL1Table[6][16] = 10;
		LL1Table[6][8] = LL1Table[6][11] = 11;
		LL1Table[7][0] = 13;LL1Table[7][5] = LL1Table[7][14] = LL1Table[7][16] = 12;
		LL1Table[7][7] = 14;LL1Table[7][9] = 15;LL1Table[7][15] = 16;
		LL1Table[8][0] = 56;LL1Table[8][5] = LL1Table[8][14] = LL1Table[8][16] = 18;
		LL1Table[9][0] = 41;
		LL1Table[10][7] = 45;
		LL1Table[11][9] = 50;
		LL1Table[12][15] = 17;
		LL1Table[13][5] = LL1Table[13][14] = LL1Table[13][16] = 19;
		LL1Table[14][5] = LL1Table[14][14] = LL1Table[14][16] = 20;
		LL1Table[15][5] = LL1Table[15][14] = LL1Table[15][16] = 23;
		LL1Table[16][5] = LL1Table[16][14] = LL1Table[16][16] = 26;
		LL1Table[17][5] = LL1Table[17][14] = LL1Table[17][16] = 29;
		LL1Table[18][0] = LL1Table[18][5] = LL1Table[18][6] = LL1Table[18][7] = LL1Table[18][8] = LL1Table[18][9] = LL1Table[18][13] = LL1Table[18][14] = LL1Table[18][15] = LL1Table[18][16] = 22;
		LL1Table[18][1] = 21;
		LL1Table[19][0] = LL1Table[19][1] = LL1Table[19][5] = LL1Table[19][6] = LL1Table[19][7] = LL1Table[19][8] = LL1Table[19][9] = LL1Table[19][13] = LL1Table[19][14] = LL1Table[19][15] = LL1Table[19][16] = 25;
		LL1Table[19][2] = 24;
		LL1Table[20][0] = LL1Table[20][1] = LL1Table[20][2] = LL1Table[20][5] = LL1Table[20][6] = LL1Table[20][7] = LL1Table[20][8] = LL1Table[20][9] = LL1Table[20][13]= LL1Table[20][14] = LL1Table[20][15] = LL1Table[20][16] = 28;
		LL1Table[20][3] = 27;
		LL1Table[21][0] = LL1Table[21][1] = LL1Table[21][2] = LL1Table[21][3] = LL1Table[21][5] = LL1Table[21][6] = LL1Table[21][7] = LL1Table[21][8] = LL1Table[21][9] = LL1Table[21][13] = LL1Table[21][14] = LL1Table[21][15] = LL1Table[21][16] = 31;
		LL1Table[21][4] = 30;
		LL1Table[22][5] = 34;LL1Table[22][14] = 32;LL1Table[22][16] = 33;
		LL1Table[23][1] = 36;LL1Table[23][2] = 36;LL1Table[23][3] = 36;LL1Table[23][4] = 36;LL1Table[23][5] = 35;LL1Table[23][6] = 36;LL1Table[23][13] = 36;LL1Table[23][14] = 36;LL1Table[23][16] = 36;
		LL1Table[24][5] = LL1Table[24][14] = LL1Table[24][16] = 37;
		LL1Table[24][6] = 38;
		LL1Table[25][6] = 40;LL1Table[25][12] = 39;
		LL1Table[26][0] = 42;
		LL1Table[27][12] = 43;LL1Table[27][13] = 44;
		LL1Table[28][0] = LL1Table[28][5] = LL1Table[28][7] = LL1Table[28][9] = LL1Table[28][14] = LL1Table[28][15] = LL1Table[28][16] = 46;
		LL1Table[28][10] = 57;
		LL1Table[29][10] = 47;
		LL1Table[30][0] = LL1Table[30][5] = LL1Table[30][7] = LL1Table[30][9] = LL1Table[30][14] = LL1Table[30][16] = 49;
		LL1Table[30][8] = 48;
		LL1Table[31][5] = LL1Table[31][14] = LL1Table[31][16] = 51;    LL1Table[31][10] = 52;
		LL1Table[32][11] = 54;
		LL1Table[32][0] = LL1Table[32][5] = LL1Table[32][7] = LL1Table[32][9] = LL1Table[32][14] = LL1Table[32][15] = LL1Table[32][16] = 53;
	}
	
	private void LL1init2(){
		VtTable[0][0] = "int";VtTable[0][1] = "float";VtTable[0][2] = "bool";VtTable[0][3] = "char";VtTable[0][4] = "double";
		VtTable[1][0] = "=";
		VtTable[2][0] = ">";VtTable[2][1] = "==";VtTable[2][2] = "<";VtTable[2][3] = ">=";VtTable[2][4] = "<=";VtTable[2][5] = "!=";
		VtTable[3][0] = "+";VtTable[3][1] = "-";
		VtTable[4][0] = "*";VtTable[4][1] = "/";
		VtTable[5][0] = "(";
		VtTable[6][0] = ")";
		VtTable[7][0] = "if";
		VtTable[8][0] = "else";
		VtTable[9][0] = "while";
		VtTable[10][0] = "{";
		VtTable[11][0] = "}";
		VtTable[12][0] = ",";
		VtTable[13][0] = ";";
		VtTable[14][0] = "identifier";
		VtTable[15][0] = "return";
		VtTable[16][0] = "const";
		VtTable[17][0] = "#";
	}
	
	private void LL1init3(){
		VnTable[0] = "PROGRAM";
		VnTable[1] = "FUNCTIONS";
		VnTable[2] = "FUNCTION";
		VnTable[3] = "FLIST";
		VnTable[4] = "ARGS";
		VnTable[5] = "ALIST";
		VnTable[6] = "STMTS";
		VnTable[7] = "STMT";
		VnTable[8] = "EXPR_STMT";
		VnTable[9] = "DEF_STMT";
		VnTable[10] = "IF_STMT";
		VnTable[11] = "WHILE_STMT";
		VnTable[12] = "RET_STMT";
		VnTable[13] = "EXPR";
		VnTable[14] = "ETLIST1";
		VnTable[15] = "ETLIST2";
		VnTable[16] = "ETLIST3";
		VnTable[17] = "ETLIST4";
		VnTable[18] = "ETLIST1_C";
		VnTable[19] = "ETLIST2_C";
		VnTable[20] = "ETLIST3_C";
		VnTable[21] = "ETLIST4_C";
		VnTable[22] = "TERM";
		VnTable[23] = "FARGS";
		VnTable[24] = "ARGS_REF";
		VnTable[25] = "ARGS_NREF";
		VnTable[26] = "DEF";
		VnTable[27] = "DTLIST";
		VnTable[28] = "CODE_BLOCK";
		VnTable[29] = "FUNC_BODY";
		VnTable[30] = "ELSE_STMT";
		VnTable[31] = "WHILE_BODY";
		VnTable[32] = "WHILE_BLOCK";
	}
	
	private void LL1init4(){
		MakeRules[0][0] = 200;MakeRules[0][1] = 201;
		MakeRules[1][0] = 201;MakeRules[1][1] = 202;MakeRules[1][2] = 203;
		MakeRules[2][0] = 203;MakeRules[2][1] = 202;MakeRules[2][2] = 203;
		MakeRules[3][0] = 203;
		MakeRules[4][0] = 202;MakeRules[4][1] = 100;MakeRules[4][2] = 114;MakeRules[4][3] = 105;MakeRules[4][4] = 204;MakeRules[4][5] = 106;MakeRules[4][6] = 229;
		MakeRules[5][0] = 204;MakeRules[5][1] = 100;MakeRules[5][2] = 114;MakeRules[5][3] = 205;
		MakeRules[6][0] = 204;
		MakeRules[7][0] = 205;MakeRules[7][1] = 112;MakeRules[7][2] = 100;MakeRules[7][3] = 114;MakeRules[7][4] = 205;
		MakeRules[8][0] = 205;
		MakeRules[9][0] = 206;MakeRules[9][1] = 207;MakeRules[9][2] = 206;
		MakeRules[10][0] = 206;
		MakeRules[11][0] = 207;MakeRules[11][1] = 208;
		MakeRules[12][0] = 207;MakeRules[12][1] = 209;
		MakeRules[13][0] = 207;MakeRules[13][1] = 210;
		MakeRules[14][0] = 207;MakeRules[14][1] = 211;
		MakeRules[15][0] = 207;MakeRules[15][1] = 212;
		MakeRules[16][0] = 212;MakeRules[16][1] = 115;MakeRules[16][2] = 208;
		MakeRules[17][0] = 208;MakeRules[17][1] = 213;MakeRules[17][2] = 113;
		MakeRules[18][0] = 213;MakeRules[18][1] = 214;
		MakeRules[19][0] = 214;MakeRules[19][1] = 215;MakeRules[19][2] = 218;
		MakeRules[20][0] = 218;MakeRules[20][1] = 101;MakeRules[20][2] = 215;MakeRules[20][3] = 218;
		MakeRules[21][0] = 218;
		MakeRules[22][0] = 215;MakeRules[22][1] = 216;MakeRules[22][2] = 219;
		MakeRules[23][0] = 219;MakeRules[23][1] = 102;MakeRules[23][2] = 216;MakeRules[23][3] = 219;
		MakeRules[24][0] = 219;
		MakeRules[25][0] = 216;MakeRules[25][1] = 217;MakeRules[25][2] = 220;
		MakeRules[26][0] = 220;MakeRules[26][1] = 103;MakeRules[26][2] = 217;MakeRules[26][3] = 220;
		MakeRules[27][0] = 220;
		MakeRules[28][0] = 217;MakeRules[28][1] = 222;MakeRules[28][2] = 221;
		MakeRules[29][0] = 221;MakeRules[29][1] = 104;MakeRules[29][2] = 222;MakeRules[29][3] = 217;
		MakeRules[30][0] = 221;
		MakeRules[31][0] = 222;MakeRules[31][1] = 114;MakeRules[31][2] = 223;
		MakeRules[32][0] = 222;MakeRules[32][1] = 116;
		MakeRules[33][0] = 222;MakeRules[33][1] = 105;MakeRules[33][2] = 213;MakeRules[33][3] = 106;
		MakeRules[34][0] = 223;MakeRules[34][1] = 105;MakeRules[34][2] = 224;MakeRules[34][3] = 106;
		MakeRules[35][0] = 223;
		MakeRules[36][0] = 224;MakeRules[36][1] = 213;MakeRules[36][2] = 225;
		MakeRules[37][0] = 224;
		MakeRules[38][0] = 225;MakeRules[38][1] = 112;MakeRules[38][2] = 213;MakeRules[38][3] = 225;
		MakeRules[39][0] = 225;
		MakeRules[40][0] = 209;MakeRules[40][1] = 226;MakeRules[40][2] = 113;
		MakeRules[41][0] = 226;MakeRules[41][1] = 100;MakeRules[41][2] = 114;MakeRules[41][3] = 227;
		MakeRules[42][0] = 227;MakeRules[42][1] = 112;MakeRules[42][2] = 114;MakeRules[42][3] = 227;
		MakeRules[43][0] = 227;
		MakeRules[44][0] = 210;MakeRules[44][1] = 107;MakeRules[44][2] = 105;MakeRules[44][3] = 213;MakeRules[44][4] = 106;MakeRules[44][5] = 228;MakeRules[44][6] = 230;
		MakeRules[45][0] = 228;MakeRules[45][1] = 207;
		MakeRules[46][0] = 229;MakeRules[46][1] = 110;MakeRules[46][2] = 206;MakeRules[46][3] = 111;
		MakeRules[47][0] = 230;MakeRules[47][1] = 108;MakeRules[47][2] = 228;
		MakeRules[48][0] = 230;
		MakeRules[49][0] = 211;MakeRules[49][1] = 109;MakeRules[49][2] = 105;MakeRules[49][3] = 213;MakeRules[49][4] = 106;MakeRules[49][5] = 231;
		MakeRules[50][0] = 231;MakeRules[50][1] = 208;
		MakeRules[51][0] = 231;MakeRules[51][1] = 110;MakeRules[51][2] = 232;MakeRules[51][3] = 111;
		MakeRules[52][0] = 232;MakeRules[52][1] = 206;
		MakeRules[53][0] = 232;
		MakeRules[54][0] = 203;
		MakeRules[55][0] = 208;
		MakeRules[56][0] = 228;MakeRules[56][1] = 229;
	}
	
	public int judgeVT(Element element){
		if(element.getChildText("type").equals("identifier")){
			return 114;
		}
		else if(element.getChildText("type").equals("operator")){
			int type = 0;
			for(int i = 0; i < VtTable[3].length ; i++){
				if(element.getChildText("value").equals(VtTable[3][i])){
					type = 103;
					break;
				}
			}
			if(type == 0){
				for(int i = 0; i < VtTable[4].length ; i++){
					if(element.getChildText("value").equals(VtTable[4][i])){
						type = 104;
						break;
					}
				}
				if(type ==0 ){
					if(element.getChildText("value").equals("="))
						type =  101;
					else if(element.getChildText("value").equals(","))
						type = 112;
					else{
						for(int i = 0; i < VtTable[2].length ; i++){
							if(element.getChildText("value").equals(VtTable[2][i])){
								type = 102;
								break;
							}
						}
					}
				}
			}
			return type;
		}
		else if(element.getChildText("type").equals("keyword")){
			if(element.getChildText("value").equals("if")){
				return 107;
			}
			else if(element.getChildText("value").equals("while")){
				return 109;
			}
			else if(element.getChildText("value").equals("return")){
				return 115;
			}
			else if(element.getChildText("value").equals("else")){
				return 108;
			}
			else{
				int type = 0;
				for(int i = 0 ; i < VtTable[0].length ; i++){
					if(element.getChildText("value").equals(VtTable[0][i])){
						type = 100;
						break;
					}
				}
				return type;
			}
		}
		else if(element.getChildText("type").equals("separator")){
			if(element.getChildText("value").equals("(")){
				return 105;
			}
			if(element.getChildText("value").equals(")")){
				return 106;
			}
			if(element.getChildText("value").equals("{")){
				return 110;
			}
			if(element.getChildText("value").equals("}")){
				return 111;
			}
			if(element.getChildText("value").equals(";")){
				return 113;
			}
		}
		else if(element.getChildText("type").equals("const")){
			return 116;
		}
		else{
			if(element.getChildText("type").equals("#"))
				return 117;
			else
				return 0;
		}
		return 0;
	}
	
	public String getVN(int type){
		return VnTable[type];
	}
	
	public int[] getRightMakeRule(int num){
		int [] ReturnData = new int[getMakeRileLength(num) - 1];
		int end = getMakeRileLength(num) - 1;
		for(int i = 0 ; end >=1 ; end --, i++){
			ReturnData[i] = MakeRules[num][end];
		}
		return ReturnData;
	}
	
	public int[] getLeftMakeRule(int num){
		int [] ReturnData = new int[getMakeRileLength(num) - 1];
		int end = getMakeRileLength(num) - 1;
		for(int i = 0, j = 1 ; j <= end ; j++, i++){
			ReturnData[i] = MakeRules[num][j];
		}
		return ReturnData;
	}
	
	public int getMakeRileLength(int num){
		int i;
		for(i = MakeRules[num].length -1 ; MakeRules[num][i] == 0; i --)
			;
		return i + 1;
	}
	
	public int getRule(int Vn, int Vt){
		if(LL1Table[Vn - 200][Vt - 100] > 0 && LL1Table[Vn - 200][Vt - 100] <= MakeRules.length){
			return LL1Table[Vn - 200][Vt - 100];
		}else{
			return 0;
		}
	}
}

class LL1Control{
	public Element rootElement = null;
	private Document documentXML = null;
	private Element currentElement = null;
	private int stackCount = 0;
	private LL1Stack ll1Stack = null;
	private xml2class elementsource = null;
	private LL1AnalysisTable ll1table = null;
	private String oFile;
	
	public LL1Control(LL1Stack stack,xml2class xmlsource,LL1AnalysisTable table,String ofile){
		oFile = ofile;
		ll1Stack = stack;
		elementsource = xmlsource;
		int []temp = new int [1];
		temp[0] = 200;
		ll1Stack.push(temp);
		stackCount = ll1Stack.getPosition();
		ll1table = table;
		currentElement = new Element(ll1table.getVN(0));
		rootElement = new Element("ParserTree");
		rootElement.setAttribute("name", "test.tree.xml");
		rootElement.addContent(currentElement);
		currentElement = rootElement;
		documentXML = new Document();
		documentXML.addContent(rootElement);
	}
	
	public void run(){
		int regStack[] = new int [1000];
		int regTop = 1;
		regStack[0] = 0;
		for(int i = 0 ; i < elementsource.list.size() ;){
			int stackTop = ll1Stack.pop();
			int temp[] = new int[1];
			temp[0] = stackTop;
			ll1Stack.push(temp);
			int elementNumber = ll1table.judgeVT(elementsource.list.get(i));
			if(elementNumber == 117 && ll1Stack.getPosition() == 1)
				break;
			if(stackTop == elementNumber){
				ll1Stack.pop();
				ll1Stack.showStack();
				System.out.println("    " + elementNumber + "……#");
				Element tempElement = new Element(elementsource.list.get(i).getChildText("type"));
				tempElement.setText(elementsource.list.get(i).getChildText("value"));
				currentElement.addContent(tempElement);
				i++;
			}
			else{
				int makeRuleNum = ll1table.getRule(stackTop, elementNumber);
				if(makeRuleNum != 0){
					int current = ll1Stack.pop();
					regStack[regTop] = ll1Stack.getPosition();
					regTop++;
					ll1Stack.push(ll1table.getRightMakeRule(makeRuleNum - 1));
					ll1Stack.showStack();
					System.out.println("    " + elementNumber + "……#");
					Element tempElement = new Element(ll1table.getVN(current - 200));
					currentElement.addContent(tempElement);
					currentElement = currentElement.getChild(ll1table.getVN(current - 200));
				}
				else{
					System.out.println("Error!");
					System.out.println(elementsource.list.get(i).getChildText("value"));
					i++;
				}
			}
			if(regStack[regTop - 1] == ll1Stack.getPosition()){
				currentElement = currentElement.getParentElement();
				regTop--;
			}
		}
		stackCount = ll1Stack.getPosition();
		if(stackCount == 1){
			System.out.println("LL1 analysis done");
		}
		saveXML();
	}
	
	private void saveXML(){
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("	");
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		try {
			xmlOutputter.output(documentXML, new FileOutputStream(oFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}