TRUNCATE TABLE V_AUT_AU1;

TRUNCATE TABLE V_AUT_AU6;

TRUNCATE TABLE V_AUT_AU3;

TRUNCATE TABLE V_AUT_AU23;

TRUNCATE TABLE V_AUT_AU9;

TRUNCATE TABLE V_AUT_AU20;

COMMIT;
--===============================================================================================================

INSERT INTO V_AUT_AU1
    SELECT
        AU1.BRUKERID,
        AU1.SELSKAP_ID,
        CASE
            WHEN SUBSTR(AU1.BRUKER_KUNDENR, 1, 2) = '00' THEN
                LTRIM(AU1.BRUKER_KUNDENR, 0)
            ELSE
                AU1.BRUKER_KUNDENR
        END BRUKER_KUNDENR,
        AU1.FORETAK_KUNDENR,
        AU1.KONTONR,
        AU1.BRUKER_TYPE,
        AU1.INSTITUTION_CODE,
        AU1.MERKNADER,
        AU1.PRISGRUPPE_FK,
        AU1.PRISGRUPPE_VK,
        AU1.STATUS,
        AU1.KONTOEIER_AKSEPT,
        AU1.OPPRORADATO,
        AU1.AJORADATO,
        AU1.ARKIVREFERANSE,
        AU1.FEIL_TELLER,
        AU1.SIKKERHETSNIVA,
        AU1.SERIENR_KORT,
        AU1.SERIENR_PIN,
        AU1.FAST_PASSORD,
        AU1.KRYPT_KODE,
        AU1.KODEKORT_KODENR,
        AU1.BRUKERNAVN,
        AU1.ADMINISTRATOR,
        AU1.SPRAAKKODE,
        AU1.SIGNATUR,
        AU1.KUNDEGRUPPE,
        AU1.BESTILLKOD,
        AU1.VARSNBORADATO,
        AU1.SYNKDATO,
        AU1.KONTOKONTROLLSUM,
        AU1.KONV,
        AU1.AUTOMATIC_EINVOICE,
        AU1.PRIMAER_KONTONR--,

        -- au1.straksbetaling,

        -- au1.status_digipost,

        -- au1.status_ecommerce,

        -- au1.status_ebox,

        -- au1.migration_date

    FROM
        AUT.AU1@RBSLINK AU1
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================

INSERT INTO V_AUT_AU6
    SELECT
        AU6.BRUKERID,
        AU6.INSTITUTION_CODE,
        AU6.PRODUKTGRUPPE,
        AU6.PRODUKTKODE,
        CASE
            WHEN SUBSTR(AU6.BRUKER_NAVN, 1, 2) = '00' THEN
                LTRIM(AU6.BRUKER_NAVN, 0)
            ELSE
                AU6.BRUKER_NAVN
        END BRUKER_NAVN,
        AU6.SERIENR_KORT,
        AU6.SERIENR_PIN,
        AU6.SIKKERHETSNIVA,
        AU6.STATUS,
        AU6.OPPRORADATO,
        AU6.AJORADATO,
        AU6.FEIL_TELLER,
        AU6.FAST_PASSORD,
        AU6.KRYPT_KODE,
        AU6.BESTILLKOD,
        AU6.BRUKER_NAVN_UPPER,
        AU6.TID1,
        AU6.BELASTKONTO,
        AU6.FREMMEDREF,
        AU6.BETALING,
        AU6.STRAKS,
        AU6.SISTE_BETALINGSDATO
    FROM
        AUT.AU6@RBSLINK AU6
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================

INSERT INTO V_AUT_AU3
    SELECT
        INSTITUTION_CODE,
        PRODUKTKODE,
        MAX_BELOP_PER,
        MAX_BELOP_TX,
        PERIODE,
        PRODUKTNAVN,
        OPPRORADATO,
        AJORADATO,
        TJENESTER,
        SS_KODE,
        SIKKERHETSGRAD,
        STD_BELOP_TX,
        STD_BELOP_PER,
        SS_SO,
        MAX_TID1,
        MAX_TID2,
        SYNK,
        SYNKINTERVALL,
        BANKOPPTID,
        BANKNEDTID,
        BA_KONTO,
        BA_DEKNINGSKONTROLL,
        BA_HIKKESPERRE,
        STD_SUM_TX,
        STD_SUM_PER
    FROM
        AUT.AU3@RBSLINK
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================

INSERT INTO V_AUT_AU23
    SELECT
        INSTITUTION_CODE,
        KUNDENR,
        PRODUKTKODE,
        AVDELING,
        TJENESTER,
        MAX_BELOP_TX,
        MAX_BELOP_PER,
        FORSEGLINGSKODE,
        PERIODE,
        FORSEGLINGSNOEKKEL,
        OPPRORADATO,
        AJORADATO,
        SIKKERHETSNIVA,
        MAX_SUM_TX,
        MAX_SUM_PER
    FROM
        AUT.AU23@RBSLINK
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================

INSERT INTO V_AUT_AU9
    SELECT
        *
    FROM
        AUT.AU9@RBSLINK
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================

INSERT INTO V_AUT_AU20
    SELECT
        *
    FROM
        AUT.AU20@RBSLINK
    WHERE
        INSTITUTION_CODE IN (
            '2544',
            '3625',
            '3890',
            '4201',
            '9055'
        );
--===============================================================================================================