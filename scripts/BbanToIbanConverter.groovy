import org.iban4j.CountryCode;
import org.iban4j.Iban;

class BbanToIbanConverter {
    public static String convertBbanToIban(String bbanAccount){
        String bankCode = bbanAccount.substring(0,4);
        String account = bbanAccount.substring(4,10);
        String checkDigit = bbanAccount.substring(10,11);

        Iban.Builder builder = new Iban.Builder();
        builder.countryCode(CountryCode.NO);
        builder.bankCode(bankCode);
        builder.nationalCheckDigit(checkDigit);
        builder.accountNumber(account);

        Iban value = builder.build();
        return value.toString();
    }
}