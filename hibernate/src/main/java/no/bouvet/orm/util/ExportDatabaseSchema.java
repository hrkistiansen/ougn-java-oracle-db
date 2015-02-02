package no.bouvet.orm.util;

import java.io.File;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Convenience class for exporting the database schema
 * 
 * @author havard
 * 
 */
public class ExportDatabaseSchema {
	private static final String FILENAME = System.getProperty("user.home") + File.separator
			+ "hibernateSchemaExport.sql";

	public static void main(String[] args) {
		Configuration configuration = HibernateUtil.getConfiguration();
		// configuration.setProperty(Environment.DIALECT,
		// Oracle10gDialect.class.getName());
		SchemaExport exporter = new SchemaExport(configuration);
		exporter.setHaltOnError(true);
		exporter.setOutputFile(FILENAME);
		exporter.setFormat(true);
		exporter.setDelimiter(";");
		exporter.execute(false, false, false, true);
	}

}
