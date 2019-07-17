package dao;

import domain.Project;
import domain.Project4Table;
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

public class ProjectDao {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public void insertOrUpdate(Project project){
        Connection conn = null;
        Statement st = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            Field[] fields = Project.class.getDeclaredFields();
            List<String> columns = new ArrayList<>();
            List<String> values = new ArrayList<>();
            List<String> updates = new ArrayList<>();
            for(Field f:fields){
                String name = f.getName();
                String nameUnderline = StringUtils.humpToLine(name);
                String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
                Method getMethod = Project.class.getMethod(getMethodName);
                Object obj = getMethod.invoke(project);
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
            String sql = "insert into craw_projects (" + String.join(",", columns) + ") VALUES (" +
                         String.join(",", values) + ") " + "on duplicate key update " +
                         String.join(",", updates);
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            System.out.println(st.executeUpdate(sql));
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, null);
        }
    }

    public List<Long> getIds() {
        List<Long> res = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select id from craw_projects";
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                res.add(rs.getLong(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
        return res;
    }

    public Long getLatestId() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select id from craw_projects order by id desc limit 1";
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next())
                return rs.getLong(1);
            else
                return -1L;
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public int getProjectsCount() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select count(*) from craw_projects";
            System.out.println(sql);
//            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next())
                return rs.getInt(1);
            else
                return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            JdbcUtils.release(conn, st, rs);
        }
    }

    public static void main(String[] args){
        ProjectDao dao = new ProjectDao();
        try {
            System.out.println(dao.getLatestId());
            System.out.println(dao.getProjectsCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
