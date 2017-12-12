package br.com.ifsp.opendata.controller;

import java.sql.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.ifsp.opendata.entity.Document;
import br.com.ifsp.opendata.repository.DocumentRepository;

@Controller
public class SearchController {

	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
    private EntityManagerFactory entityManagerFactory;
	
	@GetMapping(path="/buscar")
	public @ResponseBody Iterable<Document> getAllDocuments() {
		// This returns a JSON or XML with the users
		return documentRepository.findAll();
	}
	
	@GetMapping(path="/buscar/nome")
    public Double getDayPrice(@RequestParam("nome") String nome){
        EntityManager session = entityManagerFactory.createEntityManager();
        try {
            Double daypr = (Double)session.createNativeQuery("Select id, nome FROM document WHERE nome=:nome")
                    .setParameter("nome", nome)
                    .getSingleResult();

            return daypr;
        }
        catch (NoResultException e){
            return null;
        }
        finally {
            if(session.isOpen()) session.close();
        }
    }

}
