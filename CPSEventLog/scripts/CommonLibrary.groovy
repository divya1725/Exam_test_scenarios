class CommonLibrary {
	
	public static void main(String[] args) {		
		println getDate('yyyy-MM-dd')
		println getDate(2,'yyyy-MM-dd')		
	}
	
	public static def getDate(formart) {
		return java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(formart))
	}
	
	public static def getDate(plusDays,formart) {
		return java.time.LocalDateTime.now().plusDays(plusDays).format(java.time.format.DateTimeFormatter.ofPattern(formart))
	}
	
}
