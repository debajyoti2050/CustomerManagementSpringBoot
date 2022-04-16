package com.project.customermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.customermanagement.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
//	@Modifying
//	@Transactional
//	@Query("delete from customer")
//	void truncate();

	@Query(value = "SELECT * FROM Customer u WHERE u.email like %:email%", nativeQuery = true)
	Optional<Customer> findUserByEmail(@Param("email") String email);

//	@Query(value = "SELECT * FROM Customer u WHERE u.mobile like mobile", nativeQuery = true)
//	Optional<Customer> findByMobile(@Param("mobile") int mobile);
}
