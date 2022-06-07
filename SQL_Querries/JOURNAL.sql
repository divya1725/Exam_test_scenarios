SELECT * FROM EVENTS_2 WHERE INST IN ('4201') AND OSYS='PIN' AND TYPE='2001'
--AND ID='57102465'     --AND EKEY='0c91b5fdee4c496391dba7b8c465ab4a'
--AND KEY1='150160'     --AND TRUNC(ETIME)='31-JUL-20' 
--AND KEY2='150160'     --AND KEY3='83960201046'
--AND KEY4='90410959827'--AND KEY5='3134852'
--AND PKEY=NULL         --AND TRUNC(IDATE)='31-JUL-20'
--AND KEY6='0c91b5fdee4c496391dba7b8c465ab4a'
ORDER BY ID DESC;

/*
||	ID		||	UNIQUE JOURNAL ID		||	55973005						||||	EKEY	||	End To End ID			||	NOTPROVIDED                     ||
||	INST	||	ORG_ID					||	2544                            ||||	ETIME	||	Time of logging			||	22-OCT-19 10:43:18              ||
||	TYPE	||	(PaymentCreate=2000)	||	2000                            ||||	OSYS	||	Initiating System		||	PIN                             ||
||	KEY1	||	Employee / Initiator	||	E212927                         ||||	KEY2	||	Customer Number			||	00911333813                     ||
||	KEY3	||	Debit Account Number	||	22916445974                     ||||	KEY4	||	Credit Account Number	||	24800799658                     ||
||	KEY5	||	Message Identification	||	72b5367b86b608d0ff557a214df2bd4b||||	KEY6	||							||	NOTPROVIDED                     ||
||	PKEY	||			||	(null)    ||||	IDATE	||	Date of logging	    ||	22-OCT-19 10:43:19||||	DATA	    ||	Request & Response XML      	||
2000	PAYMENT_CREATE								||		2001    PAYMENT_UPDATE					||		2002    PAYMENT_APPROVE
2003    PAYMENT_CANCEL								||		2004    PAYMENT_CONFIRM					||		2005    PAYMENT_PRIORITIZE
2006    PAYMENT_REACTIVATE							||		2007    PAYMENT_STOP					||		2008    PAYMENT_UNAPPROVE
2009    PAYMENT_UNPRIORITIZE						||		2010    PAYMENT_REMOVE_FRAUD_FLAG		||		2011    PAYMENT_FORCE_FUND
2012    PAYMENT_APPROVE_ASYNC_STARTED				||		2013    PAYMENT_APPROVE_ASYNC_FINISHED	||		2100    FUNDS_CHECK
2101    PAIN001_TO_DNB								||		2102    PAIN002_FROM_DNB				||		2103    DEBIT_CAMT_FROM_DNB
2104    CREDIT_CAMT_FROM_DNB						||		2105    PAIN001_TO_PROFX				||		2106    MT101_TO_PROFX
2107    PAYMENT_REJECT_FROM_PROFX					||		2108    PAYMENT_CONFIRMED_FROM_PROFX	||		2300	PAYMENT_CONFIRMED_FROM_OAS
2200    STANDING_ORDER_CREATE						||		2201	STANDING_ORDER_UPDATE			||		2202	STANDING_ORDER_STATUS_UPDATE
2203	STANDING_ORDER_PAYMENT_INSTRUCTION_CREATE	||	2109    RECHARGE							||		2400	CAMT55_CANCEL_TRANSACTIONS
*/