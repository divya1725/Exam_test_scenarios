--==============================================================================================================================
SELECT * FROM STO_AGREEMENT WHERE FK_CENTRAL_AGREEMENTID IS NOT NULL;
SELECT * FROM STO_AGREEMENT WHERE STATUS IN ('ACTIVE','PENDING_DATE') AND FK_CENTRAL_AGREEMENTID IS NULL;



--HST TABLES====================================================================================================================

SELECT * FROM STOH_AGREEMENT WHERE FK_CENTRAL_AGREEMENTID IS NOT NULL;
SELECT * FROM STOH_AGREEMENT WHERE IHUB_CLOSED = NULL AND FK_CENTRAL_AGREEMENTID IS NOT NULL;
SELECT * FROM STOH_AGREEMENT WHERE STATUS IN ('CLOSED') AND FK_CENTRAL_AGREEMENTID IS NULL;