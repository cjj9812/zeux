package com.nanshen.module.system.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.nanshen.module.system.entity.SysFile;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysFileVO {

    private Long id;

    private String name;

    private Long size;

    private String suffix;

    private String md5;

    private String path;

    private Long createTime;

    private Long updateTime;

    private String isDel;

    private String serverUrl;

    public SysFileVO(){}

    public SysFileVO(SysFile sysFile){
        this.id=sysFile.getId();
        this.name=sysFile.getName();
        this.size=sysFile.getSize();
        this.suffix=sysFile.getSuffix();
        this.md5=sysFile.getMd5();
        this.path=sysFile.getPath();
        this.createTime=sysFile.getCreateTime();
        this.updateTime=sysFile.getUpdateTime();
        this.isDel=sysFile.getIsDel();
    }
}
