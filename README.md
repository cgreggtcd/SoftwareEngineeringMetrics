# SoftwareEngineeringMetrics
This project is to develop a dashboard for the presentation of a range of metrics and visualisations useful to software engineers.

## Setup Instructions

If you haven't done this yet, create a file named `application.properties` and inside, put the following

    github.access.token=<your-github-access-token>
    
You can make a GitHub access token as described [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-fine-grained-personal-access-token).

Then, usually, you can run by running run.bat or just typing `docker-compose up` into terminal. If you're making changes to the app however, you will need to run setup.bat or type `docker-compose build` then `docker-compose up` into terminal.
