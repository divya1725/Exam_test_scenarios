--BM PAYMENT FOR AGREEMENT
  SELECT D.OPPDRAG_ID,D.AVTALE_ID AS AGREEMENT_ID,D.KONTO_ID AS ACCOUNT_NUMBER,B.BRUKER_ID AS USER_ID,B.FORNAVN AS FIRST_NAME,B.ETTERNAVN AS LASTNAME,B.BRUKERNAVN AS USERNAME
  FROM TRANSAKSJON T, OPPDRAG D, BRUKER B WHERE T.OPPDRAG_ID=D.OPPDRAG_ID AND D.AVTALE_ID=B.AVTALE_ID AND B.AVTALE_ID='494768' ORDER BY B.FORNAVN;
--===================================================================================================================
--USERID FOR CPS AGREEMENT
  SELECT BRUKER_ID,AVTALE_ID,FORNAVN,ETTERNAVN,BRUKERNAVN,PERSONNR FROM BRUKER WHERE AVTALE_ID='554632' ORDER BY BRUKER_ID DESC;
--===================================================================================================================
--CUSTOMER HAVING B2B AGREEMENT
  SELECT * FROM ES.ROLLE WHERE KORTNAVN='361';
--===================================================================================================================