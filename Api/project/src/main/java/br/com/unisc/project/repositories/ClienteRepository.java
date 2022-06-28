package br.com.unisc.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unisc.project.entities.ClientEntity;

@Repository
public interface ClienteRepository extends JpaRepository<ClientEntity, Long>{

}
