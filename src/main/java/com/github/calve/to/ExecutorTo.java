package com.github.calve.to;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

public class ExecutorTo extends BaseTo implements Serializable {

    private String name;
    private boolean enabled;

    public ExecutorTo() {
    }

    public ExecutorTo(Integer id, String name, boolean active) {
        super(id);
        this.name = name;
        this.enabled = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExecutorTo that = (ExecutorTo) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, enabled);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExecutorTo.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("status='" + enabled + "'")
                .toString();
    }
}