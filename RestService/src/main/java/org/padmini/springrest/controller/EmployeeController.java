package org.padmini.springrest.controller;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.padmini.springrest.model.Employee;
import org.padmini.springrest.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	
	@Autowired
	 EmployeeServiceImpl empServiceImpl;
	
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public String test()
	{
		return "Its working";
	}
	
	@RequestMapping(value="/getAllEmployees",method=RequestMethod.GET)
			//,produces=MediaType.APPLICATION_XML)
	public List<Employee> getAllEmployees() {
		return empServiceImpl.getAllEmployees();
	}
	
	@RequestMapping(value="/getEmployee/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_XML)
	public Employee getEmployeeById(@PathVariable Integer id) {
		//System.out.println("Entered id is: "+id);
		return empServiceImpl.getEmployeeById(id);
	}
	
	@RequestMapping(value="/addEmployee",method=RequestMethod.POST,produces=MediaType.APPLICATION_XML)
	public Employee addEmployee(@RequestBody Employee employee) {
		return empServiceImpl.addEmployee(employee);
	}
	
	@RequestMapping(value="/updateEmployee/{id}",method=RequestMethod.PUT,produces=MediaType.APPLICATION_XML)
	public Employee updateEmployee(@PathVariable Integer id,@RequestBody Employee employee) {
		return empServiceImpl.updateEmployee(id, employee);
	}
	
	@RequestMapping(value="/deleteEmployee/{id}",method=RequestMethod.DELETE)
	public String deleteEmployee(@PathVariable int id) {
		 empServiceImpl.deleteEmployee(id);
		 return "Deleted Employee with id: "+id;
	}
}