
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry
import com.jcraft.jsch.ChannelSftp.LsEntrySelector
import java.io.*;
import java.nio.file.DirectoryStream.Filter
import java.util.*;



class ServerConnect {


	private static String strHost ="10.246.89.207"
	private static String strUsername = "e211732"
	private static String strPassword = "e211732"
	private static def intPort = 22
	private static String strFileDirectory = "/eos/d4/pin/i1/logs/pin"
	private static String strFileName = "cpseventlog.log"


	public static JSch objJSch
	public static Session objSession
	public static StringBuilder objStringBuilder
	public static Channel objChan
	public static ChannelSftp objSFTPChannel

	public static void main(String[] args) {

		ServerConnect.Connect(strHost,strUsername,strPassword,intPort)

		String command2="cd /ebs/d4/pin/logs/piPaymentProcessor;grep -B 10 -A 10 '<OrgnlMsgId>DomAllPmts_20200708MSG083</OrgnlMsgId>' mqpayload.log";
		

		println ("Command2 -" + ServerConnect.execCommand(command2))
		
		ServerConnect.closeConnection()
	}

	public static String Connect(String strHost, String strUsername, String strPassword,def intPort  ) {
		this.strHost = strHost
		this.strUsername = strUsername
		this.strPassword = strPassword
		this.intPort = intPort
		String returnFlag = "PASS"

		objJSch = new JSch();
		objSession = null;
		objStringBuilder = new StringBuilder();

		try{
			objSession = objJSch.getSession(strUsername, strHost);
			objSession.setPort(intPort)
			objSession.setPassword(strPassword)
			println "objSession.setPassword--"

			Properties objProp = new Properties()
			objProp.put("StrictHostKeyChecking", "no")

			objSession.setConfig(objProp);
			objSession.connect()
			println "Session Created Successfully!!"


		}
		catch(Exception ex) {
			returnFlag = "FAIL"
			ex.printStackTrace()

		}

		return returnFlag;
	}

	public static int getTotalLineNumbers(String fileDirectory, String fileName) {
		this.strFileDirectory = fileDirectory
		this.strFileName = fileName

		objChan = objSession.openChannel("sftp");
		objChan.connect();
		println "objChan.connect--"

		objSFTPChannel = (ChannelSftp) objChan;

		println "objSFTPChannel.connect--"

		ServerConnect.objSFTPChannel.cd(strFileDirectory);

		InputStream objInputStream = objSFTPChannel.get(strFileName);
		char[] chBuffer = new char[0x10000];

		Reader objReader = new InputStreamReader(objInputStream, "UTF-8");
		int intLine = 0;

		println "obgBuffReader-->"
		LineNumberReader lineReader = new LineNumberReader(objReader)
		while ((lineReader.readLine()) != null);
			println "PrintTotalNumberoflines-->" + lineReader.getLineNumber()

		objReader.close();
		objInputStream.close();

		return lineReader.getLineNumber()

	}


	public static String readFileFromServer(String fileDirectory, String fileName) {
		this.strFileDirectory = fileDirectory
		this.strFileName = fileName
		try {
			objChan = objSession.openChannel("sftp");
			objChan.connect();
			println "objChan.connect--"

			objSFTPChannel = (ChannelSftp) objChan;

			println "objSFTPChannel.connect--"

			ServerConnect.objSFTPChannel.cd(strFileDirectory);

			InputStream objInputStream = objSFTPChannel.get(strFileName);
			char[] chBuffer = new char[0x10000];

			Reader objReader = new InputStreamReader(objInputStream, "UTF-8");
			int intLine = 0;

			while (intLine >= 0)
			{
				intLine = objReader.read(chBuffer, 0, chBuffer.length);
				println "intLine-->" + intLine
				if (intLine > 0)
				{ objStringBuilder.append(chBuffer, 0, intLine);}

				println "objStringBuilder->" + intLine + "-->" + objStringBuilder
			}

			objReader.close();
			objInputStream.close();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}

		return objStringBuilder.toString()

	}


