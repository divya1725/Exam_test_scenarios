SELECT A.INTERNAL_ID, A.AGREEMENTID, A.VERSIONNR, A.ORG_ID, A.AGREEMENTTYPE, A.RECURRENCETYPE, A.STATUS, R.DUE_DATE, TT.RELEASEDATE, TT.TRANSACTIONSID, TT.PAYMENTSTATUS, TT.DEBITCOUNTERVALUEAMT AS AMT,
TT.CREDITCOUNTERVALUECCY AS CCY, A.AGREEMENTACCOUNTNUMBER, A.NEXT_PROCESSING_DATE, A.NEXT_PAYMENT_DATE, A.LOGONID, D.OTHER_ID AS DEBITOR_BBAN, D.IBAN AS DEBITOR_IBAN, S.CREDIT_ACCOUNT	AS SPLIT_ACCOUNT,
C.OTHER_ID AS CREDITOR_BBAN, C.IBAN AS CREDITOR_IBAN, A.END_DATE, A.CREATED_DATE, A.BANKHOLIDAYADJUSTMENT, A.MARKET_TYPE, A.INITIATOR_ID, A.CUSTOMERID, A.AUTH_AGREEMENTID, A.FK_CENTRAL_AGREEMENTID AS IHUB_ID,
A.CHANNEL, A.INITIATEDBY, A.CHARGE_REQUIRED, A.SOURCEAPPLICATION, A.INITIATORORGUNIT, E.START_DATE	AS EXEMPTION_START, E.END_DATE AS EXEMPTION_END, E.REOCCURING, L.LIMIT_TYPE, L.LIMIT_AMOUNT, L.LIMIT_AMOUNT_CCY,
L.LIMIT_AMOUNT_CCY_OF_TRF, S.PERCENT AS SPLIT_PERCENT, S.AMOUNT AS SPLIT_AMOUNT, S.AMOUNT_CCY AS SPLIT_AMOUNT_CCY, I.INSTRUCTION, S.AMOUNT_CCY_OF_TRF AS SPLIT_AMOUNT_CCY_OF_TRF, T.PAYMENTTYPE AS AGREEMENT_PAY_TYPE,
B.USERID AS APPROVER_USER, B.FIRSTNAME AS APPROVER_FIRST_NAME, I.ADDITIONALINFO, I.INSTRUCTIONTYPE, U.UNSTRUCTURED, U.CREDITORREFINFOREFERENCE, TT.PAYMENTTYPE, TT.FK_PROCESSINGLINEID, TT.ISPC, SR.CODE, 
SRA.ADDITIONALINFORMATION AS REJECT_REASON  FROM STO_AGREEMENT A INNER JOIN STO_RECURRENCE R ON R.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID LEFT OUTER JOIN STO_RECURRENCE_EXCLUDE E ON E.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_TRIGGER L ON L.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID 
LEFT OUTER JOIN STO_SPLIT S ON S.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID 
LEFT OUTER JOIN STO_PAYMENTINFO P	ON P.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_APPROVER B ON B.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_TRANSACTIONS T ON T.FK_PAYMENTINFOID=P.PAYMENTINFOID 
LEFT OUTER JOIN STO_ACCOUNT_DEBTOR D ON D.FK_PAYMENTINFOID=P.PAYMENTINFOID AND D.FK_ACCOUNTTYPEID = '1'
LEFT OUTER JOIN STO_ACCOUNT_CREDITOR C ON C.FK_TRANSACTIONSID=T.TRANSACTIONSID AND C.FK_ACCOUNTTYPEID = '3' 
LEFT OUTER JOIN STO_INSTRUCTIONS I ON I.FK_TRANSACTIONSID=T.TRANSACTIONSID LEFT OUTER JOIN STO_REMITTANCEINFO U ON U.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN TRANSACTIONS TT ON TT.AGREEMENTID = A.AGREEMENTID AND TT.AGREEMENTVERSION=A.VERSIONNR
LEFT OUTER JOIN STATUSREASON SR	ON SR.FK_TRANSACTIONID=TT.TRANSACTIONSID LEFT OUTER JOIN STATUSREASON_ADDT_INF SRA ON SRA.FK_STATUSREASONID=SR.STATUSREASONID
ORDER BY A.AGREEMENTID DESC,TT.TRANSACTIONSID DESC ;