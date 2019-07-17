package dao;

import domain.ProjectFingerPrint;
import domain.ProjectProvider;
import utils.JdbcUtils;
import utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectProviderDao {

    public void insertOrUpdate(ProjectProvider p){
        Connection conn = null;
        Statement st = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            Field[] fields = ProjectProvider.class.getDeclaredFields();
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            List<String> updates = new ArrayList<>();
            for(Field f:fields){
                String name = f.getName();
                String nameUnderline = StringUtils.humpToLine(name);
                String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method getMethod = ProjectProvider.class.getMethod(getMethodName);
                Object obj = getMethod.invoke(p);
                if(obj != null){
                    columns.add(nameUnderline);
                    if(obj instanceof String)
                        values.add("'" + obj + "'");
                    else if(obj instanceof Date)
                        values.add("'" +df.format(obj)+ "'");
                    else
                        values.add(obj.toString());
                    if(!"id".equals(nameUnderline))
                        updates.add(nameUnderline + " = VALUES(" + nameUnderline + ")");
                }
            }
            String sql = "insert into craw_project_provider (" + String.join(",", columns) + ") VALUES (" +
                    String.join(",", values) + ") " + "on duplicate key update " +
                    String.join(",", updates);
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            st.executeUpdate(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, null);
        }
    }
}
