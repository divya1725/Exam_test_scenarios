/*	TIME STAMPS IN SQL
	=====================================
	WITHOUT TIME PART
*/
	SELECT
	  TRUNC(M.CREATIONDATETIME) AS CREATIONDATETIME
	FROM
	  MESSAGE M
	WHERE 1=1
	  AND TRUNC(M.CREATIONDATETIME) BETWEEN '01-OCT-2019' AND '03-OCT-2019'
	  AND TRUNC(M.CREATIONDATETIME) IN (TRUNC(SYSDATE),TRUNC(SYSDATE-2))
	;
/*	=====================================
	WITH TIME PART
*/
	SELECT
	  TO_CHAR(M.CREATIONDATETIME,'DD-MON-YYYY HH24:MI:SS') AS CREATIONDATETIME
	FROM
	  MESSAGE M
	WHERE 1=1
	  AND TRUNC(M.CREATIONDATETIME) BETWEEN '01-OCT-2019' AND '03-OCT-2019'
	  AND TRUNC(M.CREATIONDATETIME) IN (TRUNC(SYSDATE),TRUNC(SYSDATE-2))
	;
/*
Time Stamp Settings in SQL
	Open Oracle SQL Developer
	"Menu → TOOLS → Preferences → NLS"

	"DD-MON-RR HH24:MI:SS" 	→	Time Stamp is present by default for all date columns
								If we try to Truncate the time part, we get 10-OCT-19 00:00:00 in result
	"DD-MON-RR" 			→ 	No time stamp by default for any column
								If we try to Display the time part, we get actual time using 'TO_CHAR' query.
*/