package com.example.chat_relex.service;

import com.example.chat_relex.exceptions.EntityDoesNotExistException;
import com.example.chat_relex.mapper.RoleMapper;
import com.example.chat_relex.models.dto.RoleDTO;
import com.example.chat_relex.models.entity.Role;
import com.example.chat_relex.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.chat_relex.models.constant.Role.ADMIN;
import static com.example.chat_relex.models.constant.Role.USER;


@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDTO getUserRole() {
        Role role = roleRepository.findByName(USER).orElseThrow(
                () -> new EntityDoesNotExistException("A role with this name does not exist")
        );
        return roleMapper.toDTOFromEntity(role);
    }

    public boolean isAdmin(Set<RoleDTO> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ADMIN));
    }
}