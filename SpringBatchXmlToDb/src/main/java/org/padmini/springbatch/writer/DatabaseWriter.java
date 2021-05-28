package org.padmini.springbatch.writer;

import java.util.List;

import org.padmini.springbatch.model.Employee;
import org.padmini.springbatch.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseWriter implements ItemWriter<Employee>{
	
	private EmployeeRepository employeeRepository;
	
	@Autowired
	public DatabaseWriter(EmployeeRepository employeeRepository)
	{
		this.employeeRepository=employeeRepository;
	}

	@Override
	public void write(List<? extends Employee> employee) throws Exception {
		employeeRepository.saveAll(employee);
		
	}

}
