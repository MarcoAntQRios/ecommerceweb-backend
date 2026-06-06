package com.ecommerce.usuariotec.service.impl;

import com.ecommerce.usuariotec.dto.request.RolRequestDTO;
import com.ecommerce.usuariotec.dto.response.RolResponseDTO;
import com.ecommerce.usuariotec.mapper.RolMapper;
import com.ecommerce.usuariotec.model.Rol;
import com.ecommerce.usuariotec.repository.RolRepository;
import com.ecommerce.usuariotec.service.RolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;
    private  final RolMapper rolMapper;

    @Override
    public RolResponseDTO create(RolRequestDTO dto) {
        Rol rol = rolMapper.toEntity(dto);
        Rol rolGuardado = rolRepository.save(rol);
        return rolMapper.toDto(rolGuardado);
    }

    @Override
    public List<RolResponseDTO> listar() {
        return rolRepository.findAll()
                .stream()
                .map(rolMapper::toDto)
                .toList();
    }



    @Override
    public RolResponseDTO buscarPorId(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Rol no encontrado con id: " + id
                ));
        return rolMapper.toDto(rol);
    }
}
