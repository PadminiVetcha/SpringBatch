package org.padmini.springrest.service;
import java.util.List;

import org.padmini.springrest.model.Employee;

public interface EmployeeService 
{
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id);
	public Employee addEmployee(Employee employee);
	public Employee updateEmployee(int id,Employee employee);
	public String deleteEmployee(int id);
	
}
