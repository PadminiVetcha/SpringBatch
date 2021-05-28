package org.padmini.springrest.repository;
import org.padmini.springrest.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository  extends CrudRepository<Employee, Integer>
{

}
