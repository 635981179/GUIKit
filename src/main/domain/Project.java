package domain;

import java.util.Date;

public class Project {

    /**  */
    private Long id;
    /** 项目名称 */
    private String xmmc;
    /** 项目类别 */
    private String xmlb;
    /** 投资类别 */
    private String tzlb;
    /** 项目编号 */
    private String xmbh;
    /** 承包类型 */
    private String cblx;
    /** 工程地点 */
    private String gcdd;
    /** 工程造价 */
    private String gczj;
    /** 建筑面积 */
    private String jzmj;
    /** 建设单位 */
    private String jsdw;
    /** 建设单位联系人 */
    private String jsdwlxr;
    /** 建设单位联系人电话 */
    private String jsdwlxrdh;
    /** 施工单位 */
    private String sgdw;
    /** 施工单位项目经理 */
    private String sgdwxmjl;
    /** 施工单位项目经理联系电话 */
    private String sgdwxmjllxdh;
    /** 企业安全分管领导 */
    private String qyaqfgld;
    /** 企业安全分管领导电话 */
    private String qyaqfgldlxdh;
    /** 监理单位 */
    private String jldw;
    /** 项目总监 */
    private String xmzj;
    /** 总监联系电话 */
    private String zjlxdh;
    /** 项目所属地区 */
    private String ssdq;
    /** 计划开工日期 */
    private Date jhkgrq;
    /** 计划竣工日期 */
    private Date jhjgrq;
    /** 是否政府项目 */
    private Integer zfxm;
    /** 是否申报文明工地 */
    private Integer wmgd;
    /** 是否申报优质工程 */
    private Integer yzgc;
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
    public void setXmlb(String xmlb)
    {
        this.xmlb = xmlb;
    }

    public String getXmlb()
    {
        return xmlb;
    }
    public void setTzlb(String tzlb)
    {
        this.tzlb = tzlb;
    }

    public String getTzlb()
    {
        return tzlb;
    }
    public void setXmbh(String xmbh)
    {
        this.xmbh = xmbh;
    }

    public String getXmbh()
    {
        return xmbh;
    }
    public void setCblx(String cblx)
    {
        this.cblx = cblx;
    }

    public String getCblx()
    {
        return cblx;
    }
    public void setGcdd(String gcdd)
    {
        this.gcdd = gcdd;
    }

    public String getGcdd()
    {
        return gcdd;
    }
    public void setGczj(String gczj)
    {
        this.gczj = gczj;
    }

    public String getGczj()
    {
        return gczj;
    }
    public void setJzmj(String jzmj)
    {
        this.jzmj = jzmj;
    }

    public String getJzmj()
    {
        return jzmj;
    }
    public void setJsdw(String jsdw)
    {
        this.jsdw = jsdw;
    }

    public String getJsdw()
    {
        return jsdw;
    }
    public void setJsdwlxr(String jsdwlxr)
    {
        this.jsdwlxr = jsdwlxr;
    }

    public String getJsdwlxr()
    {
        return jsdwlxr;
    }
    public void setJsdwlxrdh(String jsdwlxrdh)
    {
        this.jsdwlxrdh = jsdwlxrdh;
    }

    public String getJsdwlxrdh()
    {
        return jsdwlxrdh;
    }
    public void setSgdw(String sgdw)
    {
        this.sgdw = sgdw;
    }

    public String getSgdw()
    {
        return sgdw;
    }
    public void setSgdwxmjl(String sgdwxmjl)
    {
        this.sgdwxmjl = sgdwxmjl;
    }

    public String getSgdwxmjl()
    {
        return sgdwxmjl;
    }
    public void setSgdwxmjllxdh(String sgdwxmjllxdh)
    {
        this.sgdwxmjllxdh = sgdwxmjllxdh;
    }

    public String getSgdwxmjllxdh()
    {
        return sgdwxmjllxdh;
    }
    public void setQyaqfgld(String qyaqfgld)
    {
        this.qyaqfgld = qyaqfgld;
    }

    public String getQyaqfgld()
    {
        return qyaqfgld;
    }
    public void setQyaqfgldlxdh(String qyaqfgldlxdh)
    {
        this.qyaqfgldlxdh = qyaqfgldlxdh;
    }

    public String getQyaqfgldlxdh()
    {
        return qyaqfgldlxdh;
    }
    public void setJldw(String jldw)
    {
        this.jldw = jldw;
    }

    public String getJldw()
    {
        return jldw;
    }
    public void setXmzj(String xmzj)
    {
        this.xmzj = xmzj;
    }

    public String getXmzj()
    {
        return xmzj;
    }
    public void setZjlxdh(String zjlxdh)
    {
        this.zjlxdh = zjlxdh;
    }

    public String getZjlxdh()
    {
        return zjlxdh;
    }
    public void setSsdq(String ssdq)
    {
        this.ssdq = ssdq;
    }

    public String getSsdq()
    {
        return ssdq;
    }
    public void setJhkgrq(Date jhkgrq)
    {
        this.jhkgrq = jhkgrq;
    }

    public Date getJhkgrq()
    {
        return jhkgrq;
    }
    public void setJhjgrq(Date jhjgrq)
    {
        this.jhjgrq = jhjgrq;
    }

    public Date getJhjgrq()
    {
        return jhjgrq;
    }
    public void setZfxm(Integer zfxm)
    {
        this.zfxm = zfxm;
    }

    public Integer getZfxm()
    {
        return zfxm;
    }
    public void setWmgd(Integer wmgd)
    {
        this.wmgd = wmgd;
    }

    public Integer getWmgd()
    {
        return wmgd;
    }
    public void setYzgc(Integer yzgc)
    {
        this.yzgc = yzgc;
    }

    public Integer getYzgc()
    {
        return yzgc;
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

    public String toString(){
        return getId() + "\n" +
                getGczj() + "\n" +
                getJzmj() + "\n" +
                getWmgd() + "\n" +
                getYzgc() + "\n" +
                getZfxm() + "\n" +
                getXmmc() + "\n" +
                getCblx() + "\n" +
                getGcdd() + "\n" +
                getJhjgrq() + "\n" +
                getJhkgrq() + "\n" +
                getJldw() + "\n" +
                getJsdw() + "\n" +
                getJsdwlxr() + "\n" +
                getJsdwlxrdh() + "\n" +
                getXmbh() + "\n" +
                getXmlb() + "\n" +
                getXmzj() + "\n" +
                getZjlxdh() + "\n";
    }
}
