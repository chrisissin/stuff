package com.company;

        import java.io.BufferedReader;
        import java.io.*;
        import java.io.IOException;
        import java.util.*;
        import java.lang.Object;

public class rwf {
    public static void main(String[] args) {

        BufferedReader br = null;
        Writer writer = null;
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader("newbrand"));
            List<String> brands = new ArrayList<String>();

            while ((sCurrentLine = br.readLine()) != null) {
                String [] thesplted = sCurrentLine.split(",");
                brands.add(thesplted[1].toLowerCase());
            }

            br.close();
            br  = new BufferedReader(new FileReader("newtrims"));
            List<String> trimlst = new ArrayList<String>();
            while ((sCurrentLine = br.readLine()) != null) {
                trimlst.add(sCurrentLine);
            }

            br.close();
            //br  = new BufferedReader(new FileReader("newtrigger.csv"));
            br  = new BufferedReader(new FileReader("newtriggered1.csv"));
            //br  = new BufferedReader(new FileReader("testest"));
            List<String> termlst = new ArrayList<String>();

			/*

			*/
            writer = new BufferedWriter(new OutputStreamWriter(
                    //new FileOutputStream("new-newtrigger.csv"), "utf-8"));
                    new FileOutputStream("new-newtriggered1.csv"), "utf-8"));
            while ((sCurrentLine = br.readLine()) != null) {
                String [] thesplted = sCurrentLine.split(",");
                String toMa = thesplted[1];
                for(String atrim : trimlst ){
                    if(toMa.equalsIgnoreCase(atrim))
                    {
                        toMa = "";
                        //System.out.println(atrim);
                        break;
                    }
                }
                for(String abrand : brands){
                    String newterm =null;
                    toMa = toMa.toLowerCase();

                    if(toMa.indexOf(abrand)>=0
                            &&
                            toMa.length() > toMa.indexOf(abrand)+abrand.length()+1
                            &&
                            !Character.isWhitespace(toMa.charAt(toMa.indexOf(abrand)+abrand.length()))
                            )
                    {
                        System.out.println(toMa);
                        //System.out.println(abrand);
                        //System.out.println(toMa.charAt(toMa.indexOf(abrand)+abrand.length()+1));
                        newterm =  new StringBuilder(toMa).insert(toMa.indexOf(abrand)+abrand.length(), " ").toString();
                        toMa = newterm;
                        System.out.println(toMa);
                    }
                }
                termlst.add(toMa);
                if(!toMa.isEmpty())
                    writer.write("123,"+toMa+",321\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
                if(writer!=null)writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
