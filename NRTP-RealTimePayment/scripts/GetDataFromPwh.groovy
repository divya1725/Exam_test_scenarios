import groovy.sql.Sql;

class GetDataFromPwh {
	public static void connectPwh(String orgid, String pwhTable, context) {
		orgid = Bank.getOrgId (orgid);
		String env = getEnvironment(context);
		String sqlQuery = getSqlQuery(pwhTable, orgid, context);
		println sqlQuery;
		String connectionString = Environment.getExaData(env);
		println connectionString;
		String execution = executeQuery(connectionString, sqlQuery, true);
	}
	public static String getEnvironment(context){
		return Environment.getEnvironmentName(context.testCase.testSuite.project.getActiveEnvironment().getName());
	}
	public static String getSqlQuery(String pwhTable, String orgid, context){
		String pwhTableName = getPwhTableName(pwhTable);
		String testCaseName = (context.getCurrentStep().getTestCase().getLabel()).toLowerCase();
		String marketType = getMarketType(testCaseName);
		println marketType;
		boolean isEmployee = checkIfEmployee(testCaseName);
		String payChannel = getPaymentChannel(marketType, isEmployee);
		boolean isSepa = checkIfSepa(testCaseName);
		String currency = getCurrency(orgid, isSepa);
		println currency;
		if (pwhTableName != null && marketType != null && orgid != null && currency != null){
			String query;
			println query;
			switch(pwhTableName) {
				case "v_auth_userinfo":
					query = "SELECT USERID, ACCOUNTNUMBER, CURRENCYCODE FROM "+pwhTableName+" WHERE BANKORGID='"+orgid+"' AND MARKETTYPE='"+marketType+"' AND CURRENCYCODE='"+currency+"' AND ROWNUM = 1";
					return query;
				case "v_authorisation":
					query = "";
					return query;
				default: return null;
			}
		}
		return null;
	}
	public static String getMarketType(String testCaseName){
		if (testCaseName.contains("bm")) {return "bm";}
		if (testCaseName.contains("pm")) {return "pm";}
		if (testCaseName.contains("emp")) {return "employee";}
		return null;
	}
	public static boolean checkIfEmployee(String testCaseName){
		if (testCaseName.contains("emp")) { return true; }
		if (testCaseName.contains("employee")) { return true; }
		if (testCaseName.contains("ITR")) { return true; }
		return false;
	}
	public static boolean checkIfSepa(String testCaseName){
		if (testCaseName.contains("sepa")) { return true; }
		return false;
	}
	public static String getCurrency(String orgid, boolean isSepa){
		if (isSepa || orgid.equals("35803100")){return "EUR"; }
		return "NOK";
	}
	public static String getPaymentChannel(String marketType, boolean isEmployee){
		if (isEmployee) {return "ITR";}
		if (marketType != null){
			switch(marketType) {
				case "pm": return "INT";
				case "bm": return "BES";
				case "employee": return "ITR";
				default: return null;
			}
		}
		return null;
	}
	public static String getPwhTableName(pwhTable){
		pwhTable = pwhTable.toLowerCase();
		if (pwhTable.contains("v_auth_userinfo")){return "v_auth_userinfo";}
		if (pwhTable.contains("v_authorisation")){return "v_authorisation";}
		return null;
	}
	public static String executeQuery(String connectionString, String sqlQuery, boolean status){
		if (status == true) {
			println connectionString;
			println sqlQuery;
			connection = Sql.newInstance(connectionString, "pwhdata", "pwh", "oracle.jdbc.driver.OracleDriver");
			resultSet = connection.rows(sqlQuery);
			connection.close();
		}
	}
	public static String handleResponse(String testCaseName, context){
		// if (!resultSet.isEmpty()) {
		// 	customer_1 = resultSet.USERID[0].toString().replaceAll("[^a-zA-Z0-9]+","")
		// 	account_1 = resultSet.ACCOUNTNUMBER[0].toString().replaceAll("[^a-zA-Z0-9]+","")
		// 	currency_1 = resultSet.CURRENCYCODE[0].toString().replaceAll("[^a-zA-Z0-9]+","")
		// 	userid_1 = customer_1;
		// 	if(marketType == 'BM' && customer_1.length() > 8){
		// 		userid_1 = (customer_1.substring(5) as int).toString().replaceAll("[^a-zA-Z0-9]+","")
		// 		writeToPropertyStep ("Properties", "AgreementId", userid_1);
		// 	}
		// 	writeToPropertyStep ("Properties", "customerid", customer_1);
		// 	writeToPropertyStep ("Properties", "userid", userid_1);
		// 	writeToPropertyStep ("Properties", "debitAccount", account_1);
		// 	writeToPropertyStep ("Properties", "DebitAcntccy", currency_1);
		// }
	}
	public static void writeToPropertyStep(String stepName, String item, String value, context){
		context.testCase.getTestStepByName(stepName).setPropertyValue(item,value);
	}
}