package com.example.pasir_pawlik_jakub_k02_lab01.controller;

import com.example.pasir_pawlik_jakub_k02_lab01.dto.GroupDTO;
import com.example.pasir_pawlik_jakub_k02_lab01.model.Group;
import com.example.pasir_pawlik_jakub_k02_lab01.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;

@Controller
public class GroupGraphQLController {
    private final GroupService groupService;

    public GroupGraphQLController(GroupService groupService) {
        this.groupService = groupService;
    }

    @QueryMapping
    public List<Group> groups() {
       return groupService.getAllGroups();
    }

    @MutationMapping
    public Group createGroup(@Valid @Argument GroupDTO groupDTO){
        return groupService.createGroup(groupDTO);
    }

    @MutationMapping
    public Boolean deleteGroup(@Argument Long id){
        groupService.deleteGroup(id);
        return true;
    }
}
