package bit.xuxinting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import bit.minisys.minicc.scanner.IMiniCCScanner;


public class myScanner implements IMiniCCScanner {

	private Document documemtXML = null;
	private Element rootElement = null;
	private long elementNumber = 1;
	private File ifile = null;
	private File ofile = null;
	private FileInputStream fileInputStream = null;
	private BufferedReader reader = null;
	private String strLine = null;
	private long LineNumber = 0;

	@Override
	public void run(String iFile, String oFile) throws IOException {
		// TODO Auto-generated method stub
		this.ifile = new File(iFile);
		this.ofile = new File(oFile);
		init();
		setFile();

		while ((strLine = reader.readLine()) != null) {
			LineNumber++;
			ElementNode elementNode = new ElementNode(strLine, elementNumber, rootElement, LineNumber);
			elementNumber = elementNode.getElementNumber();
		}

		addEndElement();

		saveFile();
		System.out.println("2. Scanner finished!");
	}

	private boolean init() {
		documemtXML = new Document();
		Element element = new Element("project");
		Attribute attribute = new Attribute("name", "test.l");
		element.setAttribute(attribute);
		rootElement = new Element("tokens");
		documemtXML.addContent(element);
		element.addContent(rootElement);
		if (documemtXML.hasRootElement())
			return true;
		else
			return false;
	}

	private void addEndElement() {
		Element endElement = new Element("token");
		Element endNode = new Element("number");
		endNode.setText(String.valueOf(elementNumber));
		endElement.addContent(endNode);
		endNode = new Element("value");
		endNode.setText("#");
		endElement.addContent(endNode);
		endNode = new Element("type");
		endNode.setText("#");
		endElement.addContent(endNode);
		endNode = new Element("line");
		endNode.setText(String.valueOf(LineNumber + 1));
		endElement.addContent(endNode);
		endNode = new Element("valid");
		endNode.setText("true");
		endElement.addContent(endNode);
		rootElement.addContent(endElement);
	}

