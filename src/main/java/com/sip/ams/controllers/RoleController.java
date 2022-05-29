package com.sip.ams.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sip.ams.entities.Role;
import com.sip.ams.models.ERole;
import com.sip.ams.repositories.RoleRepository;


@RestController
@RequestMapping("/role/")
public class RoleController {
	
	private final RoleRepository roleRepository;
	
	@Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
	
	@GetMapping("list")
    public List<Role> listRoles(Model model) {
    	
		return (List<Role>) roleRepository.findAll();
    	
    }
    
    
    
    @PostMapping("add")
    public String addRole(@RequestParam("role") ERole role) {
        
        //System.out.println(role);
        Role r = new Role(role);
        Role rSaved = roleRepository.save(r);
        //System.out.println("role = "+ rSaved);
        return role + " Added";
    }


}
