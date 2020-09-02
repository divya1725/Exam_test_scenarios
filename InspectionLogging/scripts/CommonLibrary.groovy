import java.util.*;
import java.text.SimpleDateFormat;

class CommonLibrary {
	
	public static String getNorwayTime(String format) {

		def d = new Date()
		def sdf = new SimpleDateFormat (format)
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+2"))		
		return sdf.format(d).toString()
	}
	
	public static String getNextTime(String hurMin) {

		Integer.parseInt(hurMin.substring(2))
		def min = Integer.parseInt(hurMin.substring(2))
		def hur = Integer.parseInt(hurMin.substring(0,2))

		def res = ''
		Date now = new Date();
		int yer =  now.getYear() + 1900;
		int mnt = now.getMonth()
		int dte =  now.getDate()

		for(int i= -1 ; i<=2 ; i++) {
			Date d = new Date(yer,mnt,dte,hur,min + i ,00)
			def sdf = new java.text.SimpleDateFormat ("HHmm")
			res = res + "|" + sdf.format(d).toString()

		}
		return  res.substring(1)


	}
	public static def getDate(formart) {
		return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(formart))
	}
	
	public static def getDate(plusDays,formart) {
		return java.time.LocalDateTime.now().plusDays(plusDays).format(java.time.format.DateTimeFormatter.ofPattern(formart))
	}
	
}
