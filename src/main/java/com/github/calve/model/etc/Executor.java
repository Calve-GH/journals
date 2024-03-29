package com.github.calve.model.etc;

import com.github.calve.model.AbstractEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;
import java.util.StringJoiner;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "executors")
public class Executor extends AbstractEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "enabled")
    private boolean enabled = true;

    public Executor() {
    }

    public Executor(Integer id, String name, boolean enabled) {
        super.setId(id);
        this.name = name;
        this.enabled = enabled;
    }

    public Executor(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Executor executor = (Executor) o;
        return Objects.equals(name, executor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
