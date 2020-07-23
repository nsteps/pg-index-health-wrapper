# PG index health wrapper

https://github.com/StepanovNickolay/pg-index-health-wrapper/workflows/CI/badge.svg

This simple app allows you to get index health info from all schemas of specified database.

Based on [pg-index-health](https://github.com/mfvanek/pg-index-health) library and it's [demo](https://github.com/mfvanek/pg-index-health-demo) 

More info about checks and how it works: [pg-index-health](https://github.com/mfvanek/pg-index-health)

# Usage

Specify db connections in application.yml:
```yml
spring:
  datasource:
    url: url
    password: pass
    username: username
```

Run app:
```bash
./gradlew bootRun
```

Output example:
```
2020-07-23 11:02:00.891  INFO 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : Schemas index health
2020-07-23 11:02:00.891  INFO 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : ########################################
2020-07-23 11:02:01.100  INFO 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : --- Schema project_manager ---
2020-07-23 11:02:01.504  WARN 65053 --- [           main] i.g.m.p.c.h.logger.AbstractHealthLogger  : There are foreign keys without index in the database [ForeignKey{tableName='project_manager.element_files', constraintName='fk_element_files_project_element_id', columnsInConstraint=[id_element]}, ForeignKey{tableName='project_manager.element_value', constraintName='fk_element_value_id_element_group_element_id', columnsInConstraint=[id_element]}, ForeignKey{tableName='project_manager.global_elements', constraintName='fk_project_global_id', columnsInConstraint=[id_project_global]}, ForeignKey{tableName='project_manager.group_element', constraintName='fk_group_element_id_group_project_element_id', columnsInConstraint=[id_group]}, ForeignKey{tableName='project_manager.markup', constraintName='fk_markup_id_element_project_element_id', columnsInConstraint=[id_element]}, ForeignKey{tableName='project_manager.markup', constraintName='markup_id_element_value_element_value_element_value_id', columnsInConstraint=[id_element_value]}, ForeignKey{tableName='project_manager.multiselect_attr', constraintName='fk_multiselect_attr_id_element_project_element_id', columnsInConstraint=[id_element]}, ForeignKey{tableName='project_manager.nsi_elements', constraintName='fk_nsi_elements_dict_id_nsi_dict_id', columnsInConstraint=[dict_id]}, ForeignKey{tableName='project_manager.nsi_elements', constraintName='fk_nsi_elements_dict_nik_nsi_dict_nick', columnsInConstraint=[dict_nick]}, ForeignKey{tableName='project_manager.nsi_value', constraintName='fk_nsi_value_element_id_nsi_elements_id', columnsInConstraint=[element_id]}, ForeignKey{tableName='project_manager.organizations_fiz', constraintName='fk_organizations_fiz_id_organization_organizations_id', columnsInConstraint=[id_organization]}, ForeignKey{tableName='project_manager.organizations_legal', constraintName='fk_organizations_legal_id_organization_organizations_id', columnsInConstraint=[id_organization]}, ForeignKey{tableName='project_manager.organizations_user', constraintName='fk_organizations_user_id_organization_organizations_id', columnsInConstraint=[id_organization]}, ForeignKey{tableName='project_manager.participant_user', constraintName='fk_participant_user_id_participant_participant_org_id', columnsInConstraint=[id_participant_org]}, ForeignKey{tableName='project_manager.participant_user', constraintName='fk_participant_user_login_organizations_user_login', columnsInConstraint=[login]}, ForeignKey{tableName='project_manager.project_editing_log', constraintName='project_editing_log_project_id_fk', columnsInConstraint=[id_project]}, ForeignKey{tableName='project_manager.project_element', constraintName='fk_id_project', columnsInConstraint=[id_project]}, ForeignKey{tableName='project_manager.project_element', constraintName='fk_version_project_version_id', columnsInConstraint=[version]}, ForeignKey{tableName='project_manager.project_structure', constraintName='fk_id_project_project_id', columnsInConstraint=[id_project]}, ForeignKey{tableName='project_manager.project_version', constraintName='fk_id_project_project_id', columnsInConstraint=[id_project]}, ForeignKey{tableName='project_manager.template_project', constraintName='fk_template_project_id_parent_template_global_id', columnsInConstraint=[id_parent]}]
2020-07-23 11:02:01.535  WARN 65053 --- [           main] i.g.m.p.c.h.logger.AbstractHealthLogger  : There are tables with missing indexes in the database [TableWithMissingIndex{tableName='project_manager.broker_nsi', tableSizeInBytes=1295745024, seqScans=1219, indexScans=0}, TableWithMissingIndex{tableName='project_manager.nsi_value', tableSizeInBytes=7348224, seqScans=243, indexScans=0}]
2020-07-23 11:02:01.633  WARN 65053 --- [           main] i.g.m.p.c.h.logger.AbstractHealthLogger  : There are indexes with bloat in the database [IndexWithBloat{tableName='project_manager.broker_nsi', indexName='project_manager.broker_nsi_pkey', indexSizeInBytes=1425408, bloatSizeInBytes=1409024, bloatPercentage=99}, IndexWithBloat{tableName='project_manager.nsi_value', indexName='project_manager.nsi_value_pkey', indexSizeInBytes=2334720, bloatSizeInBytes=1998848, bloatPercentage=86}]
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.m.p.c.h.logger.AbstractHealthLogger  : There are tables with bloat in the database [TableWithBloat{tableName='project_manager.broker_nsi', tableSizeInBytes=1295745024, bloatSizeInBytes=998129664, bloatPercentage=77}, TableWithBloat{tableName='project_manager.nsi_value', tableSizeInBytes=7348224, bloatSizeInBytes=4685824, bloatPercentage=64}]
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.345499Z	db_indexes_health	invalid_indexes	0
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.373508Z	db_indexes_health	duplicated_indexes	0
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.400772Z	db_indexes_health	intersected_indexes	0
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.469908Z	db_indexes_health	unused_indexes	0
2020-07-23 11:02:01.669  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.506272Z	db_indexes_health	foreign_keys_without_index	21
2020-07-23 11:02:01.670  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.535606Z	db_indexes_health	tables_with_missing_indexes	2
2020-07-23 11:02:01.670  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.557986Z	db_indexes_health	tables_without_primary_key	0
2020-07-23 11:02:01.670  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.584549Z	db_indexes_health	indexes_with_null_values	0
2020-07-23 11:02:01.670  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.633816Z	db_indexes_health	indexes_bloat	2
2020-07-23 11:02:01.670  WARN 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : 2020-07-23T08:02:01.669447Z	db_indexes_health	tables_bloat	2
2020-07-23 11:02:01.100  INFO 65053 --- [           main] i.g.s.pghealthindexwrapper.PgAnalyzer    : --- Schema files ---
...

```