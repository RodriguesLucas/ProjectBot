package br.com.unisc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.entities.ClientAllInfoEntity;

@Repository
public interface ClienteAllInfoRepository extends JpaRepository<ClientAllInfoEntity, Long>{

	@Query(value = "select id, cpf_cnpj, name, phone, (select count(id) from history h where h.cliente_id = c.id) as num_queries, (select avg(p.price) from product p where p.id in (select product_id from history h where h.cliente_id = c.id)) as price_mean from client c", nativeQuery = true)
	List<ClientAllInfoEntity> findClientAllDto();

}
