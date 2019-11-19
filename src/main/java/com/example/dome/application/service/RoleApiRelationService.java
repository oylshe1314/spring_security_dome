package com.example.dome.application.service;

import com.example.dome.application.entity.Api;
import com.example.dome.application.entity.RoleApiRelation;
import com.example.dome.application.repository.RoleApiRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleApiRelationService {

    @Autowired
    private RoleApiRelationRepository roleApiRelationRepository;

    public List<RoleApiRelation> getAll() {
        return (List<RoleApiRelation>) roleApiRelationRepository.findAll();
    }

    public List<RoleApiRelation> getAllByPath(String path) {
        RoleApiRelation roleApiRelation = new RoleApiRelation();
        roleApiRelation.api = new Api();
        roleApiRelation.api.path = path;

        //match roleApiRelation.api.path value by start with
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("api.path", ExampleMatcher.GenericPropertyMatchers.startsWith());

        return (List<RoleApiRelation>) roleApiRelationRepository.findAll(Example.of(roleApiRelation, exampleMatcher));
    }
}
