package dao;

import domain.EnterpriseScc;
import utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EnterpriseSccDao {
    public List<EnterpriseScc> getEnterprises(){

        List<EnterpriseScc> res = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select * from craw_enterprise_scc";
            System.out.println(sql);
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while(rs.next()){
                res.add(new EnterpriseScc(rs.getString(1), rs.getString(2)));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn, st, rs);
        }
        return res;
    }
}
