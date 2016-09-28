/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slm_backend;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author lenovo
 */
public class RSS_Feed {
    private ArrayList<String> title;
    private ArrayList<String> description;
    private ArrayList<String> pubdate;
    private String department;
    
    public void getFeed(Connection connection,String dept){
        System.out.println("--------------------------RSS updateing--------------------------");
        try{
            Document document = Jsoup.connect("http://www4.sit.kmutt.ac.th/student/bsc_"+dept+"_feed").get();
            
            department = dept.toUpperCase();
            
            title = new ArrayList<String>();
            description = new ArrayList<String>();
            pubdate = new ArrayList<String>();
            boolean isFrist = true;
            String temp;
            
            Elements element = document.getElementsByTag("title");
            Iterator iter = element.iterator();
            iter = element.iterator();
            while(iter.hasNext()){
                    temp = iter.next().toString();
                    String noHTML = Jsoup.parse(temp).text().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                    title.add(noHTML);
            }
            
            element = document.getElementsByTag("description");
            iter = element.iterator();
            while(iter.hasNext()){
                temp = iter.next().toString();
                String noHTML = Jsoup.parse(temp).text().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                description.add(noHTML);
            }
            
            pubdate.add("0000-00-00");
            element = document.getElementsByTag("pubDate");
            iter = element.iterator();
            while(iter.hasNext()){
                temp = iter.next().toString();
                String noHTML = Jsoup.parse(temp).text().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
                DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ROOT);
                Date date = df.parse(noHTML);
                df = new SimpleDateFormat("yyyy-MM-dd");
                noHTML = df.format(date);
                if(noHTML == null){
                    noHTML = "0000-00-00";
                }
                pubdate.add(noHTML);
            }
                
            for(int i=1;i<title.size();i++){
                Database_Controller db_conn = new Database_Controller();
                System.out.println("TITLE: "+title.get(i));
                db_conn.executeQuery(connection, "INSERT INTO rss_stat(title, description, department, date, count) SELECT * FROM (SELECT '"+title.get(i).replace("'", "")+"','"+description.get(i).replace("'", "")+"','"+department+"','"+pubdate.get(i)+"','0') AS tmp WHERE NOT EXISTS (SELECT * FROM rss_stat WHERE title='"+title.get(i).replace("'", "")+"' AND description='"+description.get(i).replace("'", "")+"' AND department='"+department+"' AND date='"+pubdate.get(i)+"');");
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("--------------------------RSS updated---------------------------");
    }
}
