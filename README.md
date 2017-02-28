# SpringBatchDemo
Spring Batch Demo project for Tech Session presentation.

There are two side-by-side batch job configurations in the SpringBatchDemo project:
  - SpringBoot/Java config
  - XML config

Only one configuration should be configured to run at a time, otherwise untintended results may occur.

### Spring Boot Config
To configure the batch job to run with Spring Boot config, ensure that the code between `Spring Boot Launcher` tags in the `Application.class` is uncommented:
```java
    // tag:: Spring Boot Launcher
         SpringApplication.run(Application.class, args);
    // end:: Spring Boot Launcher
```
Conversely, the code for the XML launcher, found between the `XML Launcher` tags in the `Application.class` should be commented out. Double check that the XML job configuration code found in the `job-demo-config.xml` file is commented out between the `BEGIN` and `END` tags.

### XML Config
Alternatively, to use the job configuration found in `job-demo-config.xml`, comment out the code between the `Spring Boot Launcher` tags in the  `Application.class`. The code found between the `XML Launcher` tags in `Application.class` should be uncommented:
```java
      // tag:: XML Launcher
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:job-demo-config.xml");

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);

        jobLauncher.run(
                job,
                new JobParametersBuilder()
                .addLong("jobTimestamp", System.currentTimeMillis())
                .toJobParameters()
        );
    // end:: XML Launcher
```
Finally, in the `job-demo-config.xml` file, uncomment the code between the `BEGIN` and `END` tags, as follows:
```xml
        <context:annotation-config/>
        <context:component-scan base-package="com.springbatch.demo"/>

        <batch:job id="exportUserJob" job-repository="jobRepository">
            <batch:listeners>
                <batch:listener ref="jobNotificationListener" />
            </batch:listeners>
            <batch:step id="step1">
                <batch:tasklet>
                    <batch:listeners>
                        <batch:listener ref="stepNotificationListener" />
                    </batch:listeners>
                    <batch:chunk reader="jdbcItemReader" writer="itemWriter" commit-interval="1" />
                </batch:tasklet>
            </batch:step>
        </batch:job>
```
