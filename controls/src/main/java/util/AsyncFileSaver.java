package util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncFileSaver {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(AsyncFileSaver.class);
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    private final Runnable saveRunner = () -> saveFile();

    private final String path;
    private final byte[] bytes;

    public AsyncFileSaver(String path, byte[] bytes) {

        this.path = path;
        this.bytes = bytes;
    }

    public void save() {
        LOGGER.info(CommonUtil.logPrefix() + "Saving screenshot async " + path);
        executor.submit(saveRunner);
    }

    private void saveFile() {
        File f = new File(path);
        try {
            FileUtils.writeByteArrayToFile(f, bytes);
            LOGGER.info(CommonUtil.logPrefix() + "Saved screenshot async " + f.getAbsolutePath());
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}