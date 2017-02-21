package com.sunmonkeyapps.githubrepos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Repo {
    public Repo() {
    }

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("clone_url")
    @Expose
    private String cloneUrl;

    @SerializedName("language")
    @Expose
    private String language;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCloneUrl() {
        return cloneUrl;
    }

    public void setCloneUrl(String cloneUrl) {
        this.cloneUrl = cloneUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
