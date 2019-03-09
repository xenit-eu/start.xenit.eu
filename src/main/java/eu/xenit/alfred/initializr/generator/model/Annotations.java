package eu.xenit.alfred.initializr.generator.model;

import java.util.ArrayList;
import java.util.List;

public class Annotations {

    private final List<String> statements = new ArrayList<>();

    private boolean finalCarriageReturn;

    public Annotations add(String type) {
        this.statements.add(type);
        return this;
    }

    public Annotations withFinalCarriageReturn() {
        this.finalCarriageReturn = true;
        return this;
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