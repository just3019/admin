package org.demon.pojo;

import java.util.Date;

public class VideoUser {
    private Integer id;

    private String channelNo;

    private Integer type;

    private String cellModel;

    private String cellVersion;

    private Integer packageId;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo == null ? null : channelNo.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCellModel() {
        return cellModel;
    }

    public void setCellModel(String cellModel) {
        this.cellModel = cellModel == null ? null : cellModel.trim();
    }

    public String getCellVersion() {
        return cellVersion;
    }

    public void setCellVersion(String cellVersion) {
        this.cellVersion = cellVersion == null ? null : cellVersion.trim();
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}