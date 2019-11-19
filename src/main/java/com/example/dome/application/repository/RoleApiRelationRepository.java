package com.example.dome.application.repository;

import com.example.dome.application.entity.RoleApiRelation;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleApiRelationRepository extends CrudRepository<RoleApiRelation, Integer> {

    @Override
    @EntityGraph(attributePaths = {"role", "api"})
    Iterable<RoleApiRelation> findAll();

    @EntityGraph(attributePaths = {"role", "api"})
    Iterable<RoleApiRelation> findAll(Example<RoleApiRelation> example);
}
