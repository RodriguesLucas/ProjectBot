package br.com.unisc.project.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.entities.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

	Optional<CategoryEntity> findByDescription(String description);

	void deleteByCategoryParentId(Long id);

	@Query(value = "select c from category where c.id=?1", nativeQuery = true)
	Optional<CategoryEntity> findById(Long id);


}
