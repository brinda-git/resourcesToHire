package com.resourceallocation.resource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.el.stream.Stream;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @Column(name = "resourceid", nullable = false)
    private int resourceid;
    @Column(name = "resourcename", nullable = false)
    private String resourcename;
    @Column(name = "experience", nullable = false)
    private String experience;
    @Column(name = "skills", nullable = false)
    private String skills;

    public Resource() {
    }

    public int getResourceid() {
        return resourceid;
    }

    public void setResourceid(int resourceid) {
        this.resourceid = resourceid;
    }

    public String getResourcename() {
        return resourcename;
    }

    public void setResourcename(String resourcename) {
        this.resourcename = resourcename;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }




    public Resource(int resourceid, String resourcename, String experience, String skills) {
        this.resourceid = resourceid;
        this.resourcename = resourcename;
        this.experience = experience;
        this.skills = skills;
    }
//    public String[] getSkillsArray() {
//        return skills != null ? skills.split(",") : new String[0];
//    }
    @Override
    public String toString() {
        return "Resource{" +
                "resourceid=" + resourceid +
                "resourcename='" + resourcename + '\'' +
                "experience='" + experience + '\'' +
                "skills='" + skills + '\'' +
                "}";
    }

}
