package au.com.mason.expensemanager.dao;

import java.sql.Types;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class CustomPostgreSqlDialect extends PostgreSQL94Dialect {
	 
    public CustomPostgreSqlDialect() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
