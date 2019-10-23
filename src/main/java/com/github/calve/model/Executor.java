package com.github.calve.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "executors")
public class Executor extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "enabled") // TODO: 14.10.2019 not null ref
    private boolean enabled = true;

    public Executor() {
    }

    public Executor(Integer id, String name, boolean enabled) {
        super.setId(id);
        this.name = name;
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Executor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Executor.class.getSimpleName() + "[", "]")
                .add("id='" + getId() + "'")
                .add("name='" + name + "'")
                .add("enabled=" + enabled)
                .toString();
    }
}
