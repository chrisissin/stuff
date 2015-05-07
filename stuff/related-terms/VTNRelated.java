package com.yahoo.search.gossip;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.pig.EvalFunc;
import org.apache.pig.data.BagFactory;
import org.apache.pig.data.DataBag;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.io.FileLocalizer;

import java.io.FileReader;
import java.net.URLDecoder;

/**
 * <p>
 * Example of pig usage:
 * <p>
 * <code>
 * </code>
 */

public class VTNRelated extends EvalFunc<String> {
    
    /*public VTNRelated()
    {
        this.getCacheFiles();
    }*/

    private String filename;

    public VTNRelated(String filename)
    {
        this.filename = filename;
        this.getCacheFiles();
    }


    public List<String> getCacheFiles() { 
        List<String> list = new ArrayList<String>(1); 
        //list.add(" /user/chrisho/output/trends_benzene/tw/201412020600/part-r-00000#smallfile"); 
        list.add(this.filename); 
        //list.add("/homes/chrisho/trending_wf/test_cat/relatedpart-r-00000#smallfile"); 
        return list; 
    } 

     @Override
     public String exec(Tuple input) throws IOException {
     //public DataBag exec(Tuple input) throws IOException {
        // TODO Auto-generated method stub
        //if (input == null) 
        //     return null;


        FileReader fr = new FileReader("./smallfile"); 
        BufferedReader d = new BufferedReader(fr); 

        String ult_trageturl = (String) input.get(0);;//java.net.URLDecoder.decode((String) input.get(0), "UTF-8");
        String ult_title = (String) input.get(1);//java.net.URLDecoder.decode((String) input.get(1), "UTF-8");
/*
        
         
        DataBag topq = (DataBag) input.get(0);
        DataBag related = (DataBag) input.get(1);


        BagFactory mBagFactory = BagFactory.getInstance();
        DataBag finalResut = mBagFactory.newDefaultBag();

        TupleFactory mTupleFactory = TupleFactory.getInstance();
        //for (int i = 0; i < keys.size(); i++) {
            Tuple onet = mTupleFactory.newTuple();
            onet.append(d.readLine());
            //onet.append(freq);
            //onet.append(query);

            finalResut.add(onet);
        //}
  */

        //return finalResut;    

        BagFactory mBagFactory = BagFactory.getInstance();
        DataBag finalResut = mBagFactory.newDefaultBag();

        String resline;
        String res;

        List<String> topqlist = new ArrayList<String>(1); 
        
        while ( (resline = d.readLine())!= null)
        {
            res = resline.substring(0,resline.indexOf("\t"));
            topqlist.add(res);
        }
        for(String s : topqlist){
            
            if( ult_title!=null && ult_trageturl!=null  && (ult_title.contains(s) || ult_trageturl.contains(s)) )//s.contains("馬"))//s!="")//ult_title.contains(s))//ult_title.contains(s) || ult_trageturl.contains(s)
            {
                for(String ss : topqlist){
                    if( s != ss && ( ult_title.contains(ss) || ult_trageturl.contains(ss) )) {//&& (ult_title.contains(ss) || ult_trageturl.contains(ss)
                        //return s+"\t"+ss;           
                        
                        if(s.contains("led") || ss.contains("led"))
                            return s+"\t"+ss+"\t"+ult_title+"\t"+ult_trageturl;
                    }
                }
            }
        } 
        

        return "";
        //return finalResut;


/*
         List<String> keys = new ArrayList<String>(1024);
         getKeys(keys,"",query,query);

         BagFactory mBagFactory = BagFactory.getInstance();
         DataBag finalResu = mBagFactory.newDefaultBag();
         TupleFactory mTupleFactory = TupleFactory.getInstance();
         for (int i = 0; i < keys.size(); i++) {
             Tuple onet = mTupleFactory.newTuple();
             onet.append(keys.get(i));
             onet.append(freq);
             onet.append(query);

             finalResu.add(onet);
         }

         if ((freq > 100 && this.imeType.equalsIgnoreCase("cantonese"))
             || this.imeType.equalsIgnoreCase("changjie")) {
             for (int i = 0; i < keys.size(); i++) {
                 Tuple tmpt = mTupleFactory.newTuple();
                 String tmpKey = keys.get(i);
                 tmpKey = tmpKey.replace(" ", "");
                 tmpt.append(tmpKey);
                 tmpt.append((int)(freq / 10.0));
                 tmpt.append(query);
                 finalResu.add(tmpt);
             }
         }

         return finalResu;
         */
     }
}
