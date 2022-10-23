# Spring-entity-relationships

A resourcing API using Spring boot. The app has two entities Temp and Job. The aim of this project is to allow consumers to assign temps to jobs.
[insert mvp details]
[insert payload]

known issues:
- delete method returns status 500 instead of desired 404
- update method returns status 500 instead of 404
- deleting job that has been assigned to temp deletes temp and other respectively assigned jpbs

Future changes:
- ensure each method returns a suitable response status
- ensure deletion of assigned jobs do not cascade to other jobs and the temp it was assigned to

