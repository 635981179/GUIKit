package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = null;
    private static String username = null;
    private static String password = null;

    static{
        InputStream in = null;
        try{
            in = new FileInputStream("set.ini");
            Properties prop = new Properties();
            prop.load(in);
            System.out.println(prop);
            System.out.println(prop);
            url = "jdbc:mysql://"+prop.getProperty("url")+":"+prop.getProperty("port")+"/wjsmart?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
            System.out.println(url);
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            Class.forName(driver);
            DriverManager.setLoginTimeout(3);
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reload(){
        InputStream in = null;
        try{
            in = new FileInputStream("set.ini");
            Properties prop = new Properties();
            prop.load(in);
            System.out.println(prop);
            url = "jdbc:mysql://"+prop.getProperty("url")+":"+prop.getProperty("port")+"/wjsmart?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
            System.out.println(url);
            username = prop.getProperty("username");
            password = prop.getProperty("password");
        }catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws SQLException{

        return DriverManager.getConnection(url, username,password);
    }

    public static void release(Connection conn,Statement st,ResultSet rs){

        if(rs!=null){
            try{
                rs.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
            rs = null;

        }
        if(st!=null){
            try{
                st.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(conn!=null){
            try{
                conn.close();
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}