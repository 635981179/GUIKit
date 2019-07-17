package task;


import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.EnterpriseSccDao;
import dao.ProjectDao;
import dao.ProjectFingerPrintDao;
import dao.ProjectProviderDao;
import domain.EnterpriseScc;
import domain.Project;
import domain.ProjectFingerPrint;
import domain.ProjectProvider;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.xpath.DefaultXPath;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.internal.runners.statements.RunAfters;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateTask implements Runnable {
    private Integer idFrom;
    private Integer status;
    private String errorInfo;
    private String infoString;
    private Boolean running;
    private Integer maxSlot;
    private HttpClient httpClient = HttpClients.createDefault();
    private ProjectDao projectDao = new ProjectDao();
    private ProjectFingerPrintDao projectFingerPrintDao = new ProjectFingerPrintDao();
    private EnterpriseSccDao enterpriseSccDao = new EnterpriseSccDao();
    private ProjectProviderDao projectProviderDao = new ProjectProviderDao();

    private final String urlWithoutParams = "http://218.4.84.171:5445/AppGiantHopeSzSq/GiantHopePage/Module_ViewInfo/ProjecteInfo_View.aspx?ProjectID=";
    private final String projectPrintUrl = "http://218.4.84.171:5445/AppGiantHopeSzSq/GiantHopePage/Module_Project/Project_Other_Machine_Edit.aspx?ProjectID=";

    public Boolean isRunning(){
        return running;
    }

    private UpdateTask(){
        status = 0;
        running = false;
    }

    private static class SingletonHolder{
        private static UpdateTask instance = new UpdateTask();
    }

    public static UpdateTask getInstance(){
        return SingletonHolder.instance;
    }

    public void getProjects() throws IOException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        int startId = idFrom == null?1:idFrom;
        int maxSlot = this.maxSlot == null?100:this.maxSlot;
        int slot = 0;
        int id=startId;
        while(slot <= maxSlot){
            System.out.println(id);
            String uri = urlWithoutParams + id++;
            System.out.println(uri);
            HttpGet getProject = new HttpGet(uri);
            HttpResponse res = httpClient.execute(getProject);
            int statusCode = res.getStatusLine().getStatusCode();
            String content = EntityUtils.toString(res.getEntity(), StandardCharsets.UTF_8);
            res.getEntity().getContent().close();
            if(statusCode != 200){
                slot++;
            }
            else{
                slot=0;
                handleProjectPage(id-1, content);
            }
        }
    }

    public void handleProjectPage(int id, String content) throws ParseException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Document doc = Jsoup.parse(content);
        String xmmc = doc.getElementById("td_PrjName").html();
        String xmlb = doc.getElementById("ddl_xmlb").html();
        String tzlb = doc.getElementById("ddl_tzlb").html();
        String xmbh = doc.getElementById("dtb_zbtzsbh").html();
        String cblx = doc.getElementById("dbt_cblx").html();
        String gcdd = doc.getElementById("DBTextBox12").html();
        String gczj = doc.getElementById("DBTextBox1").html();
        String jzmj = doc.getElementById("DBTextBox6").html();
        String jsdw = doc.getElementById("DBTextBox2").html();
        String jsdwlxr = doc.getElementById("tb_jsdwlxr").html();
        String jsdwlxrdh = doc.getElementById("tb_jsdwlxrdh").html();
        String sgdw = doc.getElementById("tb_sgdw").html();
        String sgdwxmjl = doc.getElementById("DBTextBox3").html();
        String sgdwxmjldh = doc.getElementById("db_sgdwlxdh").html();
        String qyaqfgld = doc.getElementById("db_qyaqfgld").html();
        String qyaqfgldlxdh = doc.getElementById("db_sgdwxmjllxdh").html();
        String jldw = doc.getElementById("db_jldw").html();
        String xmzj = doc.getElementById("db_xmzj").html();
        String db_zjlxdh = doc.getElementById("db_zjlxdh").html();
        String ssdq = doc.getElementById("ddl_ssdq").html();
        String jhkgrq = doc.getElementById("DBText5").html();
        String jhjgrq = doc.getElementById("DBText6").html();
        String zfxm = doc.getElementById("DBText4").html();
        String wmgd = doc.getElementById("DBText1").html();
        String yzgc = doc.getElementById("DBText2").html();
        Project ins = new Project();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ins.setXmmc(xmmc);
        ins.setXmlb(xmlb);
        ins.setCblx(cblx);
        ins.setCreateBy("定时任务");
        ins.setGcdd(gcdd);
        ins.setGczj(gczj);
        ins.setJhjgrq(df.parse(jhjgrq));
        ins.setJhkgrq(df.parse(jhkgrq));
        ins.setJldw(jldw);
        ins.setJsdw(jsdw);
        ins.setJsdwlxr(jsdwlxr);
        ins.setJsdwlxrdh(jsdwlxrdh);
        ins.setJzmj(jzmj);
        ins.setQyaqfgld(qyaqfgld);
        ins.setQyaqfgldlxdh(qyaqfgldlxdh);
        ins.setSgdw(sgdw);
        ins.setSgdwxmjl(sgdwxmjl);
        ins.setSgdwxmjllxdh(sgdwxmjldh);
        ins.setSsdq(ssdq);
        ins.setTzlb(tzlb);
        ins.setUpdateBy("定时任务");
        ins.setWmgd(wmgd.equals("是")?1:0);
        ins.setXmbh(xmbh);
        ins.setXmzj(xmzj);
        ins.setYzgc(yzgc.equals("是")?1:0);
        ins.setZfxm(zfxm.equals("是")?1:0);
        ins.setZjlxdh(db_zjlxdh);
        ins.setId(Long.valueOf(id));
        System.out.println(ins);
        projectDao.insertOrUpdate(ins);
    }

    public void getProjectSjzw() throws IOException, ParseException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        List<Long> ids = projectDao.getIds();
        long startId = this.idFrom == null? 1:idFrom;
        for(long id:ids){
            if(id<startId)continue;
            System.out.println(id);
            String uri = projectPrintUrl + id;
            System.out.println(uri);
            HttpGet getProject = new HttpGet(uri);
            HttpResponse res = httpClient.execute(getProject);
            int statusCode = res.getStatusLine().getStatusCode();
            String content = EntityUtils.toString(res.getEntity(), StandardCharsets.UTF_8);
            res.getEntity().getContent().close();
            if(statusCode == 200){
                handleProjectSjzwPage((int)id, content);
            }

        }
    }

    public void handleProjectSjzwPage(int id, String content) throws ParseException{
        Document doc = Jsoup.parse(content);
        String xmmc = doc.getElementById("db_PrjName").html();
        String xmdz = doc.getElementById("dtb_PrjAdress").html();
        String jsdw = doc.getElementById("dtb_jsdw").html();
        String xmsjzw = doc.getElementById("dt_DataNumber").html();
        ProjectFingerPrint ins = new ProjectFingerPrint();
        ins.setXmmc(xmmc);
        ins.setXmdz(xmdz);
        ins.setJsdw(jsdw);
        ins.setXmsjzw(xmsjzw);
        ins.setId(Long.valueOf(id));
        System.out.println(ins);
        projectFingerPrintDao.insertOrUpdate(ins);
    }

    public void getRelationship() throws IOException, DocumentException {
        int startId = idFrom == null?1:idFrom;
        List<EnterpriseScc> providerList = enterpriseSccDao.getEnterprises();
        List<ProjectFingerPrint> fingerprints = projectFingerPrintDao.selectAll();
        Iterator<ProjectFingerPrint> it = fingerprints.iterator();
        while(it.hasNext()){
            ProjectFingerPrint nowfp = it.next();
            long id = nowfp.getId();
            if(id<startId)continue;
            System.out.println("当前ID " + id);
            String xmmc = nowfp.getXmmc();
            String sjzw = nowfp.getXmsjzw();
            if(sjzw == null)continue;
            Iterator<EnterpriseScc> itScc = providerList.iterator();
            while(itScc.hasNext()){
                EnterpriseScc nowscc = itScc.next();
                String name = nowscc.getName();
                String scc = nowscc.getScc();
                if(ifRelated(scc, sjzw)){
                    System.out.println("matched " + name + "  " + scc + " " + sjzw);
                    ProjectProvider ins = new ProjectProvider();
                    ins.setGysmc(name);
                    ins.setGysscc(scc);
                    ins.setId(id);
                    ins.setSjzw(sjzw);
                    ins.setName(xmmc);
                    projectProviderDao.insertOrUpdate(ins);
//                    CrawProjectProvider cpp = new CrawProjectProvider();
//                    cpp.setId(id);
//                    cpp.setGysmc(name);
//                    cpp.setGysscc(scc);
//                    cpp.setName(xmmc);
//                    cpp.setSjzw(sjzw);
//                    crawProjectProviderService.insertCrawProjectProvider(cpp);
                }
            }

        }
//        GBWebServiceManager gm = GBWebServiceManager.getInstance();
//        Iterator<CrawProjectsFingerprint> it = fingerprints.iterator();
//        while(it.hasNext()){
//            CrawProjectsFingerprint nowfp = it.next();
//            long id = nowfp.getId();
//            if(id <= 2167)continue;
//            System.out.println("当前 ID" + id);
//            String xmmc = nowfp.getXmmc();
//            String sjzw = nowfp.getXmsjzw();
//            if(sjzw == null)continue;
//            Iterator<CrawEnterpriseScc> itScc = providerList.iterator();
//            while(itScc.hasNext()){
//                CrawEnterpriseScc nowscc = itScc.next();
//                String name = nowscc.getEnterpriseName();
//                String scc = nowscc.getSocialCreditCode();
//                if(gm.getProjectInfos(scc,sjzw) == 0){
//                    System.out.println("matched " + name + "  " + scc + " " + sjzw);
//                    CrawProjectProvider cpp = new CrawProjectProvider();
//                    cpp.setId(id);
//                    cpp.setGysmc(name);
//                    cpp.setGysscc(scc);
//                    cpp.setName(xmmc);
//                    cpp.setSjzw(sjzw);
//                    crawProjectProviderService.insertCrawProjectProvider(cpp);
//                }
//            }
//        }
    }

    public boolean ifRelated(String scc, String sjzw) throws IOException, DocumentException {
        HttpPost post = new HttpPost("http://218.4.84.171:8060/AppWebService/GHBackBone_DataDocking.asmx");
        post.setHeader("Content-Type", "application/soap+xml; charset=utf-8");
        post.setHeader("Accept", "application/soap+xml, multipart/related");
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" ?>")
                .append("<env:Envelope xmlns:env=\"http://www.w3.org/2003/05/soap-envelope\">")
                .append("<env:Header></env:Header>")
                .append("<env:Body>")
                .append("<q0:GetProject xmlns:q0=\"http://218.4.84.171:5445/\">")
                .append("<dataNumber xmlns=\"http://218.4.84.171:5445/\">"+sjzw+"</dataNumber>")
                .append("<bagsBH xmlns=\"http://218.4.84.171:5445/\">"+scc+"</bagsBH>")
                .append("</q0:GetProject>")
                .append("</env:Body>")
                .append("</env:Envelope>");
        post.setEntity(new StringEntity(sb.toString(), "utf-8"));
        HttpResponse resp = httpClient.execute(post);
        String content = EntityUtils.toString(resp.getEntity(), "utf-8");
        Pattern p = Pattern.compile("<GetProjectResult[^>]*((>[\\s\\S]*</GetProjectResult>)|(/>))");
        Matcher m = p.matcher(content);
        if(m.find()) {
            content = m.group(0).replaceAll("<GetProjectResult[^>]*>", "").replaceAll("</GetProjectResult>", "");
        }
        if(content.length()>0)
            return true;
        else
            return false;
//        org.dom4j.Document doc = DocumentHelper.parseText(content);
//        Element root = doc.getRootElement();
//        DefaultXPath path = new DefaultXPath("//soap:Envelope");
//
//        System.out.println(path.selectNodes("//GetProjectResult"));
    }

    @Override
    public void run(){
        try {
            running = true;
            status = 1;
            setInfoString("①爬取项目信息中");
            getProjects();
            status = 2;
            setInfoString("②爬取数据指纹中");
            getProjectSjzw();
            status = 3;
            setInfoString("③寻找项目关系");
            getRelationship();
            status = 4;
            setInfoString("更新已完成");
            running = false;
        }
        catch (Exception e){
            e.printStackTrace();
            errorInfo = e.getMessage();
            status = -1;
            running = false;
            setInfoString("更新出现异常，稍后重试");
        }
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    public void setIdFrom(Integer idFrom) {
        this.idFrom = idFrom;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfoString() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }
}
