--flyway_issue_fixing_pin
select * from "pin_schema_version" where 1=1 and "version" = '195.00.00' --and "checksum" = '-2110250529'
ORDER BY "installed_rank" DESC
;--================================================================================================================
--flyway_issue_fixing_pwh
select * from "pwh_schema_version" where 1=1 and "version" = '000.00.01' --and "checksum" = '1405706381'
;--================================================================================================================
--flyway_issue_fixing_sto
select * from "sto_schema_version" where 1=1 and "version" = '000.00.03' --and "checksum" = '2117573245'
;--================================================================================================================
-- fix the issue by updating values
update "pin_schema_version" set "checksum" = '-1182785108' where "version" = '195.00.00';
update "pwh_schema_version" set "checksum" = '379888526' where "version" = '000.00.01';
update "sto_schema_version" set "checksum" = '2139650600' where "version" = '000.00.03';
commit;
--=================================================================================================================