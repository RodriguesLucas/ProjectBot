package br.com.unisc.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.dtos.CategoryDto;
import br.com.unisc.project.entities.CategoryEntity;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

	Optional<CategoryEntity> findByDescription(String description);

	void deleteByCategoryParentId(Long id);

	@Query(value = "select * from category c where c.id=:id", nativeQuery = true)
	Optional<CategoryEntity> findById(Long id);

	@Query(value = "select * from category c where c.category_parent_id=:id", nativeQuery = true)
	List<CategoryDto> findAllByParentId(Long id);


}
