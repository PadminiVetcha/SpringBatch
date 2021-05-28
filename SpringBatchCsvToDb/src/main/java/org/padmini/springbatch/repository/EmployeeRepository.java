package org.padmini.springbatch.repository;

import org.padmini.springbatch.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
