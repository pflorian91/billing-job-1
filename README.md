# Billing job

Processes the given input
- copies the csv in /staging/.csv
- ingests data in db
- create reports in `staging` dir


## How to run

- requires postgres available on the localhost
- modify the datasource properties accordingly

Scripts in `scripts` dir can be executed to create and clean the schema.


Package the app

> ./mvnw package -Dmaven.test.skip=true

### Run different input files 

> java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=input/billing-2023-01.csv output.file=staging/billing-report-2023-01.csv data.year=2023 data.month=1

> java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=input/billing-2023-02.csv output.file=staging/billing-report-2023-02.csv data.year=2023 data.month=2

-- will skip items, see .psv
> java -jar target/billing-job-0.0.1-SNAPSHOT.jar input.file=input/billing-2023-03.csv output.file=staging/billing-report-2023-03.csv skip.file=staging/billing-data-skip-2023-03.psv data.year=2023 data.month=3


### Run tests
> ./mvnw clean test -Dspring.batch.job.enabled=false
