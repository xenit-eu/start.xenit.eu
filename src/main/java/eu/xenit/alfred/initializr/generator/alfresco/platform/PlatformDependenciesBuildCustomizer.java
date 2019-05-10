package eu.xenit.alfred.initializr.generator.alfresco.platform;

import eu.xenit.alfred.initializr.generator.build.BuildCustomizer;
import io.spring.initializr.generator.buildsystem.BuildItemResolver;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrMetadata;

class PlatformDependenciesBuildCustomizer implements BuildCustomizer<PlatformBuild> {

    private AlfrescoPlatformModule module;
    private InitializrMetadata metadata;
    private final ResolvedProjectDescription projectDescription;
    private final BuildItemResolver depResolver;

    public PlatformDependenciesBuildCustomizer(AlfrescoPlatformModule module, InitializrMetadata metadata,
            ResolvedProjectDescription projectDescription, BuildItemResolver depResolver) {
        this.module = module;
        this.metadata = metadata;

        this.projectDescription = projectDescription;
        this.depResolver = depResolver;
    }


    @Override
    public void customize(PlatformBuild build) {
        

            projectDescription.getRequestedDependencies()
                    .keySet().stream().filter(depKey -> {
                Dependency reqDepMetadata = this.metadata.getDependencies().get(depKey);
                return reqDepMetadata != null && reqDepMetadata.getFacets().contains("platform");
            }).forEach(dependencyId -> {

                build.dependencies()
                        .add(dependencyId, this.depResolver.resolveDependency(dependencyId));
            });


//         get requested dependencies, filter on facets contains platform
//                    projectDescription.getRequestedDependencies().keySet().stream().filter(dependencyKey -> {
//                        resolver.get
//
//                    });
//
//                    subproject.dependencies().add()
    }
}
