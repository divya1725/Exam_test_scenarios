SET SERVEROUTPUT ON
  DECLARE
  L_JOBID MAINT_LOG.JOBID%TYPE; --ALWAYS NULL
  L_JOBLIST VARCHAR2(300) := 'del_trans_VALI';
  L_PURGE_LOG NUMBER(1) := 0; --PURGE OFF .... 1 PURGE ON
  L_LOG_HIST NUMBER(2) := 25; --KEEP LATEST JOB ONLY
  L_DEBUG NUMBER := 1; --DEBUG ON
  BEGIN
    PWH_MAINT.BULK_CLEANUP(L_JOBID,L_JOBLIST,L_PURGE_LOG,L_LOG_HIST,L_DEBUG);
    DBMS_OUTPUT.PUT_LINE('RUNNING JOB:'||L_JOBLIST);
  END;
--========================================================================================================
SELECT * FROM MAINT_LOG ORDER BY LOG_TZ DESC;
--========================================================================================================
SELECT TRANSACTIONSID,INITIATORBANKID,PAYMENTSTATUS,STATUSCHANGETIME, HIDDEN FROM TRANSACTIONS
WHERE PAYMENTSTATUS IN ('','','VALI') AND STATUSCHANGETIME < TRUNC(SYSDATE-3) ORDER BY TRANSACTIONSID DESC;

SELECT STATUSCHANGETIME FROM TRANSACTIONS WHERE STATUSCHANGETIME < TRUNC(SYSDATE) - 60;
--========================================================================================================
UPDATE TRANSACTIONS SET STATUSCHANGETIME='01-MAR-16 07.59.27.873556000 AM'
WHERE TRANSACTIONSID IN ('182961','385954','155356');
--========================================================================================================
SELECT * FROM AUTH_USER WHERE TRUNC(UPDATETIMESTAMP) < TRUNC(SYSDATE) - 30 ORDER BY UPDATETIMESTAMP DESC;
--========================================================================================================
UPDATE TRANSACTIONS SET STATUSCHANGETIME='17-MAR-19 11.06.23.9838 AM' WHERE TRANSACTIONSID > '386431';
UPDATE AUTH_USER SET UPDATETIMESTAMP='19-FEB-19 06.37.40.198000000 AM' WHERE AUTHUSERID < '6';
--========================================================================================================
SELECT * FROM HISTORYBANKCONFIG ORDER BY INITIATORBANKID;
SELECT * FROM MAINT_LOG ORDER BY LOG_TZ DESC;
SELECT * FROM USER_TAB_SUBPARTITIONS;
SELECT * FROM USER_TAB_PARTITIONS;
SELECT TRANSACTIONSID,INITIATORBANKID,PAYMENTSTATUS,HIDDEN FROM TRANSACTIONS ORDER BY TRANSACTIONSID DESC;
SELECT TRANSACTIONSID,INITIATORBANKID,PAYMENTSTATUS,HIDDEN FROM HST_TRANSACTIONS ORDER BY TRANSACTIONSID DESC;
SELECT DISTINCT INITIATORBANKID FROM MESSAGE;
SELECT * FROM DHUB_TRANS_TEST;
--========================================================================================================
SELECT TRUNC(SYSDATE) - 1110 FROM DUAL; --1180	1802	REJC	13-MAR-17 11.06.23.983890000 AM	Y
SELECT SYSDATE -1076 FROM DUAL; --18-OCT-17 00:00:00 
--========================================================================================================
SELECT TRANSACTIONSID,INITIATORBANKID FROM HST_TRANSACTIONS WHERE  TRANSACTIONSID IN (174040,174039,174018);
UPDATE HST_TRANSACTIONS SET STATUSCHANGETIME = SYSDATE -1111 WHERE TRANSACTIONSID IN (174040,174039,174018);
SELECT DISTINCT INITIATORBANKID FROM HST_MESSAGE;
--========================================================================================================
SELECT MIN(STATUSCHANGETIME) from HST_TRANSACTIONS WHERE INITIATORBANKID = '4201';
SELECT MIN(STATUSCHANGETIME) FROM HST_PAYMENTINFO WHERE INITIATORBANKID = '4201';
SELECT MIN(LATEST_RELEASEDATE) FROM HST_MESSAGE WHERE INITIATORBANKID = '4201';
--========================================================================================================
SELECT * FROM PWHDATA.HISTORYBANKCONFIG WHERE INITIATORBANKID IN (4201) AND PAYMENTSTATUS = 'COMP';
UPDATE HISTORYBANKCONFIG SET DAYSTOKEEP=1079 WHERE INITIATORBANKID=4201 AND PAYMENTSTATUS= 'COMP';COMMIT;
--========================================================================================================
DECLARE
l_table VARCHAR2(100) := 'HST_TRANSACTIONS';
BEGIN
PWH_MAINT.DROP_SUBPART_HST(l_table);
END;
/
--========================================================================================================
DECLARE
l_table VARCHAR2(100) := 'HST_PAYMENTINFO';
BEGIN
PWH_MAINT.DROP_SUBPART_HST(l_table);
END;
/
--========================================================================================================
DECLARE
l_table VARCHAR2(100) := 'HST_MESSAGE';
BEGIN
PWH_MAINT.DROP_SUBPART_HST(l_table);
END;
/
--========================================================================================================
SELECT 
  T.TRANSACTIONSID,T.INITIATORBANKID,T.STATUSCHANGETIME AS TRANS_STSTIME,T.PAYMENTSTATUS,
  P.PAYMENTINFOID,P.STATUSCHANGETIME AS PAYINFO_STSTIME,
  M.MESSAGEID,M.LATEST_RELEASEDATE AS MSG_STSTIME
FROM HST_TRANSACTIONS T, HST_PAYMENTINFO P, HST_MESSAGE M
WHERE 1=1
  AND T.FK_PAYMENTINFOID=P.PAYMENTINFOID 
  AND P.FK_MESSAGEID=M.MESSAGEID
  AND T.TRANSACTIONSID IN (174040);
--========================================================================================================
DECLARE
l_tbabname VARCHAR2(100) := 'DHUB_TRANS_TEST';
l_daystokeep NUMBER := 100;
BEGIN
PWH_MAINT.DROP_PART_INT(l_tbabname, l_daystokeep);
END;
/
--========================================================================================================