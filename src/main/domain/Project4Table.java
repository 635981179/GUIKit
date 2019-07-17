package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class Project4Table {
    private Long id;
    private String xmmc;
    private Date startTime;
    private String provider;

    public Project4Table(Long id, String xmmc, Date startTime, String provider){
        this.id=id;
        this.xmmc=xmmc;
        this.startTime=startTime;
        this.provider=provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String toString(){
        return "id: "+getId() + " " +
                "xmmc: "+getXmmc() + " " +
                "jhkgrq: "+new SimpleDateFormat("yyyy-MM-dd").format(getStartTime())+" " +
                "gysmc: " + getProvider();
    }
}
