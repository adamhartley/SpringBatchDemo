package com.springbatch.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Main launcher class.
 *
 * NOTE: Use either the Spring Boot Launcher OR the XML Config Launcher.
 *       When running the Spring Boot Launcher (Java config), ensure that
 *       the XML job config is commented out between the BEGIN and END tags
 *       in the job-demo-config.xml file (vice-versa when running the
 *       job with XML config).
 * @author Adam Hartley
 */
@SpringBootApplication
@ImportResource("classpath:job-demo-config.xml")
public class Application {

    public static void main(String[] args) throws Exception {

    // tag:: Spring Boot Launcher
         SpringApplication.run(Application.class, args);
    // end:: Spring Boot Launcher

    // tag:: XML Launcher
//        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:job-demo-config.xml");
//
//        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
//        Job job = context.getBean(Job.class);
//
//        jobLauncher.run(
//                job,
//                new JobParametersBuilder()
//                .addLong("jobTimestamp", System.currentTimeMillis())
//                .toJobParameters()
//        );
    // end:: XML Launcher
    }
}
