package eu.xenit.alfred.initializr.web.project;

import eu.xenit.alfred.initializr.generator.buildsystem.BuildAssetWriter;
import io.spring.initializr.generator.buildsystem.BuildWriter;
import io.spring.initializr.generator.project.ProjectAssetGenerator;
import io.spring.initializr.generator.project.ProjectGenerationContext;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;

public class BuildAssetGenerator implements ProjectAssetGenerator<Map<Path, String>> {

    @Override
    public Map<Path, String> generate(ProjectGenerationContext context) throws IOException {
        ResolvedProjectDescription projectDescription = context.getBean(ResolvedProjectDescription.class);

//        ResolvedProjectDescription projectDescription = context
//                .getBean(ResolvedProjectDescription.class);
        //StringWriter out = new StringWriter();

        ObjectProvider<BuildAssetWriter> buildAssetWriters = context.getBeanProvider(BuildAssetWriter.class);

        Map<Path, String> result = buildAssetWriters.orderedStream().collect(Collectors.toMap(
                BuildAssetWriter::relativePath,
                this::writeBuildAsset));

        return result;

    }

    private String writeBuildAsset(BuildAssetWriter buildAssetWriter) {
        try {
            StringWriter out = new StringWriter();
            buildAssetWriter.writeBuild(out);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    private Map<String, String> getBuildAssets(List<BuildAssetWriter> buildAssetWriters) {
//        Map<String, String>
//        for(BuildAssetWriter buildAssetWriter : buildAssetWriters) {
//
//        }
//    }
//
//        for (Iterator<BuildAssetWriter> it = buildAssetWriters.iterator(); it.hasNext(); ) {
//            BuildAssetWriter buildAssetWriter = it.next();
//
//        }
//            StringWriter out = new StringWriter();
//            buildAssetWriter.writeBuild(out);
//            result.putIfAbsent(buildAssetWriter.relativePath(),
//
//            buildAssetWriter.
//        });
//
//        if (buildWriter != null) {
//            buildWriter.writeBuild(out);
//            return out.toString().getBytes();
//        } else {
//            throw new IllegalStateException("No BuildWriter implementation found for "
//                    + projectDescription.getLanguage());
//        }
////        Path projectRoot = context.getBean(ProjectDirectoryFactory.class)
////                .createProjectDirectory(resolvedProjectDescription);
////        Path projectDirectory = initializerProjectDirectory(projectRoot,
////                resolvedProjectDescription);
//
//
////        List<ProjectContributor> contributors = context
////                .getBeanProvider(ProjectContributor.class).orderedStream()
////                .collect(Collectors.toList());
////        for (ProjectContributor contributor : contributors) {
////            contributor.contribute(projectDirectory);
////        }
////        return projectRoot;
//        return result;
//    }

}
