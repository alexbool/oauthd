package com.alexbool.util.jdbc;

import java.io.IOException;
import java.util.Scanner;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class SchemaCreator extends JdbcDaoSupport {

    private static final Logger logger = LoggerFactory.getLogger(SchemaCreator.class);

    private final Resource schemaScriptFile;

    public SchemaCreator(Resource schemaScriptFile, DataSource dataSource) {
        this.setDataSource(dataSource);
        this.schemaScriptFile = schemaScriptFile;
    }

    public void createSchema() throws IOException {
        String sql = null;
        try (Scanner s = new Scanner(schemaScriptFile.getInputStream(), "UTF-8")) {
            s.useDelimiter("\\A");
            sql = s.hasNext() ? s.next() : null;
        }
        if (sql == null) {
            logger.info("No SQL to execute");
            return;
        }
        try {
            getJdbcTemplate().execute(sql);
        }
        catch (DataAccessException ex) {
            logger.info("Create schema threw {}, assuming tables are already created", ex.getClass().getName());
        }
    }
}
