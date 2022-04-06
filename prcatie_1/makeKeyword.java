package prcatie_1;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 3���� �ǽ� �ڵ�
 * 
 * kkma ���¼� �м��⸦ �̿��Ͽ� index.xml ������ �����ϼ���.
 * 
 * index.xml ���� ������ �Ʒ��� �����ϴ�.
 * (Ű����1):(Ű����1�� ���� �󵵼�)#(Ű����2):(Ű����2�� ���� �󵵼�)#(Ű����3):(Ű����3�� ���� �󵵼�) ... 
 * e.g., ���:13#�а���:4#�ް�:1 ...
 * 
 * input : collection.xml
 * output : index.xml 
 */

public class makeKeyword {

   private String input_file;
   private String output_file = "C:\\Users\\�̹�ȿ\\Desktop\\opensw\\SimpleIR\\prcatie_1\\src\\prcatie_1\\index.xml";
   
  
   public makeKeyword(String file) throws Exception{
      this.input_file = file;
   }

   public void convertXml() throws Exception{
       //������ xml
         DocumentBuilderFactory docFactory2 = DocumentBuilderFactory.newInstance();
         docFactory2.setIgnoringElementContentWhitespace(true);

         DocumentBuilder docBuilder2 = docFactory2.newDocumentBuilder();
         org.w3c.dom.Document doc2 = docBuilder2.newDocument();
         Element docs2 = doc2.createElement("docs");
         doc2.appendChild(docs2);

         String xmlLocation = input_file;

         File dir2 = new File(xmlLocation);
         org.w3c.dom.Document otdoc = docBuilder2.parse(dir2);
         otdoc.getDocumentElement().normalize();
         org.jsoup.nodes.Document xml = Jsoup.parse(dir2, "UTF-8");
         NodeList nList = otdoc.getElementsByTagName("doc");
         StringBuilder sb = new StringBuilder();
         for(int b = 0; b < 5; b++) {
            Node nNode = nList.item(b);
            
            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
            String to = Integer.toString(b);
            org.jsoup.nodes.Element txt = xml.getElementById(to);
            org.jsoup.nodes.Element Htitle = xml.getElementById(to).selectFirst("title");
            Element eElement = (Element) nNode;
            
             String test = eElement.getElementsByTagName("body").item(0).getTextContent();
             
             
             KeywordExtractor ke = new KeywordExtractor();
             KeywordList k1 = ke.extractKeyword(test, true);
             for(int a = 0;a<k1.size();a++) {
                Keyword kwrd = k1.get(a);
                sb.append(kwrd.getString() + ":" + kwrd.getCnt() + "#");
             }
             Element code = doc2.createElement("doc");
             docs2.appendChild(code);
             code.setAttribute("id",to);
             
             Element title = doc2.createElement("title");
             title.appendChild(doc2.createTextNode(Htitle.text()));
             code.appendChild(title);
             
             Element body = doc2.createElement("body");
             body.appendChild(doc2.createTextNode(sb.toString()));
             code.appendChild(body);
             sb.setLength(0);
             }
         }
         TransformerFactory tff2 = TransformerFactory.newInstance();
         Transformer tf2 = tff2.newTransformer();
         tf2.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
         
         DOMSource so2 = new DOMSource(doc2);
         //������
         StreamResult result2 = new StreamResult(new FileOutputStream(new File(output_file)));
         
         tf2.transform(so2, result2);
        
         
    
      System.out.println("3���� ����Ϸ�");
      }
   }