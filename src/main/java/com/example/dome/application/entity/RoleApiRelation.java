package com.example.dome.application.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class RoleApiRelation {

    @Id
    public Integer id;

    public Integer roleId;

    @OneToOne(targetEntity = Role.class)
    @JoinColumn(name = "roleId", insertable = false, updatable = false)
    public Role role;

    public Integer apiId;

    @OneToOne(targetEntity = Api.class)
    @JoinColumn(name = "apiId", insertable = false, updatable = false)
    public Api api;
}
