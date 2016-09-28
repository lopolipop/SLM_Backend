/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slm_backend;

import java.io.UnsupportedEncodingException;
import static java.lang.System.exit;
import java.sql.Connection;
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
        Database_Controller db_conn = new Database_Controller();
        try {
            while (true) {
                Connection connection = db_conn.Database_Connector();
                RSS_Feed rss_feed = new RSS_Feed();
                rss_feed.getFeed(connection, "it");
                rss_feed.getFeed(connection, "cS");
                db_conn.Database_close(connection);
                Thread.sleep(5000); //Millisecond
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            exit(0);
        }
    }
}
