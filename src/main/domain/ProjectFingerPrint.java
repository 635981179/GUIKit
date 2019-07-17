package domain;

import java.util.Date;

public class ProjectFingerPrint {
    /**  */
    private Long id;
    /** 项目名称 */
    private String xmmc;
    /** 项目地址 */
    private String xmdz;
    /** 建设单位 */
    private String jsdw;
    /** 项目数据指纹 */
    private String xmsjzw;
    private Integer type;
    /**  */
    private Date createTime;
    /**  */
    private String createBy;
    /**  */
    private Date updateTime;
    /**  */
    private String updateBy;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setXmmc(String xmmc)
    {
        this.xmmc = xmmc;
    }

    public String getXmmc()
    {
        return xmmc;
    }
    public void setXmdz(String xmdz)
    {
        this.xmdz = xmdz;
    }

    public String getXmdz()
    {
        return xmdz;
    }
    public void setJsdw(String jsdw)
    {
        this.jsdw = jsdw;
    }

    public String getJsdw()
    {
        return jsdw;
    }
    public void setXmsjzw(String xmsjzw)
    {
        this.xmsjzw = xmsjzw;
    }

    public String getXmsjzw()
    {
        return xmsjzw;
    }
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getCreateTime()
    {
        return createTime;
    }
    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateBy()
    {
        return createBy;
    }
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }
    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
