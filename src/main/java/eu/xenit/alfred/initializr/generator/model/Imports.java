package eu.xenit.alfred.initializr.generator.model;

import java.util.ArrayList;
import java.util.List;

public class Imports {

    private final List<String> statements = new ArrayList<>();

    private final String language;

    private boolean finalCarriageReturn;

    public Imports(String language) {
        this.language = language;
    }

    public Imports add(String type) {
        this.statements.add(generateImport(type, this.language));
        return this;
    }

    public Imports withFinalCarriageReturn() {
        this.finalCarriageReturn = true;
        return this;
    }

    private String generateImport(String type, String language) {
        String end = (("groovy".equals(language) || "kotlin".equals(language)) ? ""
                : ";");
        return "import " + type + end;
    }

    @Override
    public String toString() {
        if (this.statements.isEmpty()) {
            return "";
        }
        String content = String.join(String.format("%n"), this.statements);
        return (this.finalCarriageReturn ? String.format("%s%n", content) : content);
    }

}
