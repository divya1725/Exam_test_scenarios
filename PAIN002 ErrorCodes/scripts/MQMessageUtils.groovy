import com.ibm.msg.client.wmq.*;
import java.util.concurrent.TimeUnit;
import com.ibm.mq.jms.*;
import javax.jms.*;

class MQMessageUtils {
	def hostName;
	def port;
	def mqManager;
	def mqChannelAuth;
	def log;
	MQQueueConnectionFactory connectionFactory;
	QueueConnection queueConnection;
	QueueSession queueSession;
//	MQMessageUtils(){
//		log.info "Default constructor.. ";
//	}
	MQMessageUtils(def mqManager,def hostName,def port,def mqChannelAuth,log){
		this.log =log;
		connectionFactory = new MQQueueConnectionFactory();
		connectionFactory.setQueueManager(mqManager);
		connectionFactory.setHostName(hostName);
		connectionFactory.setPort(port);
		connectionFactory.setChannel(mqChannelAuth);
		connectionFactory.setTransportType (WMQConstants.WMQ_CM_CLIENT);
		try{
		queueConnection= connectionFactory.createQueueConnection ();
		queueConnection.start();
		queueSession=queueConnection.createQueueSession (false, Session.AUTO_ACKNOWLEDGE);
		log.info ("Connection started...");
		}
		catch(all){
			log.info "Failed to Connect MQ.. "+all
			connectionFactory.clear();
		}
		
	}
	//@groovy.transform.TimedInterrupt(value = 5L, unit = TimeUnit.SECONDS)
	def sendMessage(def queue, def message){
		Queue queueToSend= new MQQueue (queue);
		Message messageToSend=queueSession.createTextMessage(message);
		QueueSender queueSender = queueSession.createSender (queueToSend);
		try{
		queueSender.send(messageToSend);
		}
		catch(all ){
			log.info "Failed to Send.."+ all
			return false;
		}
		finally{
		//	queueToSend.clear();
		}
		return true;
	}
	
	//@groovy.transform.TimedInterrupt(value = 5L, unit = TimeUnit.SECONDS)
	def receiveMessage(def queue){
	
		Queue queueToReceive= new MQQueue(queue);
		QueueReceiver queueReceiver=queueSession.createReceiver(queueToReceive);
		try{			
			def receivedMessage=queueReceiver.receive(2000);
				if (receivedMessage!=null){
					return receivedMessage.getText().toString();
				}
			return "No messsage";
		   }
		catch(all){
			//return "No messsage"
			log.info e.getMessage();
			return "No messsage";
		}
		finally{
			//queueToReceive.clear();
		}
	}
	
	public def browseMessage(def queue, def msgUnique) {

		def result = "No messsage"
		Queue queueToReceive= new MQQueue(queue);		
		MQQueueBrowser MqBrwse = queueSession.createBrowser(queueToReceive)
		try {
			Enumeration enums = MqBrwse.getEnumeration();			
			while (enums.hasMoreElements()) {
				Object objMsg = enums.nextElement();				
				if (objMsg instanceof TextMessage) {
					TextMessage message = (TextMessage) objMsg;
					result = message.getText().toString();
					if(result.contains(msgUnique)) {
						log.info "Found Unique Message " + msgUnique
						break
					}
				}else if (objMsg instanceof ObjectMessage) {
					result = "No messsage"
					break;
				}

			}

		}
		catch(Exception e)
		{
			result = "No messsage"
			e.printStackTrace()
		}

		return result;
	}

}