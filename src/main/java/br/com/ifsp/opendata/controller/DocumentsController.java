package br.com.ifsp.opendata.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import br.com.ifsp.opendata.repository.DocumentRepository;

@Controller
@RequestMapping(path="/batata")
public class DocumentsController {

	//@Autowired
	//private DocumentRepository documentRepository;
	
	@RequestMapping(path="/teste", method=RequestMethod.GET)
	public @ResponseBody String provideUploadInfo() {
		
		return "Para realizar um upload, utilize o metodo POST.";
	}
	
}
