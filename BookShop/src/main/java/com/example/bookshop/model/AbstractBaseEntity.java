package com.example.bookshop.model;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {

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
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || !this.getClass().equals(Hibernate.getClass(other))) return false;
        return (id != null && id.equals(((AbstractBaseEntity) other).id));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": #" + id.toString();
    }

}
