SELECT * FROM RESERVATION_DETAILS --WHERE FK_TRANSACTIONSID = '478215' 
ORDER BY RESERVATIONDETAILSID DESC;

SELECT * FROM HST_RESERVATION_DETAILS --WHERE RESERVATIONTIME > (SYSDATE-1)
ORDER BY FK_TRANSACTIONSID DESC
--ORDER BY RESERVATIONDETAILSID DESC
;--INSERT INTO RESERVATION_DETAILS VALUES ('39077','266571','266571','16-OCT-20','250','EUR','D');
DESC PROCESSINGLINE;
SELECT * FROM PROCESSINGLINE;