SELECT * FROM V_PAYMENT_OVERVIEW_TEST;
SELECT * FROM V_SO_OVERVIEW_TEST;
--==============================================================================
DROP VIEW V_PAYMENT_OVERVIEW_TEST;
DROP VIEW V_SO_OVERVIEW_TEST;
--==============================================================================
COMMIT;
--==============================================================================
/* V_PAYMENT_OVERVIEW_TEST QUERY JOINS BELOW MAIN TABLES:
 * TRANSACTIONS, PAYMENTINFO, MESSAGE, LACKOFFUNDSTRANSACTIONS, INSTRUCTIONS, APPROVER, RESERVATION_DETAILS, 
 * STATUSREASON, STATUSREASON_ADDT_INF, ACCOUNT_CREDITOR, AGENT_TRANS, STATUSREASON, STATUSREASON_ADDT_INF
*/
CREATE OR REPLACE VIEW V_PAYMENT_OVERVIEW_TEST AS
SELECT M.MESSAGEID,
  P.PAYMENTINFOID,
  T.TRANSACTIONSID,
  T.ENDTOENDID,
  T.PAYMENTTYPE,
  T.FK_PROCESSINGLINEID,
  T.PAYMENTSTATUS,
  R.RESERVATION_TYPE,
  T.INITIATORBANKID,
  T.PROCESSINGBANKORGID,
  T.INSTRUCTEDAMOUNT,
  T.DEBITCOUNTERVALUEAMT,
  T.CREDITCOUNTERVALUECCY,
  T.RELEASEDATE,
  M.CREATIONDATETIME,
  T.STATUSCHANGETIME,
  M.MARKETTYPE,
  M.AGREEMENTID,
  M.INITIATORID,
  M.CUSTOMERID,
  P.DEBETACCOUNTNO,
  A.APPROVERIDENTIFIER AS APPROVER,
  M.ORIGINALCHANNEL,
  AC.OTHER_ID             AS CREDITOR_BBAN,
  AC.IBAN                 AS CREDITOR_IBAN,
  T.AGREEMENTID           AS SO_AGREEMENTID,
  T.AGREEMENTVERSION	  AS SO_AGREEMENTVERSION,
  M.PAINVERSION,
  T.SKKOTYPE,
  M.MESSAGEIDENTIFICATION,
  P.PMTINFOID,
  T.INSTRUCTIONID,
  M.ORIGINALFORMAT,
  T.ISPC,
  P.CATEGORYPURPOSECODE,
  P.SERVICELEVEL,
  T.RECEIPTORDER,
  T.FIRSTLACKOFFUNDSMESSAGESENT,
  T.SEPA,
  T.WB_FLAG,
  T.PRIORITIZED,
  T.RISKSTATUS,
  T.FORCE_FUND,
  T.REQUESTEDEXECUTIONDATE,
  L.LASTOFDAY_COUNTER,
  I.INSTRUCTIONTYPE,
  I.ADDITIONALINFO,
  I.INSTRUCTIONFUNCTION,
  R.RESERVATIONREFERENCE,
  AT.BICFI AS CREDITOR_AGENT_BIC,
  AT.NAME  AS BANK_NAME,
  SR.CODE AS REASONCODE_TRANS,
  SRP.CODE AS REASONCODE_PAY,
  SRA.ADDITIONALINFORMATION AS REJECT_REASON
FROM TRANSACTIONS T
INNER JOIN PAYMENTINFO P ON T.FK_PAYMENTINFOID=P.PAYMENTINFOID
INNER JOIN MESSAGE M ON M.MESSAGEID=P.FK_MESSAGEID
LEFT OUTER JOIN LACKOFFUNDSTRANSACTIONS L ON L.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN INSTRUCTIONS I ON I.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN APPROVER A ON A.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN RESERVATION_DETAILS R ON R.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN STATUSREASON SR ON SR.FK_TRANSACTIONID=T.TRANSACTIONSID
LEFT OUTER JOIN STATUSREASON_ADDT_INF SRA ON SRA.FK_STATUSREASONID=SR.STATUSREASONID
LEFT OUTER JOIN ACCOUNT_CREDITOR AC ON AC.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN AGENT_TRANS AT ON AT.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN STATUSREASON SRP ON SRP.FK_PAYMENTINFOID=P.PAYMENTINFOID
LEFT OUTER JOIN STATUSREASON_ADDT_INF SRA ON SRA.FK_STATUSREASONID=SRP.STATUSREASONID
WHERE M.CREATIONDATETIME >= SYSDATE-5 ORDER BY T.TRANSACTIONSID DESC;
--==============================================================================

