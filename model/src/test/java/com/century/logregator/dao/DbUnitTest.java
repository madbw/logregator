package com.century.logregator.dao;

import com.century.logregator.TestConfig;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
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
    }

    private void cleanlyInsertDataSet(IDataSet dataSet) throws Exception {
        if (databaseConnection == null) {
            databaseConnection = new DatabaseConnection(jdbcTemplate.getDataSource().getConnection());
            databaseConnection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
        }
        DatabaseOperation.CLEAN_INSERT.execute(databaseConnection, dataSet);
    }


    private static IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("model/src/test/resources/com/century/logregator/dao/dataset.xml"));
    }

    protected static IDataSet readDataSet(String fileName) throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("model/src/test/resources/com/century/logregator/dao/" + fileName));
    }

    protected IDataSet fromStream(String fileName) throws Exception {
        InputStream stream = getClass().getResourceAsStream(fileName);
        return new FlatXmlDataSetBuilder().build(stream);
    }

    protected IDataSet getExpected() throws Exception {
        return readDataSet("expected.xml");
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
