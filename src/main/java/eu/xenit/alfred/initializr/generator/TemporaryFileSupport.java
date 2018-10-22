package eu.xenit.alfred.initializr.generator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TemporaryFileSupport {

    @Value("${TMPDIR:.}/initializr")
    private String tmpdir;

    private File temporaryDirectory;

    private transient Map<String, List<File>> temporaryFiles = new LinkedHashMap<>();

    public File getTemporaryDirectory() {
        if (this.temporaryDirectory == null) {
            this.temporaryDirectory = new File(this.tmpdir, "initializr");
            this.temporaryDirectory.mkdirs();
        }
        return this.temporaryDirectory;
    }

    public void addTempFile(String group, File file) {
        this.temporaryFiles.computeIfAbsent(group, (key) -> new ArrayList<>()).add(file);
    }

    /**
     * Clean all the temporary files that are related to this root directory.
     * @param dir the directory to clean
     */
    public void cleanTempFiles(File dir) {
        List<File> tempFiles = this.temporaryFiles.remove(dir.getName());
        if (!tempFiles.isEmpty()) {
            tempFiles.forEach((File file) -> {
                if (file.isDirectory()) {
                    FileSystemUtils.deleteRecursively(file);
                }
                else if (file.exists()) {
                    file.delete();
                }
            });
        }
    }
}