	public static String readFileFromServer(String fileDirectory, String fileName , def startLineNumber) {
		this.strFileDirectory = fileDirectory
		this.strFileName = fileName

		try {
			objChan = objSession.openChannel("sftp");
			objChan.connect();
			println "objChan.connect--"

			objSFTPChannel = (ChannelSftp) objChan;

			println "objSFTPChannel.connect--"

			ServerConnect.objSFTPChannel.cd(strFileDirectory);

			InputStream objInputStream = objSFTPChannel.get(strFileName);

			Reader objReader = new InputStreamReader(objInputStream, "UTF-8");

			LineNumberReader lineReader = new LineNumberReader(objReader)

			String line;
			while ((line = lineReader.readLine()) != null) {

				if(lineReader.getLineNumber() >= startLineNumber )
					objStringBuilder.append(line).append("\n");

				println "getLineNumber()->" + lineReader.getLineNumber() + " -->" + objStringBuilder.toString()
			}

			println "objStringBuilder->-->" + objStringBuilder

			objReader.close();
			objInputStream.close();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}
		return objStringBuilder.toString()

	}

	public static boolean closeConnection() {

		try {
			objSFTPChannel.exit();
			objChan.disconnect();
			objSession.disconnect();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}
		return true
	}

	public static List grepFileFromServer(String fileDirectory, String fileName , String strGrep) {
		this.strFileDirectory = fileDirectory
		this.strFileName = fileName
		List<String> grepList = new ArrayList()

		try {
			objChan = objSession.openChannel("sftp");
			objChan.connect();
			println "objChan.connect--"
			objSFTPChannel = (ChannelSftp) objChan;
			println "objSFTPChannel.connect--"

			ServerConnect.objSFTPChannel.cd(strFileDirectory);

			InputStream objInputStream = objSFTPChannel.get(strFileName);
			char[] chBuffer = new char[0x10000];

			Reader objReader = new InputStreamReader(objInputStream, "UTF-8");
			LineNumberReader lineReader = new LineNumberReader(objReader)

			String line;
			while ((line = lineReader.readLine()) != null) {
				grepList.add(line)
			}

			println "objStringBuilder->-->" + objStringBuilder
			lineReader.close();
			objReader.close();
			objInputStream.close();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}

		return grepList.grep(~/.*${strGrep}.*/)

	}


	public static String execCommand(String command1) {
		String resultStr = ''
		try{
			Channel channel=objSession.openChannel("exec");
			((ChannelExec)channel).setCommand(command1);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
			println ("setErrStream-->" + command1)
			InputStream ina=channel.getInputStream();
			channel.connect();
			byte[] tmp=new byte[1024];

			while(true){
				while(ina.available()>0){
					int i=ina.read(tmp, 0, 1024);
					if(i<0)break;
					resultStr = resultStr + new String(tmp, 0, i);
				}
				if(channel.isClosed()){
					println("exit-status: "+channel.getExitStatus());
					break;
				}
				try{Thread.sleep(1000);}catch(Exception ee){}
			}
			channel.disconnect();
			//			objSession.disconnect();
			println("DONE");
		}catch(Exception e){
			resultStr = 'FAIL'
			println "getLocalizedMessage" + e.getLocalizedMessage()
			e.printStackTrace();
		}

		return resultStr;
	}
	
	public static String execBatchCommand(String env , String areaPinPen , String command) {
		
		def res = ""
		def tempBatchFolder = ""
		String latestFolder = ""
		areaPinPen = areaPinPen.toString().toLowerCase()
		env = env.toString().toLowerCase()
		if(areaPinPen.equalsIgnoreCase("pin"))		
			tempBatchFolder = "/ebs/" + env.toLowerCase() + "/pin/releases/pin-srv-batch/"
		else
			tempBatchFolder = "/ebs/" + env.toLowerCase() + "/pen/releases/pen-srv-batch/"	
		 
		objChan = objSession.openChannel("sftp");
		objChan.connect();
		objSFTPChannel = (ChannelSftp)objChan;
		objSFTPChannel.cd(tempBatchFolder);
		int modifiedTime = 0		
		SftpATTRS att = objSFTPChannel.ls(tempBatchFolder, new LsEntrySelector() {
					@Override
					public int select(LsEntry entry) {
						String fileName = entry.getFilename();
						if(entry.getAttrs().isDir() && !entry.getFilename().startsWith(".")) {
							if(entry.getAttrs().getMTime() > modifiedTime )	{
								modifiedTime = entry.getAttrs().getMTime()
								latestFolder = entry.getFilename();
							}							
						}
						return com.jcraft.jsch.ChannelSftp.LsEntrySelector.CONTINUE;
					}
				})
		
		objSFTPChannel.exit()
		objChan.disconnect()
		def binFilePath = tempBatchFolder + latestFolder + "/bin/"
		String command2 = "echo ${ServerConnect.strUsername} | sudo -S su - $areaPinPen$env -c \'$binFilePath$command\'"
		//println "command2:" + command2
		res = execCommand(command2)				
		return res

	}


}
