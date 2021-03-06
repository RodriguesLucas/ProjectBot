package br.com.unisc.project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.entities.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

	Optional<CategoryEntity> findByDescription(String description);

	void deleteByCategoryParentId(Long id);

	@Query(value = "select * from category c where c.id=:id", nativeQuery = true)
	Optional<CategoryEntity> findById(@Param("id") Long id);

	@Query(value = "select * from category c where c.category_parent_id=:id", nativeQuery = true)
	List<CategoryEntity> findAllByParentId(Long id);

	@Query(value = "select * from category c where c.id not in (select category_parent_id from category cp where cp.category_parent_id is not null) and c.id not in (select category_id from product)", nativeQuery = true)
	List<CategoryEntity> findAllCategoryParent();

	@Query(value = "select * from category c where c.id not in (select category_id from product)", nativeQuery = true)
	List<CategoryEntity> findCategoryParendAddAndEdit();

	@Query(value = "select * from category c where c.id not in (select category_parent_id from category cp where cp.category_parent_id is not null)", nativeQuery = true)
	List<CategoryEntity> findAllCategoriesForProductAdd();

	@Query(value = "select * from category c where c.id in (select category_id from product)", nativeQuery = true)
	List<CategoryEntity> findAllCategoriesForProductEdit();

	@Query(value = "select distinct * from category c where c.id in (select p.category_id from product p where p.id not in (select h.product_id from history h))", nativeQuery = true)
	List<CategoryEntity> findCategoryDel();
}