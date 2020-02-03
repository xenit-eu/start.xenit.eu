# XeniT Initializr

XeniT Initializr is a quickstart generator to bootstrap your Alfresco projects.

The concept is similar to the [Alfresco Maven SDK](https://github.com/Alfresco/alfresco-sdk) archetypes, with the following differences:
* there are a lot more options you can choose: Alfresco version, edition, dependencies, project metadata, examples, etc ...
* both the [Alfresco Maven SDK](https://github.com/Alfresco/alfresco-sdk) and the [Alfresco Gradle SDK](https://github.com/xenit-eu/alfresco-gradle-sdk) by XeniT are supported
* it's service based (powered by [Spring Initializr](https://github.com/spring-io/initializr), not based on maven archetypes

## Status

Work in progress:

* the Gradle build is functional
* need to add more Alfresco versinos
* the Maven build is totally non-existing at this point

## Usage

1. Go to [start.xenit.eu](https://start.xenit.eu)
2. Select your preferred settings and click generate project
3. Unzip the downloaded file and import the unzipped project into your favourite IDE.
4. You are now ready to start developing your Alfresco project.

Alternatively, try out `curl https://start.xenit.eu` and follow the instructions

## Contribute

### Build

Steps:  
* Clone the project
* Run the gradle `bootRun` task
* The dashboard is now available at `localhost:8080`

### Dashboard

Steps:
* Use your favourite browser to visit `localhost:8080`.
* Select your preferred settings using the UI.
* Click `Generate Project`, save it to your preferred folder.
* Unzip the downloaded file and import the unzipped project into your favourite IDE.
* You are now ready to start developing your Alfresco project.

### Curl

Steps:
* Execute `curl localhost:8080`.
* You are presented with some documentation on the available parameters and some usage examples.
* Save the response from your self-crafted curl request (a zip) to your preferred folder.
* Unzip the downloaded file and import the unzipped project into your favourite IDE.
* You are now ready to start developing your Alfresco project.
