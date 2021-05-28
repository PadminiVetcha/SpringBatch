package org.padmini.springbatch.processor;
import org.padmini.springbatch.model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class EmployeeProcessor implements ItemProcessor<Employee,Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		System.out.println("Processing employee with id: "+employee.getId()+" and their details are: "+employee);
		return employee;
	}

}

