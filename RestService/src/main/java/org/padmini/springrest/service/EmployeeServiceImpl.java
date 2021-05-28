package org.padmini.springrest.service;

import java.util.ArrayList;
import java.util.List;

import org.padmini.springrest.model.Employee;
import org.padmini.springrest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		Employee emp1=new Employee(1,"Ram","A2",30000);
		List<Employee> employeeList=new ArrayList<Employee>();
		employeeRepository.findAll().forEach(employeeList::add);
		employeeList.add(emp1);
		//System.out.println(emp1.getId());
		return employeeList;
	}

	@Override
	public Employee getEmployeeById(int id) {
		System.out.println("id in service is: "+id);
		Employee emp= employeeRepository.findById(id).get();
		return emp;
	}
	

	@Override
	public Employee addEmployee(Employee employee) {
		employeeRepository.save(employee);
		return employee;
	}

	@Override
	public Employee updateEmployee(int id, Employee employee) {
		Employee emp=employeeRepository.findById(id).get();
		emp.setName(employee.getName());
		emp.setDesignation(employee.getDesignation());
		emp.setSalary(employee.getSalary());
		return employeeRepository.save(emp);
	}

	@Override
	public String deleteEmployee(int id) {
		Employee emp=employeeRepository.findById(id).get();
		employeeRepository.delete(emp);
		return "Deleted Employee with id: "+id;
	}
}
