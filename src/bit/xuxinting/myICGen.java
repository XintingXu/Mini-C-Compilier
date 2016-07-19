package bit.xuxinting;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import bit.minisys.minicc.icgen.IMiniCCICGen;

public class myICGen implements IMiniCCICGen{
	private XMLReader xmlReader = null;
	private XMLFormer xmlFormer = null;
	
	@Override
	public void run(String iFile, String oFile) throws IOException {
		// TODO Auto-generated method stub
		xmlReader = new XMLReader(iFile);
		xmlFormer = new XMLFormer(oFile);
		form();
		xmlFormer.writeXML();
	}
	
	private void form(){
		Element sourceElement = xmlReader.getRootElement();
		Element destElement = xmlFormer.getRootElement();
		int addr = 1;
		
		while(true){
			if(sourceElement.getChildren().isEmpty()){
				break;
			}
			
			if(!sourceElement.getChildren("FUNCTION").isEmpty() || !sourceElement.getChildren("FUNCTIONS").isEmpty()){
				if(!sourceElement.getChildren("FUNCTIONS").isEmpty()){
					Element tempElement = new Element("FUNCTIONS");
					destElement.addContent(tempElement);
					destElement = destElement.getChild("FUNCTIONS");
				}else{
					Element tempElement = new Element("FUNCTION");
					destElement.addContent(tempElement);
					destElement = destElement.getChild("FUNCTION");
				}
			}
			else if(!sourceElement.getChildren("WHILE_STMT").isEmpty()){
				while (sourceElement.getChildren("TERM").isEmpty()) {
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg1 = sourceElement.getChildText("identifier");
				while(sourceElement.getChildren("operator").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String opt = sourceElement.getChildText("operator");
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg2 = sourceElement.getChildText("identifier");
				
				Element tempElement = new Element("quaternion");
				tempElement.setAttribute("result", "");
				tempElement.setAttribute("arg2", arg2);
				tempElement.setAttribute("arg1", arg1);
				tempElement.setAttribute("op", opt);
				tempElement.setAttribute("addr", String.valueOf(addr));
				addr++;
				destElement.addContent(tempElement);
				
				int jend[] = new int[10];
				int jendTop = 1;
				
				tempElement = new Element("quaternion");
				tempElement.setAttribute("result", "");
				tempElement.setAttribute("arg2", "");
				tempElement.setAttribute("arg1", "");
				tempElement.setAttribute("op", "JF");
				tempElement.setAttribute("addr", String.valueOf(addr));
				jend[jendTop] = addr;
				addr++;
				
				while(sourceElement.getChildren("WHILE_BODY").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				sourceElement = sourceElement.getChild("WHILE_BODY");
				sourceElement = sourceElement.getChild("separator");
				
				while(sourceElement.getChildText("separator") != "}"){
					while(sourceElement.getChildren("separator").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					while (sourceElement.getChildren("identifier").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					arg1 = sourceElement.getChildText("identifier");
					while(sourceElement.getChildren("operator").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					opt = sourceElement.getChildText("operator");
					while (sourceElement.getChildren("identifier").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					arg2 = sourceElement.getChildText("identifier");
					
					tempElement = new Element("quaternion");
					tempElement.setAttribute("result", "");
					tempElement.setAttribute("arg2", arg2);
					tempElement.setAttribute("arg1", arg1);
					tempElement.setAttribute("op", opt);
					tempElement.setAttribute("addr", String.valueOf(addr));
					addr++;
					destElement.addContent(tempElement);
				}
			}
			else if(!sourceElement.getChildren("IF_STMT").isEmpty()){
				sourceElement = sourceElement.getChild("separator");
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg1 = sourceElement.getChildText("identifier");
				while(sourceElement.getChildren("operator").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String opt = sourceElement.getChildText("operator");
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg2 = sourceElement.getChildText("identifier");
				
				Element tempElement = new Element("quaternion");
				tempElement.setAttribute("result", "");
				tempElement.setAttribute("arg2", arg2);
				tempElement.setAttribute("arg1", arg1);
				tempElement.setAttribute("op", opt);
				tempElement.setAttribute("addr", String.valueOf(addr));
				addr++;
				destElement.addContent(tempElement);
				
				while (sourceElement.getChildren("FUNC_BODY").isEmpty()) {
					sourceElement = (Element) sourceElement.getChildren().get(0);
					while (sourceElement.getChildren("identifier").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					arg1 = sourceElement.getChildText("identifier");
					while(sourceElement.getChildren("operator").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					opt = sourceElement.getChildText("operator");
					while (sourceElement.getChildren("identifier").isEmpty()){
						sourceElement = (Element) sourceElement.getChildren().get(0);
					}
					arg2 = sourceElement.getChildText("identifier");
					
					tempElement = new Element("quaternion");
					tempElement.setAttribute("result", "");
					tempElement.setAttribute("arg2", arg2);
					tempElement.setAttribute("arg1", arg1);
					tempElement.setAttribute("op", opt);
					tempElement.setAttribute("addr", String.valueOf(addr));
					addr++;
					destElement.addContent(tempElement);
				}
			}
			else if(!sourceElement.getChildren("TERM").isEmpty()){
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg1 = sourceElement.getChildText("identifier");
				while(sourceElement.getChildren("operator").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String opt = sourceElement.getChildText("operator");
				while (sourceElement.getChildren("identifier").isEmpty()){
					sourceElement = (Element) sourceElement.getChildren().get(0);
				}
				String arg2 = sourceElement.getChildText("identifier");
				
				Element tempElement = new Element("quaternion");
				tempElement.setAttribute("result", "");
				tempElement.setAttribute("arg2", arg2);
				tempElement.setAttribute("arg1", arg1);
				tempElement.setAttribute("op", opt);
				tempElement.setAttribute("addr", String.valueOf(addr));
				addr++;
				destElement.addContent(tempElement);
			}
			else{
				sourceElement = (Element) sourceElement.getChildren().get(0);
			}
		}
	}
}

class XMLReader{
	private String iFile = null;
	private Document document = null;
	private Element rootElement = null;
	private SAXBuilder saxBuilder = null;
	
	public XMLReader(String ifile){
		iFile = ifile;
		saxBuilder = new SAXBuilder();
		try {
			document = saxBuilder.build(iFile);
			rootElement = document.getRootElement();
			rootElement = rootElement.getChild("PROGRAM");
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Element getRootElement(){
		return rootElement;
	}
}

class XMLFormer{
	private Document document = null;
	private Format format = null;
	private Element rootElement = null;
	private String oFile;
	
	public XMLFormer(String ofile){
		document = new Document();
		rootElement = new Element("IC");
		rootElement.setAttribute("name","test.ic.xml");
		document.addContent(rootElement);
		format = Format.getPrettyFormat();
		format.setEncoding("UTF-8");
		format.setIndent("	");
		oFile = ofile;
	}
	
	public void writeXML(){
		XMLOutputter xmlOutputter = new XMLOutputter(format);
		try {
			xmlOutputter.output(document, new FileOutputStream(oFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Element getRootElement(){
		return rootElement;
	}
}