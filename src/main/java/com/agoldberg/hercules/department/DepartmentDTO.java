package com.agoldberg.hercules.department;

import com.agoldberg.hercules.domain.Auditable;

public class DepartmentDTO extends Auditable<String> {
    private Long id;
    private String name;
    private String details;
    private boolean enabled;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name, String details, boolean enabled) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.enabled = enabled;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
}
