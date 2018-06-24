package com.xgstudio.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 管理字段
 * @author chenxsa
 */
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ManagerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Version
    private int version;
    @Column(name = "created_by",nullable = false,columnDefinition="VARCHAR(100) DEFAULT ''")
    private String  createdBy="";

    @Column(name = "created_datetime",columnDefinition="DATETIME DEFAULT NULL")
    private LocalDateTime createdDatetime;

    @Column(name = "last_modified",columnDefinition="DATETIME DEFAULT NULL")
    private LocalDateTime lastModified;
    @Column(name = "last_modified_by",nullable = false,columnDefinition="VARCHAR(100) DEFAULT ''")
    private String lastModifiedBy="";
    @Column(name = "is_deleted",nullable = false,columnDefinition=" BIT(1) DEFAULT 0")
    private boolean deleted;
    @Column(name = "is_disabled",nullable = false,columnDefinition=" BIT(1) DEFAULT 0")
    private boolean disabled;

    public ManagerEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
