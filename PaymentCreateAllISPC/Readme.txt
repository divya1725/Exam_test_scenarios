1. This test suite is designed to test all ISPC in different WSDL versions for BM and PM
- Create, Confirm, Read,Approve,Search, Update,
2. Test cases name should be ispc name, against which assertions are written in Create/update.
3. File payment are created from SOAP look at charge engine cases, when payment cannot be created from SOAP service.
5. Prepaid ispc - Has to be modified to work in restricted with correct data, Test case "PAYCTDOMOUPREP_prepaid_restricted"
6. If you are adding new account for any reason, Make sure you added in Auth request as well.
7. Each date field must be not be hard coded, It should take value from GenerateDate script, For more info see requestedexecutiondate property on test suite level.

How to test : {Test suite designed for BM}
- Change envs endpoint on project level,
- Set all properties at test suite level, Very important!!, as per your test env data, PBD agreement,
- Create all payment, Keep eye on requested execution date, Ideally it should be next day, else in some cases, PIN will call online reservaiton from status update which 
makes test case failure..
- Run duedate batch, Make sure that payment gets completed in value chain.
- Run historic search if needed(not included as part of this suite)


Adding new case/ISPC: 
Clone one of the test case, Change name and test case description.. Modify test steps.