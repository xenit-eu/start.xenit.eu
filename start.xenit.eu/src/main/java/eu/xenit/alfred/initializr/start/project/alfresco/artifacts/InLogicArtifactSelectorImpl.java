package eu.xenit.alfred.initializr.start.project.alfresco.artifacts;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;

public class InLogicArtifactSelectorImpl implements AlfrescoVersionArtifactSelector {

    private final ProjectDescription projectDescription;

    public InLogicArtifactSelectorImpl(ProjectDescription projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public String getAlfrescoVersion() {
        Version version = projectDescription.getPlatformVersion();
        if (!isCommunity()) {
            return version.toString();
        }
        if (version.getPatch() == 0) {
            return version.getMajor() + "." + version.getMinor() + "." + version.getQualifier().getQualifier();
        }
        return version.getMajor() + "." + version.getMinor() + "." + version.getPatch() + "-" + version.getQualifier().getQualifier();
    }

    @Override
    public String getAlfrescoArtifactId() {
        if (isCommunity()) {
            if (isSixOrAbove()) {
                return "content-services-community";
            }
            return "alfresco-platform";
        }
        if (isSixOrAbove()) {
            return "content-services";
        }
        return "alfresco-enterprise";
    }

    @Override
    public String getDockerRegistry() {
        if (isCommunity()) {
            return "hub.xenit.eu/public";
        }
        return "hub.xenit.eu/alfresco-enterprise";
    }

    @Override
    public String getAlfrescoDockerImage() {
        if (isCommunity()) {
            return "alfresco-repository-community";
        }
        return "alfresco-enterprise";
    }

    private boolean isCommunity() {
        return projectDescription.getPlatformVersion().getQualifier().getQualifier().matches("[a-g]*");
    }

    private boolean isSixOrAbove() {
        return projectDescription.getPlatformVersion().getMajor() >= 6;
    }

}
