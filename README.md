# XeniT Initializr

## Introduction
XeniT Initializr is a tool that allow you to quickly bootstrap an Alfresco project.
It targets the 
[Alfresco Maven SDK](https://github.com/Alfresco/alfresco-sdk)
or the 
[Alfresco Gradle SDK](https://github.com/xenit-eu/alfresco-gradle-sdk) 
but is a lot more interactive. You can pick the Alfresco version, dependencies, set project metadata and start developing immediately.

##  Build

Steps:  
* Clone the project
* Run the gradle `bootRun` task
* The dashboard is now available at `localhost:8080`

## Usage

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