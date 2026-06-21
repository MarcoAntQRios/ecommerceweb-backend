package com.ecommerce.auth_service.mapper;

import com.ecommerce.auth_service.dto.request.UsuarioRequestDTO;
import com.ecommerce.auth_service.dto.response.UsuarioResponseDTO;
import com.ecommerce.auth_service.model.Rol;
import com.ecommerce.auth_service.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword()); // se encripta en el service
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());

        Rol rol = new Rol();
        rol.setId(dto.getRolId());
        usuario.setRol(rol);

        return usuario;
    }

    public UsuarioResponseDTO toDto(Usuario usuario) {
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.getId());
        response.setNombre(usuario.getNombre());
        response.setApellido(usuario.getApellido());
        response.setCorreo(usuario.getCorreo());
        response.setTelefono(usuario.getTelefono());
        response.setDireccion(usuario.getDireccion());
        response.setRolNombre(usuario.getRol().getNombre());
        return response;
    }


}
