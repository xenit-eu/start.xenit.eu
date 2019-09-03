package eu.xenit.alfred.initializr.generator.io;

import io.spring.initializr.generator.io.IndentingWriter;

public class YmlWriter {

    private final IndentingWriter writer;

    private boolean prependIndent = false;
    private boolean printSequenceIndicator = false;

    private final char[] SEQUENCE_INDICATOR = "- ".toCharArray();
    private final char[] TWO_SPACES = "  ".toCharArray();

    public YmlWriter(IndentingWriter writer) {
        this.writer = writer;
    }

    public void println() {
        this.writer.println();
        this.prependIndent = true;
    }

    public void println(String string) {
        write(string.toCharArray(), 0, string.length());
        println();
    }

    public void indented(Runnable runnable) {
        this.writer.indented(runnable);
    }

    public void sequenceElement(Runnable runnable) {
        startSequence();
        runnable.run();
        endSequence();
    }

    private void startSequence() {
        this.prependIndent = true;
        this.printSequenceIndicator = true;
    }

    private void endSequence() {
        this.prependIndent = false;
    }

    public void write(char[] chars, int offset, int length) {
        if (this.prependIndent) {
            if (this.printSequenceIndicator) {
                this.writer.write(SEQUENCE_INDICATOR, 0, SEQUENCE_INDICATOR.length);
                this.printSequenceIndicator = false;
            } else {
                this.writer.write(TWO_SPACES, 0, TWO_SPACES.length);
            }
            this.prependIndent = false;
        }

        this.writer.write(chars, offset, length);
    }
}
