package br.com.unisc.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.unisc.project.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
	Optional<ProductEntity> findByCategoryEntityId(Long id);

}
