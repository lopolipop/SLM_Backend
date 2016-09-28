/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slm_backend;

import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lenovo
 */
public class SLM_Backend {

    /**
     */
    public static void main(String[] args) {
        while (true) {
            try {
                RSS_Timer();
                Thread.sleep(60000); //Millisecond
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void RSS_Timer() {
        try {
            Database_Controller db_conn = new Database_Controller();

            int alarm_hour = 0;
            int alarm_minute = 1;

            Calendar cNow = Calendar.getInstance();
            int now_hour = cNow.get(Calendar.HOUR);
            int now_minute = cNow.get(Calendar.MINUTE);

            if (alarm_hour == now_hour && alarm_minute == now_minute) {
                Connection connection = db_conn.Database_Connector();
                RSS_Feed rss_feed = new RSS_Feed();
                rss_feed.getFeed(connection, "it");
                rss_feed.getFeed(connection, "cS");
                db_conn.Database_close(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
