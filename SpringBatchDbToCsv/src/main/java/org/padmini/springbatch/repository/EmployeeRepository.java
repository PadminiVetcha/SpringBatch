package org.padmini.springbatch.repository;

import org.padmini.springbatch.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, Integer>{

}
