package fileName

import com.jcraft.jsch.*;
import java.io.*;
import java.util.*;



class ServerConnect {


	private static String strHost ="10.246.89.107"
	private static String strUsername = "e211732"
	private static String strPassword = "e211732"
	private static String intPort = 22
	private static String strFileDirectory = "/eos/d4/pin/i1/logs/pin"
	private static String strFileName = "cpseventlog.log"


	public static JSch objJSch
	public static Session objSession
	public static StringBuilder objStringBuilder
	public static Channel objChan
	public static ChannelSftp objSFTPChannel	
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
			println "objSession.connect--"

			objChan = objSession.openChannel("sftp");
			objChan.connect();
			println "objChan.connect--"

			objSFTPChannel = (ChannelSftp) objChan;

			println "objSFTPChannel.connect--"


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

	public static void closeConnection() {

		try {
			objSFTPChannel.exit();
			objChan.disconnect();
			objSession.disconnect();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}
	}

	public static List grepFileFromServer(String fileDirectory, String fileName , String strGrep) {
		this.strFileDirectory = fileDirectory
		this.strFileName = fileName
		List<String> grepList = new ArrayList()

		try {
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

			objReader.close();
			objInputStream.close();
		}
		catch(Exception ex) {
			ex.printStackTrace()
		}

		return grepList.grep(~/.*${strGrep}.*/)

	}


}
