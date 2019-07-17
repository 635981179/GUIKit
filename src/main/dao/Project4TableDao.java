package dao;

import domain.Project4Table;
import org.junit.Test;
import utils.JdbcUtils;

import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Project4TableDao {
    public List<Project4Table> getProjects(Date start, Date end, String name,
                                    Integer pageSize, Integer pageNum){
        List<Project4Table> res = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select a.id, a.xmmc, a.jhkgrq, b.gysmc " +
                    "from craw_projects a " +
                    "left join craw_project_provider b " +
                    "on a.id = b.id";
            List<String> conditions = new ArrayList<>();
            if(start != null)conditions.add("a.jhkgrq >= '" + df.format(start) + "'");
            if(end != null)conditions.add("a.jhkgrq <= '" + df.format(end) + "'");
            if(name != null && !name.equals(""))conditions.add("(a.xmmc like '%"+name+"%' or b.gysmc like '%" + name + "%')");
            if(conditions.size() > 0){
                String sqlExtra = " where ";
                for(int i=0;i<conditions.size();i++){
                    sqlExtra += conditions.get(i);
                    if(i != conditions.size()-1){
                        sqlExtra += " and ";
                    }
                }
                sql += sqlExtra;
            }
            if(pageNum!= null && pageSize != null){
                sql += " limit " + pageNum*pageSize + "," + pageSize;
            }
            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                res.add(new Project4Table(Long.parseLong(rs.getString(1)),
                        rs.getString(2),
                        df.parse(rs.getString(3)),
                        rs.getString(4)));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
        return res;
    }

    public int getProjectsCountOnCondition(Date start, Date end, String name){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select count(*) " +
                    "from craw_projects a " +
                    "left join craw_project_provider b " +
                    "on a.id = b.id";
            List<String> conditions = new ArrayList<>();
            if(start != null)conditions.add("a.jhkgrq >= '" + df.format(start) + "'");
            if(end != null)conditions.add("a.jhkgrq <= '" + df.format(end) + "'");
            if(name != null && !name.equals(""))conditions.add("(a.xmmc like '%"+name+"%' or b.gysmc like '%" + name + "%')");
            if(conditions.size() > 0){
                String sqlExtra = " where ";
                for(int i=0;i<conditions.size();i++){
                    sqlExtra += conditions.get(i);
                    if(i != conditions.size()-1){
                        sqlExtra += " and ";
                    }
                }
                sql += sqlExtra;
            }
            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                return rs.getInt(1);
            }
            else
                return -1;
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally{
            JdbcUtils.release(conn, st, rs);
        }

    }

    @Test
    public void a(){
        List res = getProjects(null, null, "鼎思", 10, 1);
        Iterator it = res.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }
}
