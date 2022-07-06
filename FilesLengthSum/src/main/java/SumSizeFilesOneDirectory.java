
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;


public class SumSizeFilesOneDirectory extends SimpleFileVisitor<Path> {


    private long countFiles;
    private long sumSizeFiles;
    private long countFilesFailed;

    private final boolean showProcessScanFolder;

    public static Logger logger = (Logger) LogManager.getRootLogger();

    public SumSizeFilesOneDirectory(boolean visibleFilesPathAndSize) {
        this.showProcessScanFolder = visibleFilesPathAndSize;
        this.countFiles = 0;
        this.sumSizeFiles = 0;
        this.countFilesFailed = 0;
    }

    public long getCountFiles() {
        return countFiles;
    }

    public long getSumSizeFiles() {
        return sumSizeFiles;
    }

    public long getCountFilesFailed() {
        return countFilesFailed;
    }


    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        countFilesFailed++;
        String msg = "\tОшибка чтения директории " + exc.getMessage();
        if (showProcessScanFolder) {
            System.err.println(msg);
        }
        logger.info(msg);
        return FileVisitResult.SKIP_SUBTREE;
    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (attrs.isRegularFile()) {
            countFiles++;
            long length = file.toFile().length();
            sumSizeFiles += length;
            if (showProcessScanFolder) {
                System.out.println("\tФайл \"" + file.getFileName() + "\" весит - " + length + " байт");
            }
        }
        return FileVisitResult.CONTINUE;
    }


}


