package com.expensesharing.backend.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensesharing.backend.dto.GroupDto;
import com.expensesharing.backend.dto.UserDto;
import com.expensesharing.backend.model.Group;
import com.expensesharing.backend.model.User;
import com.expensesharing.backend.repository.GroupRepository;
import com.expensesharing.backend.repository.UserRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public GroupDto createGroup(GroupDto groupDto) {
        User creator = userRepository.findById(groupDto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + groupDto.getCreatorId()));

        Group group = new Group();
        group.setName(groupDto.getName());
        group.setCreatedBy(creator);
        
        group.getMembers().add(creator);

        Group savedGroup = groupRepository.save(group);

        return mapToDto(savedGroup);
    }

    private GroupDto mapToDto(Group group) {
        GroupDto dto = new GroupDto();
        dto.setId(group.getId());
        dto.setName(group.getName());
        dto.setCreatorId(group.getCreatedBy().getId());

        dto.setMembers(group.getMembers().stream().map(user -> {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            return userDto;
        }).collect(Collectors.toList()));
        
        return dto;
    }

    
    public GroupDto addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!group.getMembers().contains(user)) {
            group.getMembers().add(user);
            groupRepository.save(group); 
        }

        return mapToDto(group);
    }
}