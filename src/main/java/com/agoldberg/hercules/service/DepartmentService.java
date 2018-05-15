package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.DepartmentDTO;

import javax.annotation.security.RolesAllowed;
import java.util.List;

public interface DepartmentService {
    @RolesAllowed("ROLE_ADMIN")
    void createDepartment(DepartmentDTO dto);

    @RolesAllowed("ROLE_ADMIN")
    void deleteDepartment(DepartmentDTO dto);

    @RolesAllowed("ROLE_ADMIN")
    void modifyDepartment(DepartmentDTO dto);

    @RolesAllowed("ROLE_ADMIN")
    void toggleDepartmentEnabled(Long id);

    List<DepartmentDTO> getDepartments();

    List<DepartmentDTO> getEnabledDepartments();

    String getDepartmentName(Long id);

    DepartmentDTO getDepartmentDTO(Long id);
}
