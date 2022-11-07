# SoftwareEngineeringMetrics
This project is to develop a dashboard for the presentation of a range of metrics and visualisations useful to software engineers.

## Setup Instructions
### Run Version on GitHub

To run the version on GitHub, clone the repo to your desktop. Open a terminal in the directory of the repo. Then, run

    ./mvnw clean install spring-boot:run   

Then, you can open the web app on localhost:8080. This will also run tests!

### Run Containerised

If you want to run the web app in a container, first clone the repo to your desktop. Open a terminal in the directory of the repo. Then, run

    ./mvnw clean install spring-boot:run  

This will create the jar file. Then, run the following to build the image.

    docker build -t metrics-image . 

To run the webapp, run

    docker run --name=metrics-container --rm -d -p 8080:8080 metrics-image

Then, you can open the web app on localhost:8080.