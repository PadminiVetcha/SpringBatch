package org.padmini.springbatch.config;

import java.util.HashMap;
import java.util.Map;

import org.padmini.springbatch.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	  @Autowired
	  private MongoTemplate mongoTemplate;
	  
	  @Bean
	    public Job job(JobBuilderFactory jobBuilderFactory,
	                   StepBuilderFactory stepBuilderFactory,
	                   ItemReader<Employee> itemReader,
	                   ItemWriter<Employee> itemWriter
	    ) {

	        Step step = stepBuilderFactory.get("LoadDatabase")
	                .<Employee, Employee>chunk(10)
	                .reader(reader())
	                .writer(writer())
	                .build();


	        return jobBuilderFactory.get("LoadEmployeeDetails")
	                .incrementer(new RunIdIncrementer())
	                .start(step)
	                .build();
	    }
	  
	 @Bean
	 public MongoItemReader<Employee> reader() 
	 {
		 MongoItemReader<Employee> reader = new MongoItemReader<>();
		 reader.setTemplate(mongoTemplate);
		 reader.setSort(new HashMap<String, Sort.Direction>() 
		 {{
			 put("_id", Direction.DESC);
		 }});
		 reader.setTargetType(Employee.class);
		 reader.setQuery("{}");
		 return reader;
	 }

	 @Bean
	 public StaxEventItemWriter<Employee> writer() 
	 {
		 StaxEventItemWriter<Employee> writer = new StaxEventItemWriter<>();
		 writer.setResource(new FileSystemResource("xml/employee.xml"));
		 writer.setMarshaller(userUnmarshaller());
		 writer.setRootTagName("employees");
		 return writer;
	 }


	 @Bean
	 public XStreamMarshaller userUnmarshaller() 
	 {
		 XStreamMarshaller unMarshaller = new XStreamMarshaller();
		 Map<String, Class> aliases = new HashMap<String, Class>();
		 aliases.put("employee", Employee.class);
		 unMarshaller.setAliases(aliases);
		 return unMarshaller;
	 }
}
	 