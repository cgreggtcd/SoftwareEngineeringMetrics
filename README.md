# SoftwareEngineeringMetrics
This project is to develop a dashboard for the presentation of a range of metrics and visualisations useful to software engineers.

## Setup Instructions

Now that a database has been added, the best way to run this is using docker-compose, as that will set up the database and the web app at the same time.

To do this, just run:

    docker-compose up

This will take a long time, especially the first time you run it. This also runs all tests.

Once the output says `Started SoftwareEngineeringMetricsApplication`, open the web app in your browser at localhost:8080