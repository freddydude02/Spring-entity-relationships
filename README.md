# Spring-entity-relationships

**Aim:**
Build a Resourcing API using the Java Spring Boot framework, that allows consumers to assign temps to jobs.

**Assumptions/Specifications:**
- Temps can only have one job at a time (can’t be doing 2 jobs on the same date)
- Temps can have many jobs, and job can have 1 temp assigned
- Should be able to assign existing temps to jobs via POST /jobs & PATCH /jobs/{id}
- must use a relational database(MySQL)
- APIs are secured using JWT Auth

**SetUp**
- Clone the Repo
- navigate to /src/main/resources/application.properties
- replace spring.datasource.url with own SQL database url
- edit the respective username and password
- save and run application
- tests API via postman or swagger

swagger URL:
- http://localhost:8080/test/swagger-ui/index.html

**Endpoints:**  
-AUTH-
- POST /auth/signup - creates a new user (for authentication purposes)
- POST /auth/login - given the correct login info, returns a bearer token to authenticate other requests

-JOB-
- POST /jobs - Creates a job
- GET /jobs - Fetch all jobs
- GET /jobs?assigned={true|false} - Filter by whether a job is assigned to a temp or not
- GET /jobs/{id} - (id, name, tempId, startDate, endDate)
- PATCH /jobs/{id} - Updates job, endpoint should be used to assign temps to jobs

-TEMP-
- POST /temps - Create a temp
- GET /temps - Fetch all temps
- GET /temps?jobId={jobId} - List temps that are available for a job based on the jobs date range
- GET /temps/{id} - get temp by id (should also display jobs they’ve been assigned to)
- PATCH /temps/{id} - Updates temp information

Approach:
- each controller returned a suitable response status:
to achieve this, the class Optional<> was used so to avoid calling medthods on a null value.
This helped to prevent any run-time exceptions that may have occured had I used the temp
or job entities themselves.

- validating the assignment of jobs to temps based off overlapping dates:
in order to check whether a temp is available for a job, I had to ensure the
existing jobs that the temp already has assigned to them, does not overlap the
start and end date of the prospective job. my approach to check this is to exit as
early as possible.  
Assuming: A = Assigned job list, P = prospective job, S = start date, E = end date

1. if temp has any pre-existing jobs assigned  
   (exits early if temp doesn't have job assigned already)  
    A: {}  
    P: [PS, PE]  
    returns: {[PS, PE]}
    
1.5. else sort Assigned job list from earliest to lastest

2. if P date range falls outside of the first or last assigned date ranges  
    A: {[A0S, A0E], [A1S - A1E]}  
    P: [PS, PE]  
    If:  PE is before A0S or PS is after A1E  
    Returns:  
     {[PS, PE], [A0S, A0E], [A1S - A1E]}  
      or  
     {[A0S, A0E], [A1S - A1E], [PS, PE]}
  
3. iterate through the temp's assigned jobs. finding the date range between the end date
   of the first job and start date of the next. Then checking if the prospective job fits
   within that date range  
    A: {[A0S, A0E], [A1S - A1E]}  
    P: [PS, PE]  
    If: PS is after A0E and PE is before A1S  
    Returns: {[A0S, A0E], [PS, PE] ,[A1S - A1E]}

4. if all logic is passed the job is not assigned

**Future changes:**
- ensure deletion of assigned jobs do not cascade to other jobs and the temp it was assigned to
- establish a one-to-one relationship with entities user(auth) and temp for access to specific temp information upon login
- add api tests
