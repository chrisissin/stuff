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

import java.util.HashMap;
import java.util.HashSet;

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

                 String translatedstr = line.substring(line.indexOf("/")+1 , line.indexOf("/", line.indexOf("/")+1) ); //for cedict
                 translatedstr = translatedstr.trim();
           

                 for (int i = 0; i < valuesplit.length; i++) {
                     this.dicMap.put(valuesplit[i], translatedstr);
                     writer.write(valuesplit[i]+" 英文\t500\t"+translatedstr+"\n");
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

    public static void main(String[] args) {
	// write your code here
        Main oldmain = new Main();
        String finalResut = oldmain.dicMap.get("addressed");//"一口氣");
        System.out.println(finalResut);
    }
}
