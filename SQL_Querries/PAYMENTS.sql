SELECT * FROM V_PAYMENT_OVERVIEW_TEST WHERE 1=1--AND PAYMENTTYPE = 'INTP'--AND INITIATORBANKID IN('3625')--AND FK_PROCESSINGLINEID IN('57')
--AND SO_AGREEMENTID IS NOT NULL --AND PAYMENTSTATUS IN('RFCA') AND MESSAGEID IN ( '159423','159428','159429')
--AND PAYMENTTYPE LIKE 'I%' --AND TRANSACTIONSID IN ('1000137731') --AND STATUSCHANGETIME > (SYSDATE - 2/24 )
--AND ENDTOENDID LIKE '%20211213%' --AND MESSAGEIDENTIFICATION LIKE 'B2B%' --AND MARKETTYPE = 'PM'
--AND RELEASEDATE < SYSDATE + 1 ORDER `BY SO_AGREEMENTID DESC
;
SELECT * FROM TRANSACTIONS;
SELECT * FROM MESSAGE WHERE MESSAGEID IN ( '1000136516','159428','159429') ORDER BY MESSAGEID DESC;
SELECT * FROM BICTOORGID WHERE BIC LIKE 'SP%';-- AND ORGID = 4201;
SELECT * FROM DIRECT_RELEASE_UNPROCESSED_TXN; --DUE_DATE = TRUNC(SYSDATE-15)