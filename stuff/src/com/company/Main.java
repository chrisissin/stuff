package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;

//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;



public class Main {
    public Main()
    {
        //this.isMappingLoaded = this.loadDics("/Users/chrisho/dev/stuff/stuff/stuff/sdict", true);
        this.isMappingLoaded = this.loadDics("/Users/chrisho/dev/stuff/stuff/stuff/cedict", true);
    }

    private HashMap<String, String> dicMap = new HashMap<String, String>();
    private boolean isMappingLoaded = false;
    private boolean isLocalRun = false;
    private String dictionaryFile;
    private String translang;
    private String thewterm;

    private boolean loadDics(String filePath, boolean localFile) {
        BufferedReader br = null;
        Writer writer = null;
        try {
            InputStream is = null;
            if (!localFile) {
                //is = FileLocalizer.openDFSFile(filePath);
            } else {
                is = new FileInputStream(filePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(
            //new FileOutputStream("new-newtrigger.csv"), "utf-8"));
            new FileOutputStream("20150608ce_space.tsv"), "utf-8"));
            while (true) {
                 String line = reader.readLine();
                 if (line == null)
                     break;

                 line = line.trim();
                 
                 /* spanish
                 
                 String[] valuesplit =  line.split("\t");
                 if(valuesplit.length==2){
                    this.dicMap.put(valuesplit[0], valuesplit[1]);
                    writer.write(valuesplit[0]+ " in french\t5000\t"+ valuesplit[1]+"\n");
                 }
                 */

                 
                 /* cedict*/
                 int firstPosi = line.indexOf("[");
                 if (firstPosi < 0)
                     continue;

                 String value = line.substring(0, firstPosi);
                 value = value.trim();

                 String[] valuesplit =  value.split(" ");

                 String dicstring = line.substring(line.indexOf("/")+1 , line.indexOf("/", line.indexOf("/")+1) ); //for cedict
                 thewterm = valuesplit[0];
                 callWebServices("https://tw.yahoo.com/_td_api/resource/dictionary;query="+thewterm);
                 //ObjectMapper mapper = new ObjectMapper();
                 
                 String translatedstr = doJson();
                 if(translatedstr != "")
                 {
                    for (int i = 0; i < valuesplit.length; i++) {
                        this.dicMap.put(valuesplit[i], translatedstr);
                        writer.write(valuesplit[i]+"英文\t5\t"+translatedstr+"\n");
                        writer.write(valuesplit[i]+"的英文\t50\t"+translatedstr+"\n");
                        writer.write(valuesplit[i]+"翻譯\t50\t"+translatedstr+"\n");
                        writer.write(valuesplit[i]+"中翻英\t50\t"+translatedstr+"\n");
                        writer.write(valuesplit[i]+"英翻中\t50\t"+translatedstr+"\n");
                        writer.write(valuesplit[i]+"字典\t50\t"+translatedstr+"\n");
                    }
                 }
                 
                 
                 
             }
             reader.close();

            return true;
        } catch (Exception e) {
            System.err.println("Unable to open file : " + filePath);
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (br != null)br.close();
                if(writer!=null)writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private String postdata;
    private final HttpClient Client = new DefaultHttpClient();
    private String Content;
    private String Error = null;
    
    public void callWebServices(String urlstr)
    {
         /************ Make Post Call To Web Server ***********/
            BufferedReader reader=null;
    
                 // Send data 
                try
                { 
                   
                   // Defined URL  where to send data
                  URL url = new URL(urlstr);
                      
                  // Send POST data request
        
                  URLConnection conn = url.openConnection(); 
                  conn.setDoOutput(true); 
                  /*OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
                  wr.write(postdata); 
                  wr.flush(); 
               */
                  // Get the server response 
                    
                  reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                  StringBuilder sb = new StringBuilder();
                  String line = null;
                 
                    // Read Server Response
                    while((line = reader.readLine()) != null)
                        {
                               // Append server response in string
                               sb.append(line);
                        }
                     
                    // Append Server Response To Content String 
                   Content = sb.toString();
                }
                catch(Exception ex)
                {
                    Error = ex.getMessage();
                }
                finally
                {
                    try
                    {
                        reader.close();
                    }
        
                    catch(Exception ex) {}
                }
             
            /*****************************************************/
    }
    
    
    
    public String doJson() throws IOException
    {
        String theterm ="";   
        try{
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(Content);
                //ECSSStreamDO test = mapper.readValue(Content, ECSSStreamDO.class);

                
                theterm = rootNode.get("Explanation_Info").get(0).get("explanation").get(0).toString();
        }catch(Exception e)
        {
            System.out.println(thewterm+ " no value");
        }
                //System.out.println(theterm);
                /*
                JSONObject jsonResponse;

                try {
                       
                     
                     jsonResponse = new JSONObject(Content);
                       
                    
                     //JSONArray jsonMainNode = jsonResponse.optJSONArray("Android");
                     
                     //theterm = jsonResponse.get("Explanation_Info").myArrayList.get(0).map.get("explanation").myArrayList.get(0);  
                     JSONArray jsonMainNode = jsonResponse.optJSONArray("Explanation_Info");
                     JSONArray jsonSecNode = jsonMainNode.optJSONArray(0);
                     
                     theterm = (String)jsonResponse.get("term");
                     
                     
                     
                     
                    
     
                     int lengthJsonArr = jsonMainNode.length();  
   
                     for(int i=0; i < lengthJsonArr; i++) 
                     {
                         JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                                                    String name       = jsonChildNode.optString("name").toString();
                         String number     = jsonChildNode.optString("number").toString();
                         String date_added = jsonChildNode.optString("date_added").toString();         
                    }

                    
                 } catch (JSONException e) {
                     //e.printStackTrace();
                     System.out.print(thewterm+"/n"+Content);
                 }
*/
        return theterm;
    
    }

    public static void main(String[] args) {
	// write your code here
        Main oldmain = new Main();
        String finalResut = oldmain.dicMap.get("addressed");//"一口氣");
        System.out.println(finalResut);
    }
}
