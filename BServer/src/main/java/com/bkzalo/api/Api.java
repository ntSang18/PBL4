package com.bkzalo.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bkzalo.model.Model_Account;
import com.bkzalo.model.Model_Error;
import com.bkzalo.model.Model_User;
import com.bkzalo.service.ServiceUser;

@RestController
public class Api {

	@PostMapping("/Login")
	public ResponseEntity<Model_User> Login(@RequestBody Model_Account login) {
		try {
			Model_User user = ServiceUser.Instance().login(login);
			if(user != null) {
				return new ResponseEntity<Model_User>(user, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<Model_User>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Model_User>(HttpStatus.NOT_FOUND);
		}
	}
	 
	@PostMapping("/Register")
	public Model_Error Register(@RequestBody Model_Account data) {
		System.out.println(data.getUsername());
		System.out.println(data.getPassword());
		return ServiceUser.Instance().Register(data);
	}
}
