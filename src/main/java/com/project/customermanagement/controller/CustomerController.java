package com.project.customermanagement.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.project.customermanagement.exception.ResourceNotFoundException;
import com.project.customermanagement.model.Customer;
import com.project.customermanagement.repository.CustomerRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;

	// display all employee
	@GetMapping("/getAllCustomers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	// create employee
	@PostMapping("/createCustomer")
	public Customer createCustomer(@RequestBody Customer customer) {
		return customerRepository.save(customer);
	}

	// display Customer by id
	@GetMapping("/getCustomer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer does'nt exist with id: " + id));
		return ResponseEntity.ok(customer);
	}

	// update Customer by id
	@PutMapping("/updateCustomer/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable int id, @RequestBody Customer customerDetails) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer does'nt exist with id: " + id));
		customer.setFirstName(customerDetails.getFirstName());
		customer.setLastName(customerDetails.getLastName());
		customer.setMobile(customerDetails.getMobile());
		customer.setAddress(customerDetails.getAddress());
		customer.setEmail(customerDetails.getEmail());

		return ResponseEntity.ok(customerRepository.save(customer)); // returning the updated Customer
	}

	// delete customer
	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable int id) {
		Customer customer = customerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Customer does'nt exist with id: " + id));
		customerRepository.delete(customer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// delete all customer
	@DeleteMapping("/deleteAllCustomer")
	public ResponseEntity<Map<String, Boolean>> deleteAllCustomer() {
		customerRepository.deleteAll();
		Map<String, Boolean> response = new HashMap<>();
		response.put("Deleted All Customer", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	// find by email
	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<Customer> findByEmail(@PathVariable String email) {
		// Customer customer = customerRepository.findUserByEmail(email)
		Customer customer = ((Optional<Customer>) customerRepository.findUserByEmail(email))
				.orElseThrow(() -> new ResourceNotFoundException("Customer does'nt exist with Email : " + email));
		return ResponseEntity.ok(customer);

	}

	// error
	// find by mobile
//	@GetMapping("/findByMobile/{mobile}")
//	public ResponseEntity<Customer> findByMobile(@PathVariable int mobile) {
//		Customer customer = customerRepository.findByMobile(mobile)
//				.orElseThrow(() -> new ResourceNotFoundException("Customer does'nt exist with Mobile : " + mobile));
//		return ResponseEntity.ok(customer);
//	}

}
