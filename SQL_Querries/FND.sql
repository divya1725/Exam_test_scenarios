SELECT * FROM RESERVATIONS WHERE 1=1 AND BANK_ORG_ID = '35803100' AND REQUESTING_SYSTEM = 'PIN' AND PROPRIETARY_CODE = '301'
--AND UNIQUE_MESSAGE_ID = ''   --AND MODIFIED_USER = 'E600685' --AND RESERVATIONS_ID LIKE '%' --AND IBAN LIKE '%' --AND OTHER_ID LIKE '%' 
ORDER BY RESERVATIONS_ID DESC; --AND RESV_ID_UUID = '%' --AND RECONCILIATION_REFERENCE = ''
--------------------------------------------------------------------------------------------------------------
SELECT * FROM ACCOUNTS WHERE 1=1 AND BANK_ORG_ID = '35803100' AND LIMIT_AMNT > '0' AND LEDGER_BALANCE >= '0'
--AND IBAN LIKE 'FI1331511000000835%' --AND OTHER_ID LIKE '%' --AND CURRENCY = 'EUR'
ORDER BY LEDGER_BALANCE DESC, LIMIT_AMNT DESC, ACCOUNTS_ID DESC;
--------------------------------------------------------------------------------------------------------------
-- SHB-D1 Test Data Existing in FND     --  FI3768848395971262 Account Does Not Exists
SELECT IBAN,LIMIT_AMNT,LEDGER_BALANCE FROM ACCOUNTS WHERE IBAN IN 
('FI2631512000018124','FI0431512000018132','FI7931512000018140','FI9431512000013117','FI5031512000013133');
--------------------------------------------------------------------------------------------------------------
-- SHB-D2 Test Data Existing in FND
SELECT IBAN,LIMIT_AMNT,LEDGER_BALANCE FROM ACCOUNTS WHERE IBAN IN ('FI8831511000000843','FI1331511000000835','FI5931511000000686');
--------------------------------------------------------------------------------------------------------------
--UPDATE ACCOUNTS SET LIMIT_AMNT = '9999' WHERE BANK_ORG_ID = '35803100' AND LIMIT_AMNT = '0' ;