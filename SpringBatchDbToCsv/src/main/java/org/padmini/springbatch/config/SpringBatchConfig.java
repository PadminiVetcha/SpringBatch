package org.padmini.springbatch.config;

import java.util.HashMap;

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
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;

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
		 reader.setSort(new HashMap<String, Sort.Direction>() {
		 {
			 put("_id", Direction.DESC);
		 }
		});
		 reader.setTargetType(Employee.class);
		 reader.setQuery("{}");
		 return reader;
	 }
	
	 @Bean
	 public FlatFileItemWriter<Employee> writer()
	 {
		 FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<Employee>();
		 writer.setResource(new FileSystemResource("csv/employee.csv"));
		 writer.setLineAggregator(new DelimitedLineAggregator<Employee>() {{
			 setDelimiter(",");
			 setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {{
				 setNames(new String[] { "id", "name","designation","salary" });
			 }});
		 }});
	  return writer;
	 }
}