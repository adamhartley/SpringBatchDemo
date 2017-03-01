package com.springbatch.demo.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import com.springbatch.demo.callbacks.HeaderCallbackCsv;
import com.springbatch.demo.domain.Person;
import com.springbatch.demo.listeners.JobNotificationListener;
import com.springbatch.demo.listeners.StepNotificationListener;
import com.springbatch.demo.mappers.PersonRowMapper;
import com.springbatch.demo.processors.PersonItemProcessor;

/**
 * @author Adam Hartley
 */
@Configuration
@EnableBatchProcessing
@PropertySource(value = "classpath:application.properties") // required for XML job config
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource demoDataSource;

    @Autowired
    public PersonRowMapper rowMapper;

    @Autowired
    public StepNotificationListener stepNotificationListener;

    @Autowired
    public HeaderCallbackCsv headerCallbackCSV;

    @Value("${output.file}")
    Resource resource;

    private static Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);
    private static final String SQL_FETCH_ALL = "SELECT first_name, last_name FROM PEOPLE";

    @Bean
    public ItemReader<Person> jdbcItemReader() {
        logger.info("ENTER jdbcItemReader()");
        JdbcCursorItemReader jdbcCursorItemReader = new JdbcCursorItemReader();
        jdbcCursorItemReader.setDataSource(demoDataSource);
        jdbcCursorItemReader.setSql(SQL_FETCH_ALL);
        jdbcCursorItemReader.setRowMapper(rowMapper);
        return jdbcCursorItemReader;
    }

    @Bean
    public ItemWriter<Person> itemWriter() {
        logger.info("ENTER itemWriter()");
        FlatFileItemWriter flatFileItemWriter = new FlatFileItemWriter();
        flatFileItemWriter.setResource(resource);
        // Line aggregator config
        DelimitedLineAggregator<Person> delimitedLineAggregator = new DelimitedLineAggregator<>();
        delimitedLineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Person> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] { "firstName", "lastName" }); // Need to match your bean's fields
        delimitedLineAggregator.setFieldExtractor(fieldExtractor);

        flatFileItemWriter.setLineAggregator(delimitedLineAggregator);
        // CSV header callback
        flatFileItemWriter.setHeaderCallback(headerCallbackCSV);

        return flatFileItemWriter;
    }

    /*
     * PROCESSOR Configuration
     */
    @Bean
    public PersonItemProcessor itemProcessor() { return new PersonItemProcessor(); }

    /*
     * JOB Configuration
     */
    @Bean
    public Job exportUserJob(JobNotificationListener listener) {
        return jobBuilderFactory.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    /*
     * STEP Configuration
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .listener(stepNotificationListener)
                .<Person, Person>chunk(10) // Commit interval set via chunkSize param
                .reader(jdbcItemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }
}
