package com.sip.ams.controllers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sip.ams.entities.Article;
import com.sip.ams.entities.Provider;
import com.sip.ams.models.MessageResponse;
import com.sip.ams.repositories.ArticleRepository;
import com.sip.ams.repositories.ProviderRepository;

@RestController
@CrossOrigin(origins="*")
@RequestMapping({"/articles"})
public class ArticleRestController {
	
	private final ArticleRepository articleRepository;
	private final ProviderRepository providerRepository;
	//public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads";
	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	
	Article article= new Article();
    
	@Autowired
    public ArticleRestController(ArticleRepository articleRepository, ProviderRepository providerRepository) {
        this.articleRepository = articleRepository;
        this.providerRepository = providerRepository;
    }
	
    @GetMapping("/list")
    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }

  /*  @PostMapping("/add/{providerId}")
    Article createArticle(@PathVariable (value = "providerId") Long providerId,
            @Valid @RequestBody Article article) {
			return providerRepository.findById(providerId).map(provider -> {
			article.setProvider(provider);
			return articleRepository.save(article);
			}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
			}*/
    
	// ############ upload images  ########  on a besoin de çà
 /*  @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadFile(@RequestParam("file") MultipartFile file) {
      String message = "";
      try {
        storageService.save(file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }	*/
    
   // @PostMapping("/add/{providerId}")
/*    @RequestMapping(value = "/add/{providerId}",
    		method = RequestMethod.POST,consumes ="application/json")
    //this.httpClient.post<any>(this.SERVER_URL+ '/' + providerId, this.formData).subscribe(
		public  ResponseEntity<MessageResponse> addArticle(
				@RequestBody Article article,
				BindingResult result,
				@RequestParam("file") MultipartFile[] files,
				@PathVariable (value = "providerId") Long providerId)
			{
    	
    	  String message = "";
		
		  Provider provider = providerRepository.findById(providerId) .orElseThrow(()-> new
		  IllegalArgumentException("Invalid provider Id:" + providerId));
		  article.setProvider(provider);
		 

		// System.out.println(article);

		/// part upload

		StringBuilder fileName = new StringBuilder();
		MultipartFile file = files[0];

		// long total = articleRepository.count();

		// LocalDateTime ldt = LocalDateTime.now();
		// String sldt = ldt.toString();

		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd_hhmmss");
		String var = ldt.format(format);

		Path fileNameAndPath = Paths.get(uploadDirectory, var + "_" + file.getOriginalFilename());

		fileName.append(var + "_" + file.getOriginalFilename());
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		article.setPicture(fileName.toString());
		/// Fin Upload 
		/// start save in DB
		try {
		articleRepository.save(article);
		 message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
	      } catch (Exception e) {
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(message));
	      }

		// return article.getLabel() + " " +article.getPrice() + " " + p.toString();
	}*/
    
    //// code Amine /////
    @PostMapping("/add")
	public Article uplaodImage(
			@RequestParam("imageFile") MultipartFile file,
			@RequestParam("imageName") String imageName, 
			@RequestParam("providerId") String sproviderId,
		//	@PathVariable (value = "providerId") Long providerId,
			@RequestParam("label") String label, 
			@RequestParam("price") String sprice
			
			
			) throws IOException {
    	Long providerId = Long.parseLong(sproviderId);
    	Long price = Long.parseLong(sprice);
    	
    	Provider provider = providerRepository.findById(providerId) .orElseThrow(()-> new
    			  IllegalArgumentException("Invalid provider Id:" + providerId));
    			  this.article.setProvider(provider);

		
		// 
		String newImageName = getSaltString().concat(file.getOriginalFilename());
		
		
		
		try {
			Files.copy(file.getInputStream(), this.root.resolve(newImageName));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}

		//Provider provider = new Provider(name, address, email, newImageName);
		this.article.setLabel(label);
		this.article.setPicture(newImageName);
		this.article.setPrice(price);

		//providerRepository.save(provider);
		articleRepository.save(this.article);

//		return provider;
		return this.article;
	}
    
 // rundom string to be used to the image name
 	protected static String getSaltString() {
 		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
 		StringBuilder salt = new StringBuilder();
 		Random rnd = new Random();
 		while (salt.length() < 18) { // length of the random string.
 			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
 			salt.append(SALTCHARS.charAt(index));
 		}
 		String saltStr = salt.toString();
 		return saltStr;

 	}
    
    //// fin code Amine  //////
    
    
    
    
    
    @PutMapping("/update/{providerId}/{articleId}")
    public Article updateArticle(@PathVariable (value = "providerId") Long providerId,
                                 @PathVariable (value = "articleId") Long articleId,
                                 @Valid @RequestBody Article articleRequest) {
        if(!providerRepository.existsById(providerId)) {
            throw new IllegalArgumentException("ProviderId " + providerId + " not found");
        }

        return articleRepository.findById(articleId).map(article -> {
        	 article.setPrice(articleRequest.getPrice());
             article.setLabel(articleRequest.getLabel()); 
             article.setPicture(articleRequest.getPicture()); 
        return articleRepository.save(article);
        }).orElseThrow(() -> new IllegalArgumentException("ArticleId " + articleId + "not found"));
    }
    // ajout 28 05 //
    @GetMapping("/{articleId}") 
    

    public Article getArticle(@PathVariable (value = "articleId") Long articleId) {

    	Optional<Article> article = articleRepository.findById(articleId);
    
		System.out.println("Cet article :"+article.get().getProvider().getName());
		return article.get();
		

	}
    //
    
    @DeleteMapping("/delete/{articleId}")
    public ResponseEntity<?> deleteArticle(@PathVariable (value = "articleId") Long articleId) {
        return articleRepository.findById(articleId).map(article -> {
            articleRepository.delete(article);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new IllegalArgumentException("Article not found with id " + articleId));
    }
}
