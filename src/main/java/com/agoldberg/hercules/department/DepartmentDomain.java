package com.agoldberg.hercules.department;

import com.agoldberg.hercules.domain.Auditable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class DepartmentDomain extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String details;
    private boolean enabled;

    public DepartmentDomain() {
    }


    public DepartmentDomain(String name, String details, boolean enabled) {
        this.name = name;
        this.details = details;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
