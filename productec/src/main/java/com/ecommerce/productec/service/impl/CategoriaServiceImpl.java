package com.ecommerce.productec.service.impl;

import com.ecommerce.productec.dto.request.CategoriaRequestDTO;
import com.ecommerce.productec.dto.response.CategoriaResponseDTO;
import com.ecommerce.productec.mapper.CategoriaMapper;
import com.ecommerce.productec.model.Categoria;
import com.ecommerce.productec.repository.CategoriaRepository;
import com.ecommerce.productec.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public CategoriaResponseDTO crear(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        return categoriaMapper.toDto(categoriaRepository.save(categoria));    }

    @Override
    public List<CategoriaResponseDTO> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .toList();
    }


    @Override
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categoría no encontrada con id: " + id
                ));
        return categoriaMapper.toDto(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Categoría no encontrada con id: " + id
                ));
        categoria.setNombre(dto.getNombre());
        return categoriaMapper.toDto(categoriaRepository.save(categoria));
    }

}
