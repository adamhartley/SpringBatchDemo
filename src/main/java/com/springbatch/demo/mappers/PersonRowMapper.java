package com.springbatch.demo.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.springbatch.demo.domain.Person;

/**
 * @author Adam Hartley
 */
@Component
public class PersonRowMapper implements RowMapper<Person> {

    private static Logger logger = LoggerFactory.getLogger(PersonRowMapper.class);

    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        logger.info("ENTER mapRow(resultSet, i)");
        Person person = new Person();
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        return person;
    }
}
