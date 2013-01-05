package com.alexbool.oauth.test;

import javax.sql.DataSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Abstract class for DAOs placed in Spring context.
 * 
 * @author Alexander Bulaev
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:context/jdbc-context.xml",
        "classpath:context/properties-context.xml" })
public abstract class AbstractJdbcDaoTest extends JdbcDaoSupport {

    @Autowired
    public void initDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }
}
