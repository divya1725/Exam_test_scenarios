import java.text.DecimalFormat;

class IHubLibrary{

	public static Map<String,String> map = ['daily':'Daglig','weekly':'Uke','biweekly':'14.dag','semimonthly':'2 g mnd','monthly':"${new String([77, -61, -91, 110, 101, 100] as byte[])}",'bimonthly':'2. mnd','quarterly':'Kvartal','tertially':'Tertial','semiannually':"${new String([72, 97, 108, 118, -61, -91, 114] as byte[])}",'yearly':"${new String([-61, -123, 114, 108, 105, 103] as byte[])}"]
	
	public static String validatePeriodicIHubText(amount,ccyCode,creditAccount,firstDueDate,recurrenceType,log){
		def amountStr = getAmountString(amount.toString())
		
		if(ccyCode.toUpperCase() !=  "NOK")
			ccyCode = ccyCode.toUpperCase()
		else 
			ccyCode = ""


			if (creditAccount.size() >11)
				creditAccount = 'UTLAND'

			
			def newDueDate = java.time.LocalDate.parse(firstDueDate).format(java.time.format.DateTimeFormatter.ofPattern("ddMMyy")).toString()
			log.info newDueDate
			def newrecurrenceType = map.get(recurrenceType.toLowerCase())
			//newrecurrenceType = new String(newrecurrenceType.getBytes(), "UTF-8");		
			if(ccyCode == "") 
				return [amountStr ,creditAccount,"Forf:" +newDueDate, newrecurrenceType].join(" ")
			else
				return [amountStr ,ccyCode,creditAccount,"Forf:" +newDueDate, newrecurrenceType].join(" ")
			
	}

	public static def validateBalanceIHubText(accountNumber,creditAccountNumber,firstDueDate,recurrenceType,limitType,log){

		String hentOrOver = "Hent"
		def hentOrOverArray 
		if(limitType.toString().equalsIgnoreCase('ABOVE')){
			accountNumber = creditAccountNumber			
			hentOrOverArray = [79, 118, 101, 114, 102, -61, -72, 114] as byte[]
			hentOrOver = new String(hentOrOverArray, "UTF-8");
		}	
		
		if (accountNumber.size() >11)
					accountNumber = 'UTLAND'

		def newDueDate = java.time.LocalDate.parse(firstDueDate).format(java.time.format.DateTimeFormatter.ofPattern("ddMMyy")).toString()

		def newrecurrenceType = map.get(recurrenceType.toLowerCase())
		return[accountNumber ,"Forf:" +newDueDate, newrecurrenceType, hentOrOver].join(" ")
		
	}

	public static def getAmountString(amount){
		Double amountDouble  = Double.parseDouble(amount)
		DecimalFormat decimalFormat = new DecimalFormat("#");
		decimalFormat.setGroupingUsed(true);
		decimalFormat.setGroupingSize(3);

		String amountStr = decimalFormat.format(amountDouble).toString().replace(',', '.')
		if (amountStr.size() >9)
			return "Se PCL"
		else
			return amountStr
	}
}
