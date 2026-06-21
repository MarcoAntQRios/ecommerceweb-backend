package com.ecommerce.usuariotec.mapper;

import com.ecommerce.usuariotec.dto.request.UsuarioRequestDTO;
import com.ecommerce.usuariotec.dto.request.UsuarioUpdateDTO;
import com.ecommerce.usuariotec.dto.response.UsuarioResponseDTO;
import com.ecommerce.usuariotec.model.Rol;
import com.ecommerce.usuariotec.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public Usuario toEntity(UsuarioRequestDTO dto, Rol rol) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
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

        response.setRolId(usuario.getRol().getId());
        response.setRolNombre(usuario.getRol().getNombre());

        return response;
    }

    public void updateEntity(UsuarioUpdateDTO dto, Usuario usuario, Rol rol) {

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());
        usuario.setRol(rol);

    }
}
