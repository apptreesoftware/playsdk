package sdk.utils;

import net.coobird.thumbnailator.Thumbnailator;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

public class ImageUtils {
    public static ImageUtils imageUtils;

    public static ImageUtils getInstance() {
        if (imageUtils == null) {
            imageUtils = new ImageUtils();
        }
        return imageUtils;
    }


    public File resizeImage(File file, int width, int height) throws IOException {
        if (!validFile(file)) {
            throw new RuntimeException("The image file you are trying to resize is invalid.");
        }
        return resizeImageToWidthAndHeight(file, width, height);
    }


    private File resizeImageToWidthAndHeight(File imageFile, int width, int height) throws IOException {
        File outTempFile = File.createTempFile(UUID.randomUUID().toString(), ".jpg");
        outTempFile.deleteOnExit();
        Thumbnailator.createThumbnail(imageFile, outTempFile, width, height);
        return outTempFile;
    }


    public boolean exceedsSize(File file, long size) {
        return (file.length() > size);
    }

    private boolean validFile(File file) {
        return (file != null && file.length() > 0);
    }


}
