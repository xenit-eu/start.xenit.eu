package eu.xenit.alfred.initializr.generator.model;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Imports {

    private final List<Import> statements = new ArrayList<>();

    private final String language;

    private boolean finalCarriageReturn;

    public Imports(String language) {
        this.language = language;
    }

    public Imports add(String type) {
        return this.add(new Import(type, false));
    }

    public Imports addStatic(String type) {
        this.add(new Import(type, true));
        return this;
    }

    private Imports add(Import imp) {
        this.statements.add(imp);
        return this;
    }

    public Imports withFinalCarriageReturn() {
        this.finalCarriageReturn = true;
        return this;
    }

    @Override
    public String toString() {
        if (this.statements.isEmpty()) {
            return "";
        }

        String content = this.statements.stream()
                .map(imp -> imp.getStatement(this.language))
                .collect(Collectors.joining(String.format("%n")));

        return (this.finalCarriageReturn ? String.format("%s%n", content) : content);
    }

    @Value
    public class Import {

        private final String type;
        private final boolean isStatic;

        public String getStatement(String language) {
            String end = (("groovy".equals(language) || "kotlin".equals(language)) ? "" : ";");
            String staticMod = this.isStatic ? "static " : "";
            return "import " + staticMod + type + end;
        }

    }

}
