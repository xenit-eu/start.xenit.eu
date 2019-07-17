package eu.xenit.alfred.initializr.generator.build;

/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import eu.xenit.alfred.initializr.generator.buildsystem.Build;
import io.spring.initializr.generator.project.ResolvedProjectDescription;
import org.springframework.core.Ordered;

/**
 * Customize the {@link Build} as early as possible based on the information held in the
 * {@link ResolvedProjectDescription}.
 *
 */
public class SimpleBuildCustomizer implements BuildCustomizer<Build> {

    private final ResolvedProjectDescription projectDescription;

    public SimpleBuildCustomizer(ResolvedProjectDescription projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Override
    public void customize(Build build) {
        build.setGroup(this.projectDescription.getGroupId());
        build.setArtifact(this.projectDescription.getArtifactId());
        build.setVersion(this.projectDescription.getVersion());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
