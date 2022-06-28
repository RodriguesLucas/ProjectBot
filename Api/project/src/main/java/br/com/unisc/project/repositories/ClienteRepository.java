package br.com.unisc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.dtos.ClienteAllDto;
import br.com.unisc.project.entities.ClientEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClientEntity, Long>{

	@Query(value = "select id, cpf_cnpj, name, phone, (select count(id) from history h where h.cliente_id = c.id) as numQueries, (select avg(p.price) from product p where p.id in (select product_id from history h where h.cliente_id = c.id)) as priceMean from client c", nativeQuery = true)
	List<ClienteAllDto> findClientAllDto();

}
