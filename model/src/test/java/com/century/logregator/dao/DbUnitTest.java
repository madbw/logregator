package com.century.logregator.dao;

import com.century.logregator.TestConfig;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import java.io.File;
import java.io.InputStream;

@ContextConfiguration(classes = TestConfig.class)
public class DbUnitTest {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private IDatabaseTester iDatabaseTester;

    private static IDataSet dataSet;
    protected static DatabaseConnection databaseConnection = null;

    @BeforeClass
    public static void initDataSet() throws Exception {
        dataSet = readDataSet();
    }

    public static void closeConnection() throws Exception {
        databaseConnection.close();
    }

    @Before
    public void initDb() throws Exception {
        IDataSet iDataSet = readDataSet();
        cleanlyInsertDataSet(iDataSet);
        resetSequences();
    }

    private void resetSequences() {
        jdbcTemplate.update("alter sequence application_id_seq restart");
        jdbcTemplate.update("alter sequence application_environment_id_seq restart");
        jdbcTemplate.update("alter sequence application_properties_id_seq restart");
        jdbcTemplate.update("alter sequence jar_info_id_seq restart");
        jdbcTemplate.update("alter sequence mvn_tag_id_seq restart");
    }

    private void cleanlyInsertDataSet(IDataSet dataSet) throws Exception {
        if (databaseConnection == null) {
            databaseConnection = new DatabaseConnection(jdbcTemplate.getDataSource().getConnection());
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        }
        DatabaseOperation.TRUNCATE_TABLE.execute(databaseConnection, dataSet);
    }


    private static IDataSet readDataSet() throws Exception {
        InputStream inputStream = DbUnitTest.class.getResourceAsStream("dataset.xml");
        return new FlatXmlDataSetBuilder().build(inputStream);
    }

    protected static IDataSet readDataSet(String fileName) throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/com/century/logregator/dao/" + fileName));
    }

    protected static IDataSet readDataSet(InputStream inputStream) throws Exception{
        return new FlatXmlDataSetBuilder().build(inputStream);
    }

    protected IDataSet fromStream(String fileName) throws Exception {
        InputStream stream = getClass().getResourceAsStream(fileName);
        return new FlatXmlDataSetBuilder().build(stream);
    }

    public IDataSet getActual(String table) throws Exception {
        return iDatabaseTester.getConnection().createDataSet(new String[]{table});
    }

    protected void assertTablesEqual(String tableName, IDataSet expected, IDataSet actual) throws Exception {
        ITable expectedTable = new SortedTable(expected.getTable(tableName));
        ITable actualTable = new SortedTable(actual.getTable(tableName));
        Assertion.assertEquals(expectedTable, actualTable);
    }
}
