package eu.xenit.alfred.initializr.start.project.alfresco.artifacts;

import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.generator.version.Version.Qualifier;
import org.springframework.util.StringUtils;

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
            return version.getMajor() + "." + version.getMinor() + "." + version.getQualifier().getId();
        }
        return version.getMajor() + "." + version.getMinor() + "." + version.getPatch() + "-" + version.getQualifier().getId();
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
        Qualifier qualifier = projectDescription.getPlatformVersion().getQualifier();
        if (StringUtils.isEmpty(qualifier)) {
            return false;
        }
        return qualifier.getId().matches("[a-g]*");
    }

    private boolean isSixOrAbove() {
        return projectDescription.getPlatformVersion().getMajor() >= 6;
    }

}
