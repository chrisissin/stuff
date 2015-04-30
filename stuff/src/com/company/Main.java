package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.HashSet;

public class Main {
    public Main()
    {
        this.isMappingLoaded = this.loadDics("/Users/chrisho/sdict", true);
    }

    private HashMap<String, String> dicMap = new HashMap<String, String>();
    private boolean isMappingLoaded = false;
    private boolean isLocalRun = false;
    private String dictionaryFile;
    private String translang;

    private boolean loadDics(String filePath, boolean localFile) {
        try {
            InputStream is = null;
            if (!localFile) {
                //is = FileLocalizer.openDFSFile(filePath);
            } else {
                is = new FileInputStream(filePath);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while (true) {
                String line = reader.readLine();
                if (line == null)
                    break;

                line = line.trim();

                String[] splited = line.split("\\t");
                this.dicMap.put(splited[0], splited[1]);
                System.out.println(line);
            }
            reader.close();
            return true;
        } catch (Exception e) {
            System.err.println("Unable to open file : " + filePath);
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
	// write your code here
        Main oldmain = new Main();
        String finalResut = oldmain.dicMap.get("abbe");
        System.out.println(finalResut);
    }
}
