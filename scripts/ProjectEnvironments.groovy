package actions

import com.eviware.soapui.model.environment.ServiceImpl
import com.eviware.soapui.model.environment.EndpointImpl

class ProjectEnvironments {

    public static def log = null
    public static def context = null

    public static void load(def testRunner,def context,  def log){
        this.log=log;
        this.context = context;
        def project = testRunner.getProject();
        def envActive = project.getActiveEnvironment()
        def envActiveName = envActive.getName();
        def allEnvList = project.getEnvironmentList();
        def constantEnv = "G-D4"
        def actualEnv = System.getenv().get("COMMAND_LINE"); // get actual environment from user input
        //actualEnv = "G-D7"
        log.info "Actual envirnment from user input is -- $actualEnv"

        if (isEnvironmentExists(allEnvList,actualEnv)){ // If env is already present just update the project prperies
            log.info "Environemnt $actualEnv alredy exists"
            project.setActiveEnvironment(actualEnv)
            loadProjectProperties(project,actualEnv);

        }
        else  // If env is not present, create new environment and update the project properties
        {
            log.info "Environemnt $actualEnv NOT exists"
            cloneExistingEnvironment( project,  actualEnv ,  constantEnv)

        }

        def currectEnvironmentServices = getCurrentEnvExistingSoapServices(project,actualEnv)
        addSeviceNameIfNotPresentInEnv(project,currectEnvironmentServices )

        updateAllServicesIfIPAddressPresent(project,actualEnv,constantEnv)
		setEnvironmentDataBaseDetails(project,actualEnv,constantEnv)
        //updateAllServices(project,actualEnv,constantEnv)
        //log.info "---->>>>>>>>>>>>>>>>END>>>>>>>>>>>>>>>>>>>>>"

        //log.info "getCurrentEnvExistingSoapServicesDefinition--" + getCurrentEnvExistingSoapServicesDefinition(project,actualEnv)
        //log.info printProjectProperties(project,actualEnv);

    }


//Check if suite has this environment already created
    public static boolean isEnvironmentExists(def allEnvListTemp,def envNameTemp){
        return allEnvListTemp.name.contains(envNameTemp)
    }

    public static boolean loadProjectProperties(def projectTemp, def actualEnvTemp){
        def flag = false;
        try{
            def file
            if (File.separator.equals("\\"))
                file= new File(projectTemp.getPath()).getParent().toString() + File.separator + "env" +  File.separator + "${actualEnvTemp}.json"
            else
                file= "/usr/local/SmartBear/project" + File.separator + "env" +  File.separator + "${actualEnvTemp}.json"

            def jsonSlurper = new groovy.json.JsonSlurper()
            def object = jsonSlurper.parse(new FileReader(new File(file)))

            object.environment.findAll{ it.'NAME' == "$actualEnvTemp" }[0].each {
                it -> projectTemp.setPropertyValue( it.key , it.value );
            }

            flag = true
        }catch(Exception ex){
            flag = false
        }

        return flag;

    }


    public static boolean cloneExistingEnvironment(def project, def actualEnv , def constantEnv){

        project.setActiveEnvironment(constantEnv)
        Thread.sleep(1000)
        project.getActiveEnvironment().clone(actualEnv)
        Thread.sleep(1000)
        project.setActiveEnvironment(actualEnv)
        Thread.sleep(1000)
        loadProjectProperties(project,actualEnv);
        log.info "New environment $actualEnv creted"
    }

//    public static boolean updateAllServices(def projectTemp,def actualEnvTemp,def constantEnvTemp){
//
//        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
//        def soapServCount = env.getSoapServiceCount()
//        for(int i = 0;i<soapServCount;i++){
//            def soapServ = env.getSoapServiceAt(i)
//            def endpointConf = soapServ.getEndpoint().getConfig()
//            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
//            def endPointStringValue = endpointConf.getStringValue()
//                    .replaceAll(constantEnvTemp,actualEnvTemp.replaceAll('_','-'))
//                    .replaceAll(constantEnvTemp.replaceAll('-','_'),actualEnvTemp.replaceAll('-','_'))
//			log.info "After Replace 0--endPointStringValue -- $endPointStringValue"
//            endpointConf.setStringValue(endPointStringValue)
//        }
//
//        def restServCount = env.getRestServiceCount()
//        for(int i = 0;i<restServCount;i++){
//            def restServ = env.getRestServiceAt(i)
//            def endpointConf = restServ.getEndpoint().getConfig()
//            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
//            def endPointStringValue = endpointConf.getStringValue()
//                    .replaceAll(constantEnvTemp,actualEnvTemp.replaceAll('-','_'))
//                    .replaceAll(constantEnvTemp.replaceAll('-','_'),actualEnvTemp.replaceAll('-','_'))
//			log.info "After Replace 0--endPointStringValue -- $endPointStringValue"
//            endpointConf.setStringValue(endPointStringValue)
//        }
//
//    }

