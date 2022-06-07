--==============================================================================
SELECT A.INTERNAL_ID,A.AGREEMENTID,A.VERSIONNR,A.AGREEMENTTYPE,A.RECURRENCETYPE,A.STATUS,A.IHUB_CLOSED,A.FK_CENTRAL_AGREEMENTID,A.BANKHOLIDAYADJUSTMENT,A.CREATED_DATE, R.DUE_DATE,A.END_DATE,A.ORG_ID,A.AGREEMENTACCOUNTNUMBER,A.MARKET_TYPE,A.INITIATOR_ID, A.CUSTOMERID,A.AUTH_AGREEMENTID,A.CHANNEL,A.INITIATEDBY,A.CHARGE_REQUIRED,A.SOURCEAPPLICATION,
A.INITIATORORGUNIT, E.START_DATE AS EXEMPTION_START, E.END_DATE AS EXEMPTION_END, E.REOCCURING, L.LIMIT_TYPE, L.LIMIT_AMOUNT, L.LIMIT_AMOUNT_CCY,L.LIMIT_AMOUNT_CCY_OF_TRF, S.CREDIT_ACCOUNT AS SPLIT_ACCOUNT, S.PERCENT AS SPLIT_PERCENT,S.AMOUNT AS SPLIT_AMOUNT,S.AMOUNT_CCY AS SPLIT_AMOUNT_CCY,
I.INSTRUCTION,S.AMOUNT_CCY_OF_TRF AS SPLIT_AMOUNT_CCY_OF_TRF, T.PAYMENTTYPE AS AGREEMENT_PAY_TYPE, C.OTHER_ID AS CREDITOR_BBAN, C.IBAN AS CREDITOR_IBAN, C.CURRENCY AS CREDIT_ACCOUNT_CURRENCY,	B.USERID, B.FIRSTNAME AS APPROVER_FIRST_NAME,B.LASTNAME AS APPROVER_LAST_NAME,
I.ADDITIONALINFO, I.INSTRUCTIONTYPE, U.UNSTRUCTURED, U.CREDITORREFINFOREFERENCE
FROM STOH_AGREEMENT A INNER JOIN	STOH_RECURRENCE R ON R.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER	JOIN	STOH_RECURRENCE_EXCLUDE E  ON E.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER	JOIN	STOH_TRIGGER L	ON L.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER	JOIN	STOH_SPLIT S 
ON S.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER 	JOIN	STOH_PAYMENTINFO	P ON P.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER	JOIN	STOH_APPROVER B ON B.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER	JOIN	STOH_TRANSACTIONS T ON T.FK_PAYMENTINFOID=P.PAYMENTINFOID 
LEFT OUTER	JOIN	STOH_ACCOUNT_CREDITOR C ON C.FK_TRANSACTIONSID=T.TRANSACTIONSID LEFT OUTER	JOIN	STOH_INSTRUCTIONS I ON I.FK_TRANSACTIONSID=T.TRANSACTIONSID LEFT OUTER	JOIN	STOH_REMITTANCEINFO U ON U.FK_TRANSACTIONSID=T.TRANSACTIONSID
WHERE 1=1 --AND --A.ORG_ID IN ('3201') --  AND A.AGREEMENTID IN ('11345')--  AND A.CUSTOMERID IN ('') --  AND A.AGREEMENTACCOUNTNUMBER IN ('') 
ORDER BY A.AGREEMENTID DESC;
--==============================================================================
SELECT * FROM STOH_AGREEMENT ORDER BY AGREEMENTID DESC;