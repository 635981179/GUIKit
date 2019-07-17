package dao;

import domain.Project;
import domain.ProjectFingerPrint;
import utils.JdbcUtils;
import utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectFingerPrintDao {
    public void insertOrUpdate(ProjectFingerPrint projectFingerPrint){
        Connection conn = null;
        Statement st = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            Field[] fields = ProjectFingerPrint.class.getDeclaredFields();
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            List<String> updates = new ArrayList<>();
            for(Field f:fields){
                String name = f.getName();
                String nameUnderline = StringUtils.humpToLine(name);
                String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method getMethod = ProjectFingerPrint.class.getMethod(getMethodName);
                Object obj = getMethod.invoke(projectFingerPrint);
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
            String sql = "insert into craw_projects_fingerprint (" + String.join(",", columns) + ") VALUES (" +
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

    public List<ProjectFingerPrint> selectAll(){
        List<ProjectFingerPrint> res = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select * from craw_projects_fingerprint";
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                ProjectFingerPrint p = new ProjectFingerPrint();
                p.setId(rs.getLong("id"));
                p.setJsdw(rs.getString("jsdw"));
                p.setXmdz(rs.getString("xmdz"));
                p.setXmmc(rs.getString("xmmc"));
                p.setXmsjzw(rs.getString("xmsjzw"));
                p.setCreateBy(rs.getString("create_by"));
                p.setCreateTime(rs.getDate("create_time"));
                p.setType(rs.getInt("type"));
                p.setUpdateBy(rs.getString("update_by"));
                p.setUpdateTime(rs.getDate("update_time"));
                res.add(p);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, null);
        }
        return res;
    }
}