	private boolean setFile() {
		try {
			fileInputStream = new FileInputStream(ifile);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			reader = new BufferedReader(inputStreamReader);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	private boolean saveFile() {
		Format format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("    ");

		XMLOutputter xmlOutputter = new XMLOutputter(format);
		try {
			xmlOutputter.output(documemtXML, new FileOutputStream(ofile));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}

class ElementNode {
	private String sourceString = null;
	private Element rootElement = null;
	private long nodeNumber = 0;
	private long lineNumber = 0;
	private int position = 0;

	public ElementNode(String source, long number, Element root, long lineNumber) {
		// TODO Auto-generated constructor stub
		this.sourceString = source;
		this.nodeNumber = number;
		this.rootElement = root;
		this.lineNumber = lineNumber;
		makeElement();
	}

	public long getElementNumber() {
		return nodeNumber;
	}

	private void makeElement() {
		while (position < sourceString.length()) {
			char checkChar = sourceString.charAt(position);
			if (isAlpha(checkChar)) {// ����ĸ
				dealAlpha();
			} else {
				if (isOperator(String.valueOf(checkChar))) {// ����һ�������
					dealOperator();
				} else {
					if (isSeparator(checkChar)) {// ����һ���ָ���
						dealSeparator(checkChar);
					} else {
						if (checkChar == '#') {// �ǡ�#����
							dealPound();
						} else {
							if (isNumber(checkChar)) {// ������
								dealNumber();
							} else {
								if (isQuote(checkChar)) {// ������
									dealQuote();
								} else {
									if (isComma(checkChar)) {
										dealComma();
									} else {
										position++;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean isKeyWords(String value) {
		if (value.equals("break") || value.equals("case") || value.equals("char") || value.equals("const")
				|| value.equals("continue") || value.equals("default") || value.equals("do") || value.equals("double")
				|| value.equals("else") || value.equals("enum") || value.equals("extern") || value.equals("float")
				|| value.equals("for") || value.equals("goto") || value.equals("if") || value.equals("int")
				|| value.equals("long") || value.equals("return") || value.equals("short") || value.equals("signed")
				|| value.equals("sizeof") || value.equals("static") || value.equals("struct") || value.equals("switch")
				|| value.equals("typedef") || value.equals("unsigned") || value.equals("union") || value.equals("void")
				|| value.equals("while")) {
			return true;
		} else {
			return false;
		}
	}
/*
	private boolean isDataType(String value) {
		if (value.equals("char") || value.equals("double") || value.equals("float") || value.equals("int")
				|| value.equals("long") || value.equals("short")) {
			return true;
		} else {
			return false;
		}
	}
*/
	private boolean isOperator(String value) {
		if (value.equals("+") || value.equals("-") || value.equals("*") || value.equals("/") || value.equals("%")
				|| value.equals("&") || value.equals("=") || value.equals(",") || value.equals(">") || value.equals("<")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isAlpha(char argument) {
		if ((argument >= 'a' && argument <= 'z') || (argument >= 'A' && argument <= 'Z')) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isAvilableInIdenfifier(char argument) {
		if (isAlpha(argument) || isNumber(argument) || argument == '_') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isNumber(char argument) {
		if (argument >= '0' && argument <= '9') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isSeparator(char argument) {
		if (argument == '{' || argument == '}' || argument == '(' || argument == ')' || argument == ';'
				|| argument == '[' || argument == ']') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isMulitiOperator(String argumnet) {
		if (argumnet.equals("+=") || argumnet.equals("*=") || argumnet.equals("-=") || argumnet.equals("++")
				|| argumnet.equals("--") || argumnet.equals("/=") || argumnet.equals(">>") || argumnet.equals("<<")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isQuote(char argument) {
		if (argument == '"' || argument == '\'') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isComma(char argument) {
		if (argument == ',') {
			return true;
		} else {
			return false;
		}
	}

	private boolean isConst(String argument) {
		if (argument.equals("true") || argument.equals("false") || argument.equals("null")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean addElement(String value, String type, String valid) {
		Element thisNode = new Element("token");
		Element thisNodeContent = new Element("number");
		thisNodeContent.setText(String.valueOf(nodeNumber));
		thisNode.addContent(thisNodeContent);
		thisNodeContent = new Element("value");
		thisNodeContent.setText(value);
		thisNode.addContent(thisNodeContent);
		thisNodeContent = new Element("type");
		thisNodeContent.setText(type);
		thisNode.addContent(thisNodeContent);
		thisNodeContent = new Element("line");
		thisNodeContent.setText(String.valueOf(lineNumber));
		thisNode.addContent(thisNodeContent);
		thisNodeContent = new Element("valid");
		thisNodeContent.setText(valid);
		thisNode.addContent(thisNodeContent);

		rootElement.addContent(thisNode);

		nodeNumber++;

		return true;
	}

	private boolean dealAlpha() {
		int lookHeadPosition = position + 1;
		while (isAvilableInIdenfifier(sourceString.charAt(lookHeadPosition))
				&& lookHeadPosition < sourceString.length()) {
			lookHeadPosition++;
		}
		String tempCompare = String.valueOf(sourceString.substring(position, lookHeadPosition));
		if (isKeyWords(tempCompare)) {
			addElement(tempCompare, "keyword", "true");
			position = lookHeadPosition;
			return true;
		} else {
			if (isConst(tempCompare)) {
				dealConstString(tempCompare);
				position = lookHeadPosition;
				return true;
			} else {
				addElement(tempCompare, "identifier", "true");
				position = lookHeadPosition;
				return true;
			}
		}
	}

	private boolean dealOperator() {
		if ((position + 1) <= sourceString.length()) {
			String tempCompare = sourceString.substring(position, position + 1);
			if (isMulitiOperator(tempCompare)) {
				addElement(tempCompare, "operator", "true");
				position = position + 2;
				return true;
			} else {
				addElement(String.valueOf(sourceString.charAt(position)), "operator", "true");
				position += 1;
				return true;
			}
		} else {
			addElement(String.valueOf(sourceString.charAt(position)), "operator", "true");
			position += 1;
			return true;
		}
	}

	private boolean dealSeparator(char argument) {
		String tempStringSeparator = sourceString.substring(position, position + 1);
		if (isMulitiOperator(tempStringSeparator)) {
			addElement(tempStringSeparator, "operator", "true");
			position += 2;
			return true;
		} else {
			if(argument == '<' || argument == '>'){
				char tempCharCompare;
				int lookheadPosition= position + 1;
				tempCharCompare = sourceString.charAt(lookheadPosition);
				while(tempCharCompare == ' ' && lookheadPosition < sourceString.length()){
					lookheadPosition++;
					tempCharCompare = sourceString.charAt(lookheadPosition);
				}
				if (isNumber(tempCharCompare)) {// ��λ�����
					addElement(String.valueOf(argument), "operator", "true");
					position++;
					return true;
				}else{
					addElement(String.valueOf(argument), "separator", "true");
					position += 1;
					lookheadPosition = position;
					while(sourceString.charAt(lookheadPosition) != '>' && lookheadPosition < sourceString.length()){
						lookheadPosition ++;
					}
					String getConstString = sourceString.substring(position,lookheadPosition);
					dealConstString(getConstString);
					
					addElement(String.valueOf(sourceString.charAt(lookheadPosition)), "separator", "true");
					
					position = lookheadPosition + 1;
					return true;
				}
			} else {
				addElement(String.valueOf(argument), "separator", "true");
				position += 1;
				return true;
			}
		}
	}

	private boolean dealPound() {
		addElement(String.valueOf('#'), "#", "true");
		position++;
		return true;
	}

	private boolean dealNumber() {
		int lookHeadPosition = position + 1;
		while (isNumber(sourceString.charAt(lookHeadPosition)) && lookHeadPosition < sourceString.length()) {
			lookHeadPosition++;
		}
		if (sourceString.charAt(lookHeadPosition) == '.' && isNumber(sourceString.charAt(lookHeadPosition + 1))) {
			while (isNumber(sourceString.charAt(lookHeadPosition)) && lookHeadPosition < sourceString.length()) {
				lookHeadPosition++;
			}
		}

		String tempNumber = sourceString.substring(position, lookHeadPosition);

		addElement(String.valueOf(tempNumber), "const", "true");
		position = lookHeadPosition;

		return true;
	}

	private boolean dealQuote() {
		addElement(String.valueOf(sourceString.charAt(position)), "separator", "true");
		position++;
		int lookHeadPosition = position;
		while (true) {
			if (sourceString.charAt(lookHeadPosition) != sourceString.charAt(position - 1)) {
				lookHeadPosition++;
			} else {
				if (sourceString.charAt(lookHeadPosition - 1) == '\\') {
					lookHeadPosition++;
				} else {
					break;
				}
			}
			if (lookHeadPosition >= sourceString.length()) {
				break;
			}
		}

		String tempConstString = sourceString.substring(position, lookHeadPosition);
		lookHeadPosition++;

		position = lookHeadPosition;

		dealConstString(tempConstString);

		addElement(String.valueOf(sourceString.charAt(position - 1)), "separator", "true");
		
		return true;
	}

	private boolean dealConstString(String constString) {
		addElement(constString, "const", "true");
		return true;
	}

	private boolean dealComma() {
		addElement(",", "operator", "true");
		return true;
	}
}