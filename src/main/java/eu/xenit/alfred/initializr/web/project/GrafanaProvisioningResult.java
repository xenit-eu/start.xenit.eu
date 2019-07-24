package eu.xenit.alfred.initializr.web.project;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class GrafanaProvisioningResult {

    private final Map<String, String> dataSources;
    private final Map<String, String> dashboards;


}
