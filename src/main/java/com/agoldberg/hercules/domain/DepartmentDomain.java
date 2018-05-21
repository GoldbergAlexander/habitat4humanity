package com.agoldberg.hercules.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class DepartmentDomain extends Auditable<String>{
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private StoreLocationDomain location;
    private double size;
    private boolean enabled;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
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

    public StoreLocationDomain getLocation() {
        return location;
    }

    public void setLocation(StoreLocationDomain location) {
        this.location = location;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentDomain domain = (DepartmentDomain) o;
        return Double.compare(domain.size, size) == 0 &&
                Objects.equals(name, domain.name) &&
                Objects.equals(location, domain.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, location, size);
    }
}
