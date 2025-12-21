package com.expensesharing.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensesharing.backend.dto.GroupDto;
import com.expensesharing.backend.dto.GroupMemberRequest;
import com.expensesharing.backend.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        GroupDto createdGroup = groupService.createGroup(groupDto);
        return ResponseEntity.ok(createdGroup);
    }

    @PostMapping("/{groupId}/members")
    public ResponseEntity<GroupDto> addUserToGroup(
            @PathVariable Long groupId, 
            @RequestBody GroupMemberRequest request) {
        
        GroupDto updatedGroup = groupService.addUserToGroup(groupId, request.getUserId());
        return ResponseEntity.ok(updatedGroup);
    }
}