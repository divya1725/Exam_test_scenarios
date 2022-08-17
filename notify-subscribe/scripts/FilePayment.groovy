//import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import com.eviware.soapui.support.GroovyUtils.*;

class FilePayment{
    public static void prepareMessage(){
        setProperty(testRunner, step,"paymentInfoCount",paymentInfoCount);
        setProperty(testRunner, step,"transactionCount",transactionCount);
        addMessageHeader();
        addMessageBody();
    }
    
    public static void addMessageHeader(){
        def template_MessageHeader = context.expand( '${Properties#template_MessageHeader}');
        messageWithHeader = template_MessageHeader.replaceAll("AGREEMENT_ID",agreementid);
        messageWithHeader = messageWithHeader.replaceAll("ORG_ID",org_id);
        messageWithHeader = messageWithHeader.replaceAll("USER_ID",userid);
        messageWithHeader = messageWithHeader.replaceAll("CUSTOMER_ID",customerid);
        messageWithHeader = messageWithHeader.replaceAll("DATE",dueDate());
        
        def xmlNodeString = getXmlNodeString(paymentInfoCount as int);
        messageWithHeader = messageWithHeader.replaceAll("ADD_ACCOUNTS",xmlNodeString);
        
        setProperty(step,"msg_Header",messageWithHeader);
        addHeaderAccounts();
    }
    
    public static void addHeaderAccounts(){
        def msg_Header = context.expand( '${Properties#msg_Header}');
        def template_Message = context.expand( '${Properties#template_Message}');
        def template_HeaderAccounts = context.expand( '${Properties#template_HeaderAccounts}');
    
        headerAccounts = template_HeaderAccounts.replaceAll("ACCOUNT_ID",Account);
        headerAccounts = headerAccounts.replaceAll("BANK_BIC",orgId_Bic);
        headerAccounts = headerAccounts.replaceAll("CURRENCY_CODE",currency_Code);
        header = msg_Header.replaceAll("XMLValue",headerAccounts)
    
        msg_Header = template_Message.replaceAll("header",header)
        setProperty(testRunner, step,"msg_Header",msg_Header);
    }
    
    public static void addMessageBody(){
        def template_MessageBody = context.expand( '${Properties#template_MessageBody}');
        def msg_Header = context.expand( '${Properties#msg_Header}');
    
        messageBody = template_MessageBody.replaceAll("MSG_ID",dateTime()+uuid());
        messageBody = messageBody.replaceAll("TRANSACTION_COUNT",transactionCount);
        messageBody = messageBody.replaceAll("AMT",getControlSumAmount());
        messageBody = messageBody.replaceAll("DATE",dueDate());
        messageBody = messageBody.replaceAll("BANK_BIC",orgId_Bic);
        
        msg_Header_PayInfo = msg_Header.replaceAll("body",messageBody);
        setProperty(testRunner, step,"msg_Header_PayInfo",msg_Header_PayInfo)
    
        addPaymentInfo();
    }
    
    public static void addPaymentInfo(){
        def messageBody = context.expand( '${Properties#msg_Header_PayInfo}');
        def template_PaymentInfoBody = context.expand( '${Properties#template_PaymentInfoBody}');
    
        paymentInfoBody = template_PaymentInfoBody.replaceAll("PAY_INFO_ID",dateTime()+uuid());
        paymentInfoBody = paymentInfoBody.replaceAll("AMT",getControlSumAmount());
        paymentInfoBody = paymentInfoBody.replaceAll("DATE",dueDate());
        paymentInfoBody = paymentInfoBody.replaceAll("ACCOUNT_ID",Account);
        paymentInfoBody = paymentInfoBody.replaceAll("BANK_BIC",orgId_Bic);
        paymentInfoBody = paymentInfoBody.replaceAll("TRANSACTION_COUNT",transactionCount);
        messageBody = messageBody.replaceAll("PAYMENTINFO_BODY",paymentInfoBody);
    
        xmlNodeString = getXmlNodeString(transactionCount as int);
        messageBody = messageBody.replaceAll("TRANSACTIONS_BODY",xmlNodeString);
        setProperty(testRunner, step,"msg_Header_PayInfo",messageBody);
        addTransactions();
    }
    
    public static void addTransactions(){
        def messageBody = context.expand( '${Properties#msg_Header_PayInfo}');
        def transactionsBody = context.expand( '${Properties#template_TransactionsBody}');
    
        transactions = transactionsBody.replaceAll("INSTRUCTIONS_ID",dateTime()+uuid());
        transactions = transactions.replaceAll("ETE_ID",endToEndID);
        transactions = transactions.replaceAll("AMT",getAmount());
        messageBody = messageBody.replaceAll("XMLValue",transactions)
    
        setProperty(testRunner, step,"msg_Header_PayInfo_Transactions",messageBody)
        log.info messageBody
    }
    
    public static void setProperty(def testRunner, def step, def name, def value) {
        println ("Inside");
        testRunner.testCase.getTestStepByName(step).setPropertyValue(name,value)
    }
    
    public static int uuid(int counter){
        counter = counter*1000;
        int uuid = Math.abs(new Random().nextInt() % counter) + 1;
        return uuid;
    }

    public static int uuid(){
        int uuid = Math.abs(new Random().nextInt() % 6) + 1;
        return uuid;
    }
    
    public static String getAmount(){
        String amount = context.expand( '${Properties#transaction_Amount}' );
        return amount;
    }
    
    public static String dateTime(){
        def dateTime = getDate("ddMMyy_hhmm",0);
        return dateTime;
    }
    
    public static String dueDate(){
        def dueDate = getDate("yyyy-MM-dd",0);
        return dueDate;
    }
    
    public static String getXmlNodeString(counter){
        def xmlNodeString="";
        for (int i = 0; i < counter; i++){
            xmlNodeString = xmlNodeString+"XMLValue"
        }
        return xmlNodeString;
    }
    
    public static String getControlSumAmount(){
        def transactionAmount = context.expand( '${Properties#transaction_Amount}' );
        
        int amt = (transactionCount as int)*(transactionAmount as int);
        def amount =  amt as String;
        return amount;
    }
    
    public static String getDate(def timeFormat, int addDays){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        def dateTime = dateFormat.format(date);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(dateTime));
        calendar.add(Calendar.DATE, addDays);
        String getDate = dateFormat.format(calendar.getTime());
        return getDate;
    }
}