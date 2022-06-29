package br.com.unisc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.entities.HistoryEntity;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Long>{

	@Query(value = "select * from history h where h.cliente_id = :id", nativeQuery = true)
	List<HistoryEntity> findByClientId(Long id);

}