    public static boolean updateAllServicesIfIPAddressPresent(projectTemp,actualEnvTemp,constantEnvTemp){

        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
        def soapServCount = env.getSoapServiceCount()
        for(int i = 0;i<soapServCount;i++){
            def soapServ = env.getSoapServiceAt(i)
            def endpointConf = soapServ.getEndpoint().getConfig()
            def isProjectStringPresent = endpointConf.getStringValue().contains("Project")
            if(endpointConf.getStringValue().contains("/pin/")){
                def ipddress = projectTemp.getPropertyValue(actualEnvTemp)
                def endPointStringValuePart2 = endpointConf.getStringValue().split('/pin/')[1]
                def endPointStringValue = "http://$ipddress/pin/$endPointStringValuePart2"
                endpointConf.setStringValue(endPointStringValue)
            }

        }

        def restServCount = env.getRestServiceCount()
        for(int i = 0;i<restServCount;i++){
            def restServ = env.getRestServiceAt(i)
            def endpointConf = restServ.getEndpoint().getConfig()
            def isProjectStringPresent = endpointConf.getStringValue().contains("Project")
            if(endpointConf.getStringValue().contains("/pin/")){
                def ipddress = projectTemp.getPropertyValue(actualEnvTemp)
                def endPointStringValuePart2 = endpointConf.getStringValue().split('/pin/')[1]
                def endPointStringValue = "http://$ipddress/pin/$endPointStringValuePart2"
                endpointConf.setStringValue(endPointStringValue)
            }
        }
    }


    public static def getCurrentEnvExistingSoapServices(def projectTemp, def actualEnvTemp){
        def serviceList = []
        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
        //for SOap services
        def soapServCount = env.getSoapServiceCount()
        for(int i = 0;i<soapServCount;i++){
            def soapServ = env.getSoapServiceAt(i)
            serviceList.add(soapServ.getName())
        }
        //For REST services
        def restServCount = env.getRestServiceCount()
        for(int i = 0;i<restServCount;i++){
            def restServ = env.getRestServiceAt(i)
            serviceList.add(restServ.getName())
        }
        return serviceList
    }

    public static void addSeviceNameIfNotPresentInEnv(def projectTemp, def currectEnvironmentServices){

        def newEnvObject= projectTemp.getActiveEnvironment()
        def interfaceList =  projectTemp.getInterfaceList()
        for(item in interfaceList ) {
            def enumType = (item.getType().equalsIgnoreCase("rest"))?"REST":"SOAP"
            def nameofService = item.name
            def serviceDefnition = item.getEndpoints()[0].toString()
            def indexNum = serviceDefnition.indexOf('/pin/')

            if(!currectEnvironmentServices.contains(nameofService)){
                ServiceImpl soapService = null
                if(enumType.equals("REST"))
                    soapService = newEnvObject.addNewService(nameofService, com.eviware.soapui.config.ServiceConfig.Type.REST)
                else
                    soapService = newEnvObject.addNewService(nameofService, com.eviware.soapui.config.ServiceConfig.Type.SOAP)


                def str = '<xml-fragment authProfile="No Authorization" username="" password="" domain="" incomingWss="" outgoingWss="" wssTimeToLive="" wssType="" proxyHost="" proxyPort="" proxyUsername="" proxyPassword="" xmlns:con="http://eviware.com/soapui/config">' + serviceDefnition+ '</xml-fragment>'
                com.eviware.soapui.config.EnvironmentEndpointConfig epConfig = com.eviware.soapui.config.EnvironmentEndpointConfig.Factory.parse(str);
                EndpointImpl epImpl = new EndpointImpl(epConfig, soapService);
                soapService.setEndpoint(epImpl);

            }
        }

    }

