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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	 @Bean
	    public Job job(JobBuilderFactory jobBuilderFactory,
	                   StepBuilderFactory stepBuilderFactory,
	                   ItemReader<Employee> itemReader,
	                   ItemProcessor<Employee,Employee> itemProcessor,
	                   ItemWriter<Employee> itemWriter
	    ) {

	        Step step = stepBuilderFactory.get("LoadXmlFile")
	                .<Employee, Employee>chunk(10)
	                .reader(itemReader)
	                .processor(itemProcessor)
	                .writer(itemWriter)
	                .build();


	        return jobBuilderFactory.get("LoadEmployeeDetails")
	                .incrementer(new RunIdIncrementer())
	                .start(step)
	                .build();
	    }
	 
	 @Bean
	 public StaxEventItemReader<Employee> reader(){
	  StaxEventItemReader<Employee> reader = new StaxEventItemReader<Employee>();
	  reader.setResource(new ClassPathResource("employee.xml"));
	  reader.setFragmentRootElementName("employee");
	
	  Map<String, String> aliases = new HashMap<String, String>();
	  aliases.put("employee", "org.padmini.springbatch.model.Employee");
	  
	  XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
	  xStreamMarshaller.setAliases(aliases);
	  
	  reader.setUnmarshaller(xStreamMarshaller);
	  
	  return reader;
	 }
}
