package br.com.ifsp.opendata.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.ifsp.opendata.entity.Document;

public interface DocumentRepository extends CrudRepository<Document, Long> {	

}
