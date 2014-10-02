package com.century.logregator.dao;

import com.century.logregator.TestConfig;
import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.ReplacementTable;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

import javax.naming.Context;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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
        InputStream dataset = DbUnitTest.class.getResourceAsStream("dataset.xml");
        return new FlatXmlDataSetBuilder().build(dataset);
    }

    protected static IDataSet readDataSet(String fileName) throws Exception {
        InputStream dataset = DbUnitTest.class.getResourceAsStream(fileName);
        return new FlatXmlDataSetBuilder().build(dataset);
    }

    protected  IDataSet fromStream(String fileName) throws Exception{
        InputStream stream = getClass().getResourceAsStream(fileName);
        return new FlatXmlDataSetBuilder().build(stream);
    }

    protected IDataSet getExpected() throws Exception {
        return readDataSet("expected.xml");
    }

    protected IDataSet getActual() throws Exception {
        return iDatabaseTester.getConnection().createDataSet(new String[]{"mvn_tag"});
    }

    protected void assertTablesEqual(String tableName) throws Exception{
        IDataSet expected = getExpected();
        IDataSet actual = getActual();
        Assertion.assertEquals(expected.getTable(tableName), actual.getTable(tableName));
    }

    protected void assertTablesEqualWithReplacement(String tableName, Map<String, Object> reps) throws Exception{
        IDataSet actual = getActual();
        ReplacementTable expected = new ReplacementTable(getExpected().getTable(tableName));
        for (Map.Entry<String, Object> entry : reps.entrySet()) {
            expected.addReplacementObject(entry.getKey(), entry.getValue());
        }
        Assertion.assertEquals(expected, actual.getTable(tableName));
    }



    public  void a(){
        ExpressionParser expressionParser = new SpelExpressionParser();
//        expressionParser.parseExpression();

        new FlatXmlDataSetBuilder();
    }

}
