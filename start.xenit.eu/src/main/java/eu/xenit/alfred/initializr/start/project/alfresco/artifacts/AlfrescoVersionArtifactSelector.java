package eu.xenit.alfred.initializr.start.project.alfresco.artifacts;

public interface AlfrescoVersionArtifactSelector {

    String getAlfrescoVersion();
    String getAlfrescoArtifactId();
    String getDockerRegistry();
    String getAlfrescoDockerImage();

}
