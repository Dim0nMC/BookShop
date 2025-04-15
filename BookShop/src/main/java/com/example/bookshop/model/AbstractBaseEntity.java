package com.example.bookshop.model;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.StringJoiner;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Integer id;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    protected AbstractBaseEntity() {}

    protected AbstractBaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !this.getClass().equals(Hibernate.getClass(other))) return false;
        AbstractBaseEntity that = (AbstractBaseEntity) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", this.getClass().getSimpleName() + "{", "}");
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (Collection.class.isAssignableFrom(field.getType()) ||
                        Map.class.isAssignableFrom(field.getType()) ||
                        field.getType().getPackageName().startsWith("com.example")) {
                    joiner.add(field.getName() + "=<ignored>");
                } else {
                    Object value = field.get(this);
                    joiner.add(field.getName() + "=" + value);
                }
            } catch (IllegalAccessException e) {
                joiner.add(field.getName() + "=<access denied>");
            }
        }
        return joiner.toString();
    }


}
