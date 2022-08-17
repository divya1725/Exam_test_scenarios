import com.eviware.soapui.impl.WsdlInterfaceFactory;
class TestEnvironment {
    public static void addEndPoints( context ){
        def project = context.testCase.testSuite.project;
        int envCount = project.getEnvironmentCount();
        for ( int i = 0; i <envCount ; i++ ){
            def env = project.getEnvironmentAt( i ).getName();
            add( env , context );
        }
    }
    public static void add( userReqEnvName , context ){
        def project = context.testCase.testSuite.project;
        String apiCount = project.getInterfaceCount();
        boolean envExists =  checkIfEnvExists(userReqEnvName, project);
        if (envExists.equals(false)){ userReqEnvName = createNewTestEnvironment( userReqEnvName , apiCount , project ); }
        if (apiCount != '0'){ setEndPoint( userReqEnvName , apiCount , project ); }
        setJdbcConnection( userReqEnvName , project );
    }
    public static boolean checkIfEnvExists(String userReqEnvName, project){
        def getEnv = project.getEnvironmentByName(userReqEnvName);
        if (getEnv != null && userReqEnvName.equals(getEnv.getName())){
            return true;
        }
        return false;
    }
    public static String createNewTestEnvironment( env , apiCount , project ){
        project.addNewEnvironment( env );
        project.setActiveEnvironment( env );
        def activeEnv = project.getActiveEnvironment().getName();
        if(apiCount != '0'){
            for ( int i = 0; i < apiCount ; i++ ){
                addWsdlInterface(activeEnv, i, project);
            }
        }
        return (project.getEnvironmentByName(activeEnv).getName());
    }
    public static void addWsdlInterface(activeEnv, counter, project){
        def wsdl = project.getInterfaceAt( counter );
        URL url = new URL(wsdl.getDefinition());
        def urlDefinition = url.toString();
        def wsdlName = wsdl.getName().toString();
        def service = activeEnv.addNewService(wsdlName,com.eviware.soapui.config.ServiceConfig.Type.Enum.forString("SOAP"));
        def serviceConfig = service.getConfig();
        def endpointConfig = serviceConfig.addNewEndpoint();
        endpointConfig.setStringValue( urlDefinition )
        service.buildEndpoint( endpointConfig );
        def isCopied = true;
        activeEnv.populateService( service , isCopied );
        activeEnv.release();
    }
    public static void setEndPoint( env , apiCount , project ){
        String getEnv = project.getEnvironmentByName(env);
        String delimitor = "PortTypeSoap11";
        String envDbContainer= getEnv.getDatabaseConnectionContainer();
        for ( int i = 0; i < apiCount ; i++ ){
            String wsdlInterface = project.getInterfaceAt( i ).getName();
            def wsdlService = getEnv.getService(wsdlInterface, com.eviware.soapui.config.ServiceConfig.Type.Enum.forString("SOAP"));
            String interfaceName = getInterfaceName(wsdlInterface, wsdlService);
            String service = wsdlInterface.split(delimitor).toString();
            service = service.replaceAll( "[^a-zA-Z0-9_]","" );
            String serviceIP = getServiceIP(getEnv, interfaceName);
            String setupString = getUrlString(serviceIP, interfaceName, service);
            if(setupString != null){
                def urlConf = wsdlService.getEndpoint().getConfig();
                if ( urlConf != null ){ urlConf.setStringValue( setupString ); }
            }
        }
    }
    public static String getInterfaceName(String interfaces){
        if (interfaces != null){
            if((interfaces.contains("PINS")||interfaces.contains("PAYS"))){ return "pin"; }
            if((interfaces.contains("PBDS"))){ return "pbd"; }
            if((interfaces.contains("IAMS"))){ return "eam"; }
            if((interfaces.contains("CAUC"))){ return "ftsBatch"; }
            if((interfaces.contains("PCAS"))){ return "ftsOnline"; }
            if((interfaces.contains("AGRS")||interfaces.contains("DEPS")||interfaces.contains("CUSS")||interfaces.contains("CCOS"))){ return "core"; }
        }
        return null;
    }
    public static String getServiceIP(String env, String interfaceName){
        switch (interfaceName){
            case "pin": return (Environment.getEOSIP(env));
            case "pbd": return (Environment.getPbd(env));
            case "eam": return (Environment.getEam(env));
            case "ftsBatch": return (Environment.getFtsEBS(env));
            case "ftsOnline": return (Environment.getFtsEOS(env));
            case "core": return (Environment.getCore(env));
            default: return null;
        }
    }
    public static String getUrlString(String serviceIP, String interfaceName, String service){
        switch (interfaceName){
            case "pin": return ("http://"+serviceIP+"/pin/services/"+service+"?wsdl");
            case "pbd": return ("http://"+serviceIP+"/pbdsrv/services/"+service+"?wsdl");
            case "eam": return ("http://"+serviceIP+"/eam-server/services/"+service+"?wsdl");
            case "ftsBatch": return ("http://"+serviceIP+"/fts/services/"+service+"?wsdl");
            case "ftsOnline": return ("http://"+serviceIP+"/fts/services/"+service+"?wsdl");
            case "core": return ("http://"+serviceIP+"/corewsdpgensrv/services/"+service+"?wsdl");
            default: return null;
        }
    }
    public static void setJdbcConnection( env , project ){
        def prjDbContainer = project.getDatabaseConnectionContainer();
        def jdbcConnectionCount = prjDbContainer.getResourceCount();
        if ( jdbcConnectionCount == 0 ){
            jdbcConnectionCount = writeConnection(env, prjDbContainer, "PWHDATA");
        }
        jdbcConnectionCount = prjDbContainer.getResourceCount();
        if ( jdbcConnectionCount != 0 ){
            addJdbcConnectionToEnvironment( env , project );
        }
    }
    public static void addJdbcConnectionToEnvironment( env , project ){
        def getEnv = project.getEnvironmentByName( env );
        def envDbContainer= getEnv.getDatabaseConnectionContainer();
        def envJdbcCount = envDbContainer.getResourceCount();
        def connection;
        if ( envJdbcCount == 0 ){
            envJdbcCount = writeConnection(env, envDbContainer, "PWHDATA");
        }else{
            for ( int i = 0 ; i < envJdbcCount ; i++ ){
                connection = envDbContainer.getResourceAt( i )
                def jdbcName = connection.getName();
                if ( jdbcName.contains ( "PWHDATA" )){
                    writeConnectionDrivers(env, connection);
                }else{
                    writeConnection(env, connection,"PWHDATA")
                }
            }
        }
    }
    public static void writeConnection(env, container, data){
        def connection = container.addResource( data );
        writeConnectionDrivers(env, connection);
    }
    public static void writeConnectionDrivers(env, connection){
        String validEnvName = Environment.getEnvironmentName(env);
        def pwhConnectionString = Environment.getExaDataWithHost(validEnvName);
        connection.setDriver( "oracle.jdbc.driver.OracleDriver" );
        connection.setConnectionString(pwhConnectionString);
    }
    public static void removeAll( context ){
        def project = context.testCase.testSuite.project;
        int envCount = project.getEnvironmentCount();
        for ( int i = 0; i < envCount ; i++ )    {
            def env = project.getEnvironmentAt( 0 ).getName();
            remove(env , context )
        }
    }
    public static void remove( env , context ){
        def project = context.testCase.testSuite.project;
        def envName = project.getEnvironmentByName(env)
        if (envName != null){
            def activeEnv = project.getActiveEnvironment().getName();
            if (activeEnv == env)    {
                project.setActiveEnvironment('Default');
            }
            project.removeEnvironmentByName(env);
        }
    }
    public static void importAll( projectName , context ){
        def project = context.testCase.testSuite.project
        importWsdl( projectName, "authoriz" , context );
        importWsdl( projectName, "standingorder" , context );
        importWsdl( projectName, "statusupdate" , context );
        importWsdl( projectName, "predefinedcreditor" , context );
        importWsdl( projectName, "search" , context );
        importWsdl( projectName, "paymentcreate" , context );
        importWsdl( projectName, "util" , context );
        importWsdl( projectName, "fts_CAUC" , context );
        importWsdl( projectName, "fts_PCAS" , context );
        importWsdl( projectName, "pbds_SKKO" , context );
        importWsdl( projectName, "pbds_BankStandards" , context );
        importWsdl( projectName, "pbds_PaymentParameter" , context );
        importWsdl( projectName, "DEPSAccount" , context );
        importWsdl( projectName, "AGRSAgreement" , context );
        importWsdl( projectName, "CUSSCustomer" , context );
        importWsdl( projectName, "CCOSCCPermissionService" , context );
        importWsdl( projectName, "eam" , context );
    }
    public static void importWsdl( String projectName, String wsdl, context ){
        def workspace = context.testCase.testSuite.project.workspace
        def setProject = workspace.getProjectByName(projectName);
        if ( setProject != null ){
            def wsdlName = findWsdlName(wsdl);
            if ( wsdlName != null ){
                def wsdlExists = verifyExistance( setProject , wsdlName );
                if (wsdlExists){
                    def wsdlUrl = findWsdlUrl( wsdlName );
                    if (wsdlUrl != null){
                        if (WsdlInterfaceFactory.importWsdl( setProject , wsdlUrl , true))    {
                            println "Adding Service "+wsdlName+" is successful"
                        }
                    }
                }
            }
        }
    }
    public static String findWsdlUrl( String wsdlName ){
        String wsdl = getInterfaceName(wsdlName);
        return (getServiceIP(wsdlName));
    }
    public static String findWsdlName(String wsdl ){
        wsdl = wsdl.toLowerCase();
        if (wsdl.contains( "authoriz" ) ) { return('PAYSPaymentAuthorization_V1_2');}
        if (wsdl.contains( "standingorder")){ return('PINSStandingOrder_V2_0Service'); }
        if (wsdl.contains( "statusupdate")){ return('PINSPaymentStatusUpdate_V1_5'); }
        if (wsdl.contains( "predefinedcreditor")){ return('PINSPaymentPredefinedCreditor_V1_1'); }
        if (wsdl.contains( "search")){ return('PINSPaymentSearch_V1_9'); }
        if (wsdl.contains( "paymentcreate") || wsdl.contains( "create")){ return('PINSPayment_V1_9'); }
        if (wsdl.contains( "forecastsearch") || wsdl.contains( "forecast")){ return('PINSPaymentAccountForecastSearch_V1_1'); }
        if (wsdl.contains( "util")){ return('PINSPaymentUtil_V1_2'); }
        if (wsdl.contains( "eam")){ return('IAMSUserInfoAndCrossBankAuthorizationReadService_V1'); }
        if (wsdl.contains( "pbds") && wsdl.contains( "skko")){ return('PBDSSKKORegisterNumber_V1_0Service'); }
        if (wsdl.contains( "pbds") && wsdl.contains( "bankstandards")){ return('PBDSBankStandards_V1_0Service'); }
        if (wsdl.contains( "pbds") && wsdl.contains( "paymentparameter")){ return('PBDSPaymentParameters_V4_5Service'); }
        if (wsdl.contains( "deps") && wsdl.contains( "depsaccount")){ return('DEPSAccountService_V17_1'); }
        if (wsdl.contains( "agrs") && wsdl.contains( "agrsagreement")){ return('AGRSAgreementService_V19_2'); }
        if (wsdl.contains( "cuss") && wsdl.contains( "cusscustomer")){ return('CUSSCustomerService_V20_1'); }
        if (wsdl.contains( "ccos") && wsdl.contains( "ccosccpermission")){ return('CCOSCCPermissionService_V20_2'); }
        if (wsdl.contains( "fts") && wsdl.contains( "cauc")){ return('CAUCJobService'); }
        if (wsdl.contains( "fts") && wsdl.contains( "pcas") && wsdl.contains( "pcasreservationcreate") ){ return('PCASReservationCreate_V1_1Service'); }
        if (wsdl.contains( "fts") && wsdl.contains( "pcas") && wsdl.contains( "pcascoveragecontrolexecute")){ 
            return('PCASCoverageControlExecute_V1_0Service');
        }
        return null;
    }
    public static boolean verifyExistance(String setProject, String wsdlName) {
        def delimitor = "PortTypeSoap11";
        def apiCount = setProject.getInterfaceCount();
        for ( int i = 0; i < apiCount ; i++ ){
            def wsdlInterface = setProject.getInterfaceAt( i ).getName();
            def service = (wsdlInterface.split(delimitor).toString()).replaceAll( "[^a-zA-Z0-9_]","" );
            if ( service.contains ( wsdlName )){ return false; }
        }
        return true;
    }
}