/* V_SO_OVERVIEW_TEST QUERY JOINS BELOW MAIN TABLES:
 * STO_AGREEMENT, STO_RECURRENCE, STO_RECURRENCE_EXCLUDE, STO_TRIGGER, STO_SPLIT, STO_PAYMENTINFO, STO_APPROVER, STO_TRANSACTIONS
 * STO_ACCOUNT_CREDITOR, STO_INSTRUCTIONS, STO_REMITTANCEINFO, TRANSACTIONS, STATUSREASON, STATUSREASON_ADDT_INF
*/

CREATE OR REPLACE VIEW V_SO_OVERVIEW_TEST AS
SELECT A.INTERNAL_ID,
  A.AGREEMENTID,
  A.VERSIONNR,
  A.ORG_ID,
  A.AGREEMENTTYPE,
  A.RECURRENCETYPE,
  A.STATUS,
  R.DUE_DATE,
  TT.RELEASEDATE,
  TT.TRANSACTIONSID,
  TT.PAYMENTSTATUS,
  TT.DEBITCOUNTERVALUEAMT  AS AMT,
  TT.CREDITCOUNTERVALUECCY AS CCY,
  A.AGREEMENTACCOUNTNUMBER,
--  A.NEXT_PROCESSING_DATE,
--  A.NEXT_PAYMENT_DATE,
  A.END_DATE,
  A.CREATED_DATE,
  A.BANKHOLIDAYADJUSTMENT,
  A.MARKET_TYPE,
  A.INITIATOR_ID,
  A.CUSTOMERID,
  A.AUTH_AGREEMENTID,
  A.FK_CENTRAL_AGREEMENTID AS IHUB_ID,
  A.CHANNEL,
  A.INITIATEDBY,
  A.CHARGE_REQUIRED,
  A.SOURCEAPPLICATION,
  A.INITIATORORGUNIT,
  E.START_DATE AS EXEMPTION_START,
  E.END_DATE   AS EXEMPTION_END,
  E.REOCCURING,
  L.LIMIT_TYPE,
  L.LIMIT_AMOUNT,
  L.LIMIT_AMOUNT_CCY,
  L.LIMIT_AMOUNT_CCY_OF_TRF,
  S.CREDIT_ACCOUNT AS SPLIT_ACCOUNT,
  S.PERCENT        AS SPLIT_PERCENT,
  S.AMOUNT         AS SPLIT_AMOUNT,
  S.AMOUNT_CCY     AS SPLIT_AMOUNT_CCY,
  I.INSTRUCTION,
  S.AMOUNT_CCY_OF_TRF AS SPLIT_AMOUNT_CCY_OF_TRF,
  T.PAYMENTTYPE       AS AGREEMENT_PAY_TYPE,
  C.OTHER_ID          AS CREDITOR_BBAN,
  C.IBAN              AS CREDITOR_IBAN,
  B.USERID            AS APPROVER_USER,
  B.FIRSTNAME         AS APPROVER_FIRST_NAME,
  I.ADDITIONALINFO,
  I.INSTRUCTIONTYPE,
  U.UNSTRUCTURED,
  U.CREDITORREFINFOREFERENCE,
  TT.PAYMENTTYPE,
  TT.FK_PROCESSINGLINEID,
  TT.ISPC,
  SR.CODE,
  SRA.ADDITIONALINFORMATION AS REJECT_REASON
FROM STO_AGREEMENT A
INNER JOIN STO_RECURRENCE R ON R.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_RECURRENCE_EXCLUDE E ON E.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_TRIGGER L ON L.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_SPLIT S ON S.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_PAYMENTINFO P ON P.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_APPROVER B ON B.FK_AGREEMENT_INTERNAL_ID=A.INTERNAL_ID
LEFT OUTER JOIN STO_TRANSACTIONS T ON T.FK_PAYMENTINFOID=P.PAYMENTINFOID
LEFT OUTER JOIN STO_ACCOUNT_CREDITOR C ON C.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN STO_INSTRUCTIONS I ON I.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN STO_REMITTANCEINFO U ON U.FK_TRANSACTIONSID=T.TRANSACTIONSID
LEFT OUTER JOIN TRANSACTIONS TT ON TT.AGREEMENTID = A.AGREEMENTID AND TT.AGREEMENTVERSION=A.VERSIONNR 
LEFT OUTER JOIN STATUSREASON SR ON SR.FK_TRANSACTIONID=TT.TRANSACTIONSID 
LEFT OUTER JOIN STATUSREASON_ADDT_INF SRA ON SRA.FK_STATUSREASONID=SR.STATUSREASONID 
WHERE A.STATUS NOT IN ('VALIDATED')
ORDER BY A.AGREEMENTID DESC,
  TT.TRANSACTIONSID DESC ;