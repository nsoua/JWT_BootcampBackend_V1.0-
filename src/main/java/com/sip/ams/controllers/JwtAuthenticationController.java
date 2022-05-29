/*package com.sip.ams.controllers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.config.JwtTokenUtil;
import com.sip.ams.models.JwtRequest;
import com.sip.ams.models.JwtResponse;

@RestController
@CrossOrigin(origins = "*")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = { "/auth" }, method = RequestMethod.POST)

	public JwtResponse generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		// Spring security : elle vérifie si l'user(username, password) existe ou pas

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		// génération du Token

		final String token = jwtTokenUtil.generateToken(userDetails);
		// je suppose que le user a les info suivantes:
		// return ResponseEntity.ok(new JwtResponse(token));

		//return ResponseEntity.ok(new JwtResponse(token, "Med Amine Mezghich", "Admin"));
		
//		return new JwtResponse(token, "Med Amine Mezghich", "Admin");
		
		return new JwtResponse(token, "eeeeeee", "Admin");
	}

	private void authenticate(String username, String password) throws Exception {
		try {

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}

		catch (Exception e) {
			// throw new Exception("INVALID_CREDENTIALS", e);
			e.printStackTrace();
		}
	}
}
*/