    public static def getCurrentEnvExistingSoapServicesDefinition(def projectTemp, def actualEnvTemp){
        def serviceList = []
        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
        //for SOap services
        def soapServCount = env.getSoapServiceCount()
        for(int i = 0;i<soapServCount;i++){
            def soapServ = env.getSoapServiceAt(i)
            def endpointConf = soapServ.getEndpoint().getConfig()
            serviceList.add(endpointConf.getStringValue())
        }
        //For REST services
        def restServCount = env.getRestServiceCount()
        for(int i = 0;i<restServCount;i++){
            def restServ = env.getRestServiceAt(i)
            def endpointConf = restServ.getEndpoint().getConfig()
            serviceList.add(endpointConf.getStringValue())
        }
        return serviceList
    }

    public static boolean printProjectProperties(def projectTemp, def actualEnvTemp ){
        def flag = false;
        try{
            def file
            if (File.separator.equals("\\"))
                file= new File(projectTemp.getPath()).getParent().toString() + File.separator + "env" +  File.separator + "${actualEnvTemp}.json"
            else
                file= "/usr/local/SmartBear/project" + File.separator + "env" +  File.separator + "${actualEnvTemp}.json"

            log.info "Path --$file"
            def jsonSlurper = new groovy.json.JsonSlurper()
            def object = jsonSlurper.parse(new FileReader(new File(file)))
            log.info object.environment
            object.environment.findAll{ it.'NAME' == "$actualEnvTemp" }[0].each {
                it -> log.info it.key + "|" + it.value
            }

            object.environment.findAll{ it.'NAME' == "$actualEnvTemp" }[0].each {
                it -> log.info "Proj prop--> ${it.key}-->"+ projectTemp.getPropertyValue(it.key)
            }

            flag = true
        }catch(Exception ex){
            flag = false
        }

        return flag;

    }
	
	public static boolean setEnvironmentDataBaseDetails(def projectTemp,def actualEnv,def constantEnv	){
	//There are many names given to PWH in different project suites, hence keep ll names in this list
		def PWHDBTempList = ["Database","PWH","PWHDB","PWHDATA"]
			for(dbItem in PWHDBTempList){
				log.info "dbItem--$dbItem"
				def dbConnection = projectTemp.activeEnvironment.databaseConnectionContainer.getResourceByName(dbItem)
				log.info "dbConnection-" + dbConnection
				if(dbConnection != null){
					log.info "set DB for $dbItem"
					dbConnection.setDriver(projectTemp.getPropertyValue('JDBCDriver'))
					dbConnection.setConnectionString(projectTemp.getPropertyValue('PWHDbString'))	
					Thread.sleep(500)		
					
				}	
			}
			
	//Same for RBS Bank
		def RBSBankTempList = ["RBS_BANK"]
			for(dbItem in RBSBankTempList){
				log.info "dbItem--$dbItem"
				def dbConnection = projectTemp.activeEnvironment.databaseConnectionContainer.getResourceByName(dbItem)
				log.info "dbConnection-" + dbConnection
				if(dbConnection != null){
					log.info "set DB for $dbItem"
					dbConnection.setDriver(projectTemp.getPropertyValue('JDBCDriver'))
					dbConnection.setConnectionString(projectTemp.getPropertyValue('RBSBank'))	
					Thread.sleep(500)		
					
				}	
			}
	//Same for RBS Forfall
		def RBSForfallTempList = ["RBS_FORFALL"]
			for(dbItem in RBSForfallTempList){
				log.info "dbItem--$dbItem"
				def dbConnection = projectTemp.activeEnvironment.databaseConnectionContainer.getResourceByName(dbItem)
				log.info "dbConnection-" + dbConnection
				if(dbConnection != null){
					log.info "set DB for $dbItem"
					dbConnection.setDriver(projectTemp.getPropertyValue('JDBCDriver'))
					dbConnection.setConnectionString(projectTemp.getPropertyValue('RBSForfall'))	
					Thread.sleep(500)		
					
				}	
			}
	//Same for RBS PAY
		def RBSPayTempList = ["RBS_PAY"]
			for(dbItem in RBSPayTempList){
				log.info "dbItem--$dbItem"
				def dbConnection = projectTemp.activeEnvironment.databaseConnectionContainer.getResourceByName(dbItem)
				log.info "dbConnection-" + dbConnection
				if(dbConnection != null){
					log.info "set DB for $dbItem"
					dbConnection.setDriver(projectTemp.getPropertyValue('JDBCDriver'))
					dbConnection.setConnectionString(projectTemp.getPropertyValue('RBSPay'))	
					Thread.sleep(500)		
					
				}	
			}
		projectTemp.setActiveEnvironment(constantEnv)
		Thread.sleep(500)					
		projectTemp.setActiveEnvironment(actualEnv)
	
	}


}
