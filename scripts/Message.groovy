class Message {
    public static String sendFileMessage(String source, String destination, String environment, String fileContent) {
        String env = Environment.getEnvironmentName(environment);
        String[] queueManagerdetails;
        String queueName;
        if (fileContent != null && env != null) {
            queueManagerdetails = getQueueManagerDetails(env);
            queueName = getQueueName (source, destination, fileContent, env);
            if (queueName != null && queueManagerdetails[0] != null && queueManagerdetails[1] != null && queueManagerdetails[2]!=null && queueManagerdetails[3] != null){
                println("Queue Name: "+queueName+" in "+queueManagerdetails[0]+" and the content is: \n"+fileContent);
                MQMessageUtils mqMessage=new MQMessageUtils(queueManagerdetails[0],queueManagerdetails[1], queueManagerdetails[2] as int, queueManagerdetails[3]);
                boolean val = mqMessage.sendMessage(queueName, fileContent);
                if (val) { return "Message Sent to "+env;}
            }
        }
        return "Failed to send message to "+env;
    }
    public static String[] getQueueManagerDetails(String env){
        String[] queueHolder = new String[4]; queueHolder[0]="QT0GNMQ"; queueHolder[1]="10.246.89.117"; queueHolder[2]="1415"; queueHolder[3]="QT0GNMQ.SEPA.CL";
        if ((env == "d1"||env == "s6"||env == "d8"||env == "10") && (Environment.access(env)).equals("restricted")){ queueHolder[0]="QT0RNMQ"; queueHolder[1]="139.114.216.8"; queueHolder[2]="1415"; queueHolder[3]="QT0RNMQ.SEPA.CL"; }
        return queueHolder;
    }
    public static String getQueueName(String source, String destination, String fileContent, String env) {
        String fileType = determineFileType(fileContent);
        switch (fileType){
            case "pacs008": if (source == "NIC" && destination == "PIN" ) { return(("PIN."+env+".NIC.PACS008.FROM_NETS").toUpperCase());}
                            if (source == "CLIF" && destination == "ROMA" ) {
                                if (env == "d17") {return(("ROMA.d2.CLIF").toUpperCase());}
                                if (env == "s4")  {return(("ROMA.d1.CLIF").toUpperCase());}}
                            if (source == "ABOL" && destination == "ROMA" ) {
                                if (env == "d17") {return(("ROMA.d2.ABOL").toUpperCase());}
                                if (env == "s4")  {return(("ROMA.d1.ABOL").toUpperCase());}}
            case "pacs002": if (source == "NIC" && destination == "PIN" ) { return(("PIN."+env+".NIC.PACS002.FROM_NETS").toUpperCase());}
            case "camt055": if (source == "CPS" && destination == "PIN" ) { return(("CAMT055."+env+".CPS.PIN").toUpperCase());}
            case "camt029": if (source == "PIN" && destination == "CPS" ) { return(("CAMT029."+env+".PIN.CPS").toUpperCase());}
            case "pain002": if (source == "TPS" && destination == "PIN" ){ return(("PIN."+env+".TPS.ROMA").toUpperCase());}
                            if (source == "PIN" && destination == "CPS" ){
                                String orgnlMsgNmId = getOrgnlMsgNmId (fileContent);
                                if (orgnlMsgNmId.equals("camt.055")){ return(("PAIN002.CAMT055."+env+".PIN.CPS").toUpperCase());}
                                else if (orgnlMsgNmId.equals("pain.001")){
                                    if (env == "d2") { return(("PAIN002.sprint.PIN.CPS.ONLINE").toUpperCase());}
                                    if (env == "d4") { return(("PAIN002.PRFUNC.PIN.CPS.ONLINE").toUpperCase());}
                                    if (env == "d5") { return(("PAIN002.SPRINTREL.PIN.CPS.ONLINE").toUpperCase());}
                                    if (env == "s1") { return(("PAIN002.PRSYS.PIN.CPS.ONLINE").toUpperCase());}
                                    return(("PAIN.002."+env+".PIN.CPS.ONLINE").toUpperCase()); }}
            case "pain001": if (source == "PIN" && destination == "TPS" ){ return(("TPS."+env+".PIN.ROMA").toUpperCase()); }
                            if (source == "CPS" && destination == "PIN" ){
                                if (env == "d2") { return(("PAIN001.sprint.CPS.PIN.ONLINE").toUpperCase());}
                                if (env == "d4") { return(("PAIN001.PRFUNC.CPS.PIN.ONLINE").toUpperCase());}
                                if (env == "d5") { return(("PAIN001.SPRINTREL.CPS.PIN.ONLINE").toUpperCase());}
                                if (env == "s1") { return(("PAIN001.PRSYS.CPS.PIN.ONLINE").toUpperCase());}
                                return(("PAIN001."+env+".CPS.PIN.ONLINE").toUpperCase());}
            case "camt054": if (source == "BAI" && destination == "PIN" ) { return(("PIN."+env+".BAI.CAMT054REQ").toUpperCase()); }
            case "nicToPbdStraks": if (source == "NIC" && destination == "PBD"){ return(("PBD."+getPbdEnv(env)+".NIC.STRAKS").toUpperCase()); }
            case "crr"  :   if (source == "NIC" && destination == "BAI" ) { return ("BAI."+env+".NIC.RECON_REPORTS");}
            case "dsr"  :   if (source == "NIC" && destination == "BAI" ) { return ("BAI."+env+".NIC.RECON_REPORTS");}
            case "trr"  :   if (source == "NIC" && destination == "BAI" ) { return ("BAI."+env+".NIC.RECON_REPORTS");}
            default: return null;
        }
    }
    public static String getPbdEnv (String env){
        return Environment.getPbd(env).toUpperCase();
    }
    public static String updateUniqueValues (String fileContent, String propertyStepName, context){
        //Dynamic values 
        fileContent = checkIfFileContains(fileContent, propertyStepName, "MESSAGE_ID", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DATESTAMP", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "ORIGINAL_TRANSACTIONSID", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "END_TO_END_ID", context);
        //Customer details
        fileContent = checkIfFileContains(fileContent, propertyStepName, "USER_ID", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "ORG_ID", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "AGREEMENT_ID", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CUSTOMER_ID", context);

        //Sender and Receiver details
        fileContent = checkIfFileContains(fileContent, propertyStepName, "RECEIVING_BIC", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DEBTOR_BIC", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CRR_RECEIVING_BIC_Level1", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CRR_RECEIVING_BIC_Level2", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DSR_RECEIVING_BIC_Level1", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "TRR_RECEIVING_BIC_Level1", context);    
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITORAGENT_BIC", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CRR_CREDITORAGENT_BIC", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DSR_CREDITORAGENT_BIC", context);
        //Debtor details
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DEBTOR_ACCOUNT_IBAN", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DEBTOR_ACCOUNT_BBAN", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "DEBTOR_COUNTRY", context);
        //Transfer amounts
        fileContent = checkIfFileContains(fileContent, propertyStepName, "TRANSFER_CURRENCY", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "AMOUNT", context);
        //Creditor details
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITORAGENT_COUNTRY", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITOR_COUNTRY", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITOR_ACCOUNT_IBAN", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITOR_ACCOUNT_BBAN", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "CREDITOR_NAME", context);
        //Regulatory reporting
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_CODE", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_34Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_35Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_36Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_Spaces_35Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_LastCharSpace_35Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_FirstLastCharSpace_35Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_SpecialChar_35Char", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "REGULATORY_INFO_Symbols_35Char", context);
        //Remittance details
        fileContent = checkIfFileContains(fileContent, propertyStepName, "UNSTRUCTURED_REMITTANCE", context);
        fileContent = checkIfFileContains(fileContent, propertyStepName, "FILE_STATUS", context);
        return fileContent;
    }
    public static String checkIfFileContains(String fileContent, String propertyStepName, String value, context){
        if (fileContent.contains(value)){
            switch (value){
                case "MESSAGE_ID":
                case "DATESTAMP":
                case "ORIGINAL_TRANSACTIONSID":
                case "END_TO_END_ID":
                    String fileType = determineFileType(fileContent);
                    if (fileType!=null){
                        switch (fileType){
                            case "pacs008":
                            case "pain001":
                            case "camt055": 
                                context.testCase.getTestStepByName(propertyStepName).setPropertyValue(value, getDynamicValues (value) );
                        }
                    }
            }
        }
        switch (value){
            case "REGULATORY_INFO_Spaces_35Char": return (fileContent.replaceAll(value, "                                   "));
            case "REGULATORY_INFO_LastCharSpace_35Char": return (fileContent.replaceAll(value, "RegulatoryReportingInformation35ch "));
            case "REGULATORY_INFO_FirstLastCharSpace_35Char": return (fileContent.replaceAll(value, " RegulatoryReportingInformation35c "));
            case "REGULATORY_INFO_SpecialChar_35Char": return (fileContent.replaceAll(value, "ÄöÆä ÅæÖæ øØæø åöäæ"));
            case "DATESTAMP": return (fileContent.replaceAll(value, (getDynamicValues (value))));
            default: return (fileContent.replaceAll(value, context.expand('${'+propertyStepName+'#'+value+'}').toString()));}
        return fileContent;
    }
    public static String determineFileType(String fileContent) {
        if (fileContent.contains("xsd:pain.001")) { return "pain001";}
        if (fileContent.contains("xsd:pain.002")) { return "pain002";}
        if (fileContent.contains("xsd:pacs.002")) { return "pacs002";}
        if (fileContent.contains("xsd:pacs.008")) { return "pacs008";}
        if (fileContent.contains("xsd:camt.029")) { return "camt029";}
        if (fileContent.contains("xsd:camt.054")) { return "camt054";}
        if (fileContent.contains("xsd:camt.055")) { return "camt055";}
        if (fileContent.contains("realtime247:crr")) { return "crr";}
        if (fileContent.contains("realtime247:dsr")) { return "dsr";}
        if (fileContent.contains("realtime247:trr")) { return "trr";}
        if (fileContent.contains("FinancialInstitutions") && fileContent.contains("NicsRealParticipants")) { return "nicToPbdStraks";}
    }
    public static String getOrgnlMsgNmId (String fileContent){
        if (fileContent.contains ("<OrgnlMsgNmId>pain.001")) { return "pain.001";}
        if (fileContent.contains ("<OrgnlMsgNmId>pacs.008")) { return "pacs.008";}
        if (fileContent.contains ("<OrgnlMsgNmId>camt.055")) { return "camt.055";}
    }
    public static String getDynamicValues(String value) {
        switch (value.toUpperCase()){
            case "MESSAGE_ID"       : return ("Msg-"+java.util.UUID.randomUUID().toString().substring(0,28));
            case "DATESTAMP"        : return (java.time.LocalDateTime.now().format('yyyy-MM-dd'));
            case "END_TO_END_ID"    : return (java.time.LocalDateTime.now().format('yyyyMMddHHmmss'));
            default: return (Math.abs(new Random().nextInt() % 6000000) + 1); }
    }
}