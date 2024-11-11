package org.example.database;

public class DatabaseConnectionFactory
{
    private static final String SCHEMA = "library";
    private static final String TEST_SCHEMA = "testlibrary";
    public static JDBConnectionWrapper getConnectionWrapper(boolean test) {
        if(test) {
            return new JDBConnectionWrapper(TEST_SCHEMA);
        }
        else {
            return new JDBConnectionWrapper(SCHEMA);
        }
    }
}
