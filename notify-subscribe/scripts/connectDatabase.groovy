import java.util.*;
import groovy.sql.Sql;

class ConnectDatabase {
	public static void connectDatabase (String env, String orgid, String tableName, String currency) {
		// String env = Environment.getEnvironmentName(context.testCase.testSuite.project.getActiveEnvironment().getName());
		connect(env, orgid, tableName, currency);
	}
	public static void connect (String env, String orgid, String tableName, String currency) {
		String exaData =  Environment.getExaData(env);
		String exaDataWithHost =  Environment.getExaDataWithHost(env);
		String query = getQueryForTable(orgid, tableName, currency);
		String[] result = tryConnect(exaDataWithHost,query);
		println "Result is "+result[0];
		if (result[0] == null){
			println "result[0] is null";
		}else println "result[0] is "+result[0];
	}
	public static String getQueryForTable(String orgid, String tableName, String currency) {
		String select;
		String where;
		String query;
		switch (tableName) {
			case "V_AUTHORISATION"	:
					select	= "SELECT * "
					where	= "WHERE 1=1 AND ROWNUM = 1";
			case "V_AUTH_USERINFO"	:
			default :
					select	= "SELECT USERID, ACCOUNTNUMBER, CURRENCYCODE "
					where	= "WHERE BANKORGID='"+orgid+"' AND CURRENCYCODE='"+currency+"' AND ROWNUM = 1";
		}
		query = select + "FROM "+tableName+" " + where;
		println ("Query: "+query);
		return query;
	}
	public static def tryConnect(String exaDataWithHost, String query) {
		def result;
		println ("Connecting to .. "+exaDataWithHost);
		String connection = Sql.newInstance(exaDataWithHost, "pwhdata", "pwh", "oracle.jdbc.driver.OracleDriver");
		println ("Connected, Trying to Execute SQL Query");
    	result = connection.rows('select sysdate from dual');
		println "Trying to Close SQL connection";
		// connection.close()
		println ("Connection Closed, Returning NULL");
		return null;
	}
	public static setValues (String[] result, String tableName) {
		String userid;
		String accountNumber;
		String currency;
		if (tableName == "V_AUTH_USERINFO") {
			userid = result[0];
			accountNumber = result[1];
			currency = result[2];
		}else if (tableName == "V_AUTHORISATION"){

		}
	}
}