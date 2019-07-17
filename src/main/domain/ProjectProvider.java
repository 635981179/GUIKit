package domain;

import java.util.Date;

public class ProjectProvider {
    /**  */
    private Long id;
    /** 项目名称 */
    private String name;
    /** 数据指纹 */
    private String sjzw;
    /** 供应商 */
    private String gysmc;
    /** 供应商社会信用代码 */
    private String gysscc;
    /**  */
    private Date updateTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setSjzw(String sjzw)
    {
        this.sjzw = sjzw;
    }

    public String getSjzw()
    {
        return sjzw;
    }
    public void setGysmc(String gysmc)
    {
        this.gysmc = gysmc;
    }

    public String getGysmc()
    {
        return gysmc;
    }
    public void setGysscc(String gysscc)
    {
        this.gysscc = gysscc;
    }

    public String getGysscc()
    {
        return gysscc;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }
}
