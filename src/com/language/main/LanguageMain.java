package com.language.main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.eclipse.jetty.util.statistic.SampleStatistic;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LanguageMain {

	public static WebClient webClient=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LanguageMain main = new LanguageMain();
		try {
			//main.getLanunguageData(1);
			main.getLanunguageData(213);

			//main.getLanunguageData(140);

			//main.getLanunguageData(213);

			//main.getLanunguageData(333);

			//main.getLanunguageData(420);

		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  LanguageMain(){
			webClient = new WebClient();
	        webClient.getOptions().setJavaScriptEnabled(false);
	        webClient.getOptions().setCssEnabled(false);
	}

	public String convert(String utfString) {
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while ((i = utfString.indexOf("\\u", pos)) != -1) {
			sb.append(utfString.substring(pos, i));
			if (i + 5 < utfString.length()) {
				pos = i + 6;
				sb.append((char) Integer.parseInt(utfString.substring(i + 2, i + 6), 16));
			}
		}

		return sb.toString();
	}

	public static String decodeUnicode(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {

			aChar = theString.charAt(x++);

			if (aChar == '\\') {

				aChar = theString.charAt(x++);

				if (aChar == 'u') {

					// Read the xxxx

					int value = 0;

					for (int i = 0; i < 4; i++) {

						aChar = theString.charAt(x++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);

				}

			} else

				outBuffer.append(aChar);

		}

		return outBuffer.toString();

	}

	public static String sendGet(String url, String param) throws Exception {

		String result = "";
		BufferedReader in = null;

		String urlNameString = url + "?" + param;
		URL realUrl = new URL(urlNameString);
		URLConnection connection = realUrl.openConnection();
		connection.setRequestProperty("accept", "*/*");
		connection.setRequestProperty("connection", "Keep-Alive");
		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		connection.connect();
		Map<String, List<String>> map = connection.getHeaderFields();
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			result += line;
		}
		try {
			if (in != null) {
				in.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return result;
	}

	public void  getLanunguageData(int pageId) throws FailingHttpStatusCodeException, MalformedURLException, IOException{

	        String url="http://language.chinadaily.com.cn/news_bilingual";
	        String tail=".html";
	        
	        if(pageId==1){
	        	url=url+tail;
	        }else{
	        	url=url+"_"+pageId+tail;
	        }
	        HtmlPage page = webClient.getPage(url);
	
	        List<HtmlDivision> items = (List<HtmlDivision>) page.getByXPath("/html/body/div[9]/div[1]/div[@class='busBox1']");
	        for(HtmlDivision e:items){
	        	HtmlAnchor ddd = (HtmlAnchor) e.getElementsByTagName("a").get(0);
	        	String aticleUrl="http://language.chinadaily.com.cn/"+ddd.getHrefAttribute();
	        	aticleUrl="http://language.chinadaily.com.cn/2012-03/01/content_14727194.htm";
	        	 HtmlPage atriclepage = webClient.getPage(aticleUrl);
	        	 
	        	 HtmlElement contentEl = (HtmlElement) atriclepage.getElementById("Content");
	        	 List<HtmlElement> checkList = contentEl.getElementsByAttribute("td","class", "3jineiwen");
	        	 if(checkList != null&&checkList.size()>0){
	        		 String el4="";
	        		 String jineiwen="";
	        		 try{
	        		 el4= contentEl.getElementsByAttribute("td", "class", "e14").get(0).asText();
	        		jineiwen = contentEl.getElementsByAttribute("td", "class", "3jineiwen").get(0).asText();
	        	 }catch(Exception e1){
	        		 e1.printStackTrace();
	        	 }
	        		List<String> el4Arry=new ArrayList(Arrays.asList(el4.split("\n")));
	        		List<String> jinneiwenArry=new ArrayList(Arrays.asList(jineiwen.split("\n")));
	        		String last=jinneiwenArry.get(jinneiwenArry.size()-1);
	        		if(last.contains("译者")||last.contains("编辑")){
	        			jinneiwenArry.remove(jinneiwenArry.size()-1);
	        		}
	        		if(el4Arry.size()==jinneiwenArry.size()){
	        			for(int i=0;i<el4Arry.size();i++){
	        				if(el4Arry.get(i).equals("查看原文")||el4Arry.get(i).equals(jinneiwenArry.get(i))&&!isEnglish(el4Arry.get(i))){
	        					el4Arry.remove(i);
	        					jinneiwenArry.remove(i);
	        				}
	        			}
	        		}else if(el4Arry.get(el4Arry.size()-1).length()<8||!isEnglish(el4Arry.get(el4Arry.size()-1))){
	        			int len=el4Arry.size()-jinneiwenArry.size();
	        			if(len>0){
	        				int fd=el4Arry.get(0).length()-jinneiwenArry.get(0).length();
	        				fd=Math.abs(fd);
	        				if(fd>70){
	        					for(int i=0;i<len;i++){
	        						el4Arry.remove(i);
	        					}
	        				}else{
	        					for(int i=0;i<len;i++){
	        						el4Arry.remove(el4Arry.size()-1-i);
	        					}
	        				}
	        			}else{
	        				int len2=-len;
	        				int fd=el4Arry.get(0).length()-jinneiwenArry.get(0).length();
	        				fd=Math.abs(fd);
	        				if(fd>70){
	        					for(int i=0;i<len2;i++){
	        						jinneiwenArry.remove(i);
	        					}
	        				}else{
	        					for(int i=0;i<len2;i++){
	        						
	        						jinneiwenArry.remove(jinneiwenArry.size()-1);
	        					}
	        				}	        			
	        				
	        			}
	        		}else{
	        			System.out.println("########################");
	        			System.out.println("########################");
	        			System.out.println("page is not correct : "+pageId);
	        			System.out.println("########"+aticleUrl);
	        			System.out.println("########################");
	        			continue;
	        		}
	        		System.out.println("#############    #####"+aticleUrl);	
	        		for(int m=0;m<el4Arry.size();m++){
        				if(el4Arry.get(m).equals(jinneiwenArry.get(m))||el4Arry.get(m).equals("查看原文")||el4Arry.get(m).length()<3){
        					el4Arry.remove(m);
        					jinneiwenArry.remove(m);
        					continue;
        				}
	        			System.out.println(el4Arry.get(m));
	        			System.out.println(jinneiwenArry.get(m));
	        			System.out.println("***********************");

	        		}
	        		
	        	 }else{
	        	 DomNodeList<HtmlElement> pElems = contentEl.getElementsByTagName("p");
	        	boolean fist=true;
	        	 for(HtmlElement e1:pElems){
	        		 String text=e1.asText();	   
	        		//System.out.println(text);
	        		 if(text.length()<2||(!text.contains(".")&&!text.contains("。"))||!text.contains("\n"))continue;
	        		 if(fist&&!isEnglish(text)){	        			 
		        		 continue;
		        	 }
	        		 fist=false;
	        		 String english=text.split("\n")[0];
	        		 String chinese=text.split("\n")[1];
	        		 System.out.println(english);
	        		 System.out.println(chinese);
	        		 System.out.println("%%%%%%%%%%%%%%%%%%%%%%");
	        	 }
	        	 }
	        	 
	        	 
	        	 
	        }
	        
	      //  System.out.println(page.asText());
	}
	public boolean isEnglish(String strings){
		 int totalLength=strings.length();
		 double c=0;
		 int notEnglish=0;
	        for (int i = 0; i < strings.length(); i++) {  
	            if (!(strings.charAt(i) >= 'A' && strings.charAt(i) <= 'Z')  
	                    && !(strings.charAt(i) >= 'a' && strings.charAt(i) <= 'z')) {  
	            	notEnglish++;
	            }  
	        }  
	        if(notEnglish==0)return true;
	        c=(double)(Math.round(notEnglish/totalLength));
	        if(c<0.4){
	        return true;
	        }else{
	        	return false;
	        }
	}
}
