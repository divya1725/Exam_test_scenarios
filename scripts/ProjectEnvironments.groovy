package actions

import com.eviware.soapui.model.environment.ServiceImpl
import com.eviware.soapui.model.environment.EndpointImpl

class ProjectEnvironments {

    public static def log = null

    public static void load(def testRunner, def log){
        this.log=log;
        def project = testRunner.getProject();
		log.info "Scripts started"
        def envActive = project.getActiveEnvironment()
        def envActiveName = envActive.getName();
        def allEnvList = project.getEnvironmentList();
        def constantEnv = "G-D4"
        def actualEnv = System.getenv().get("COMMAND_LINE"); // get actual environment from user input
        log.info "Actual envirnment from user input is -- $actualEnv"

        if (isEnvironmentExists(allEnvList,actualEnv)){ // If env is already present just update the project prperies
            project.setActiveEnvironment(actualEnv)
            loadProjectProperties(project,actualEnv);

        }
        else  // If env is not present, create new environment and update the project properties
        {
            cloneExistingEnvironment( project,  actualEnv ,  constantEnv)

        }

        def currectEnvironmentServices = getCurrentEnvExistingSoapServices(project,actualEnv)
        log.info "currectEnvironmentServices--$currectEnvironmentServices"
        addSeviceNameIfNotPresentInEnv(project,currectEnvironmentServices )

        log.info "---->>>>>>>>>>>>>>>>END>>>>>>>>>>>>>>>>>>>>>"


    }


//Check if suite has this environment already created
    public static boolean isEnvironmentExists(def allEnvListTemp,def envNameTemp){
        return allEnvListTemp.name.contains(envNameTemp)
    }

    public static boolean loadProjectProperties(def projectTemp, def actualEnvTemp){
        def flag = false;
        try{
            def file = projectTemp.getPath() + File.separator + "env" +  File.separator + "env.json"
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
        project.getActiveEnvironment().clone(actualEnv)
        project.setActiveEnvironment(actualEnv)
        loadProjectProperties(project,actualEnv);
    }

    public static boolean updateAllServices(def projectTemp,def actualEnvTemp,def constantEnvTemp){

        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
        def soapServCount = env.getSoapServiceCount()
        for(int i = 0;i<soapServCount;i++){
            def soapServ = env.getSoapServiceAt(i)
            def endpointConf = soapServ.getEndpoint().getConfig()
            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
            def endPointStringValue = endpointConf.getStringValue()
                    .replaceAll(constantEnvTemp,actualEnvTemp.replaceAll('-','_'))
                    .replaceAll(constantEnvTemp.replaceAll('-','_'),actualEnvTemp.replaceAll('-','_'))
            endpointConf.setStringValue(endPointStringValue)
        }

        def restServCount = env.getRestServiceCount()
        for(int i = 0;i<restServCount;i++){
            def restServ = env.getRestServiceAt(i)
            def endpointConf = restServ.getEndpoint().getConfig()
            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
            def endPointStringValue = endpointConf.getStringValue()
                    .replaceAll(constantEnvTemp,actualEnvTemp.replaceAll('-','_'))
                    .replaceAll(constantEnvTemp.replaceAll('-','_'),actualEnvTemp.replaceAll('-','_'))
            endpointConf.setStringValue(endPointStringValue)
        }

    }

    public static boolean updateAllServicesIfIPAddressPresent(projectTemp,actualEnvTemp,constantEnvTemp){

        def env = projectTemp.getEnvironmentByName(actualEnvTemp);
        def soapServCount = env.getSoapServiceCount()
        for(int i = 0;i<soapServCount;i++){
            def soapServ = env.getSoapServiceAt(i)
            def endpointConf = soapServ.getEndpoint().getConfig()
            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
            def isProjectStringPresent = endpointConf.getStringValue().contains("Project")
            log.info "isProjectStringPresent contains /Project/ " + isProjectStringPresent
            if(!isProjectStringPresent){
                def endPointStringValuePart2 = endpointConf.getStringValue().split('/pin/')[1]
                def endPointStringValue = "http://\${#Project#${constantEnvTemp}}/pin/" + endPointStringValuePart2
                endpointConf.setStringValue(endPointStringValue)

            }

        }

        def restServCount = env.getRestServiceCount()
        for(int i = 0;i<restServCount;i++){
            def restServ = env.getRestServiceAt(i)
            def endpointConf = restServ.getEndpoint().getConfig()
            log.info "endpointConf.getStringValue()-" + endpointConf.getStringValue()
            def isProjectStringPresent = endpointConf.getStringValue().contains("Project")
            log.info "isProjectStringPresent contains /Project/ " + isProjectStringPresent
            if(!isProjectStringPresent){
                def endPointStringValuePart2 = endpointConf.getStringValue().split('/pin/')[1]
                def endPointStringValue = "http://\${#Project#${constantEnvTemp}}/pin/" + endPointStringValuePart2
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
            log.info "enumType--$enumType"
            def serviceDefnition = item.getEndpoints()[0].toString()
            def indexNum = serviceDefnition.indexOf('/pin/')

            if(!currectEnvironmentServices.contains(nameofService)){
                log.info "nameofService $nameofService - Not exists exists"
               // NewUrl = serviceDefnition
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

}
