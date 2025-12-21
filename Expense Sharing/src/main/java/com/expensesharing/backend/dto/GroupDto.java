package com.expensesharing.backend.dto;

import java.util.List;

import lombok.Data;

@Data
public class GroupDto {
    private Long id;
    private String name;
    private Long creatorId; // Who is making this group?
    private List<UserDto> members; // Who is in the group?
}