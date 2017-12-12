package br.com.ifsp.opendata.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.ifsp.opendata.entity.Document;
import br.com.ifsp.opendata.repository.DocumentRepository;
import br.com.ifsp.opendata.storage.FileSystemStorageService;
import br.com.ifsp.opendata.storage.StorageFileNotFoundException;
import br.com.ifsp.opendata.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    
    @Autowired
    private DocumentRepository documents;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
    @PostMapping("/upload/csv")
    public String handleFileUploadCSV(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
    
    @PostMapping("/upload/pdf")
    public String handleFileUploadPDF(@RequestParam("file") MultipartFile file,
    		@RequestParam("top") String top,
    		@RequestParam("left") String left,
    		@RequestParam("bottom") String bottom,
    		@RequestParam("right") String right,
    		@RequestParam("nome") String nome,
    		@RequestParam("mes") String mes,
    		@RequestParam("ano") String ano,
    		@RequestParam("estado") String estado,
    		@RequestParam("cidade") String cidade,
    		@RequestParam("paginas") String paginas,
            RedirectAttributes redirectAttributes) {

    	FileSystemStorageService service = (FileSystemStorageService) storageService;
    	
    	String command = "";
    	
    	if (!(top.isEmpty() && bottom.isEmpty() && left.isEmpty() && right.isEmpty())) {
    		
    		command += ("-a " + top + "," + left + "," + bottom + "," + right + " "); 
    		
    	}
    	
    	if (!paginas.isEmpty()) {
    		
    		command += ("-p " + paginas + " ");
    	}
    	
        service.storeCsv(file, command);
        
        Document document = new Document();
        
        if (!nome.isEmpty()) {
        
        	document.setNome(nome);
        }
        
        if (!ano.isEmpty() && !mes.isEmpty()) {
        
        	document.setData(new Date(Integer.parseInt(ano), Integer.parseInt(mes), 0));
        }
        
        if(!cidade.isEmpty()) {
        	
        	document.setCidade(cidade);
        }
        
        if(!estado.isEmpty()) {
        	
        	document.setEstado(estado);
        }
        
        
        document.setDataDeUpload(new Date(Calendar.getInstance().getTimeInMillis()));
        
        document.setTamanho(String.valueOf(file.getSize()));
        
        document.setNomeDoArquivoPDF(file.getOriginalFilename());
        document.setNomeDoArquivoCSV(file.getOriginalFilename().substring(0, file.getOriginalFilename().length()-3) + "csv");
        
        document.setUrl("localhost:8080/files/" + document.getNomeDoArquivoCSV());
        
        documents.save(document);
        
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
