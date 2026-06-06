package com.ecommerce.usuariotec.mapper;

import com.ecommerce.usuariotec.dto.request.RolRequestDTO;
import com.ecommerce.usuariotec.dto.response.RolResponseDTO;
import com.ecommerce.usuariotec.model.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public Rol toEntity (RolRequestDTO dto){
        Rol rol = new Rol();
        rol.setNombre(dto.getNombre());
        return rol;
    }

    public RolResponseDTO toDto(Rol rol){
        RolResponseDTO responseDto = new RolResponseDTO();
        responseDto.setId(rol.getId());
        responseDto.setNombre(rol.getNombre());
        return responseDto;
    }
}
