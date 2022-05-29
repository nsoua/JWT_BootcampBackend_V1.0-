package com.sip.ams.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.Provider;
import com.sip.ams.repositories.ProviderRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping({ "/providers", "/hom*" })

public class ProviderController {

	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private ProviderRepository providerRepository;

	@GetMapping("/list")
	public List<Provider> getAllProviders() {
		return (List<Provider>) providerRepository.findAll();
	}

	@PostMapping("/add")
	public Provider createProvider(@Valid @RequestBody Provider provider) {
		return providerRepository.save(provider);
	}
	
	@PutMapping("/{providerId}")
	public Provider updateProvider(@PathVariable Long providerId, @Valid @RequestBody Provider providerRequest) {
		return providerRepository.findById(providerId).map(provider -> {
			provider.setName(providerRequest.getName());
			provider.setEmail(providerRequest.getEmail());
			provider.setAddress(providerRequest.getAddress());
			return providerRepository.save(provider);
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}
	
	@DeleteMapping("/{providerId}")
	public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {
		logger.info("ProviderId : "+providerId.toString());
		return providerRepository.findById(providerId).map(provider -> {
			providerRepository.delete(provider);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}
	
	@GetMapping("/{providerId}")
	public Provider getProvider(@PathVariable Long providerId) {

		Optional<Provider> p = providerRepository.findById(providerId);
		//System.out.println(p);
		return p.get();

	}
	
	
	

	/*@PutMapping("/{providerId}")
	public Provider updateProvider(@PathVariable Long providerId, 
			
			@RequestParam("imageFile") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("email") String email, 
			@RequestParam("address") String address,
			@RequestParam("imageName") String imageName
			//@Valid @RequestBody Provider providerRequest
			
			
			) {
		return providerRepository.findById(providerId).map(provider -> {
			
			// STEP 1 : delete Old Image from server
			String OldImageName = provider.getNomImage();
					
				////////
					try {
						File f = new File(this.root + "/" + OldImageName); // file to be delete
						if (f.delete()) // returns Boolean value
						{
							System.out.println(f.getName() + " deleted"); // getting and printing the file name
						} else {
							System.out.println("failed");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

			 /////// END STEP 1
			
			/// STEP 2 : Upload new image to server
			String newImageName = getSaltString().concat(file.getOriginalFilename());
			try {
				Files.copy(file.getInputStream(), this.root.resolve(newImageName));
			} catch (Exception e) {
				throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
			}
			/// END STEP 2
			
			
			/// STEP 3 : Update provider in database
			provider.setName(name);
			provider.setEmail(email);
			provider.setAddress(address);
			provider.setNomImage(newImageName);
			return providerRepository.save(provider);
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}
		
	
	*/
	
	
	

	/*@DeleteMapping("/{providerId}")
	public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {
		return providerRepository.findById(providerId).map(provider -> {
			providerRepository.delete(provider);

			//////// delete de l'image du serveur
			try {
				File f = new File(this.root + "/" + provider.getNomImage()); // file to be delete
				if (f.delete()) // returns Boolean value
				{
					System.out.println(f.getName() + " deleted"); // getting and printing the file name
				} else {
					System.out.println("failed");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			///////

			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}
	*/

	

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

}

/*
public class ProviderController {
	
	private final Path root = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static/uploads");
	@Autowired
	private ProviderRepository providerRepository;

	@GetMapping("/list")
	public List<Provider> getAllProviders() {
		return (List<Provider>) providerRepository.findAll();
	}

	@PostMapping("/add")
	public Provider createProvider(@Valid @RequestBody Provider provider) {
		return providerRepository.save(provider);
	}

	@PutMapping("/{providerId}")
	public Provider updateProvider(@PathVariable Long providerId, @Valid @RequestBody Provider providerRequest) {
		return providerRepository.findById(providerId).map(provider -> {
			provider.setName(providerRequest.getName());
			provider.setEmail(providerRequest.getEmail());
			provider.setAddress(providerRequest.getAddress());
			return providerRepository.save(provider);
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}

	@DeleteMapping("/{providerId}")
	public ResponseEntity<?> deleteProvider(@PathVariable Long providerId) {
		return providerRepository.findById(providerId).map(provider -> {
			providerRepository.delete(provider);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new IllegalArgumentException("ProviderId " + providerId + " not found"));
	}

	@GetMapping("/{providerId}")
	public Provider getProvider(@PathVariable Long providerId) {
		Optional<Provider> p = providerRepository.findById(providerId);
		return p.get();
	}
}*/