package cn.glor.xiaolun.app.util;

import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by heylear on 15/11/3.
 */
public class FileUtils {
    public static void download(String rootPath, String filePath, OutputStream out) throws IOException{
        File file = new File(rootPath + filePath);
        if(file.exists()){
            FileCopyUtils.copy(new FileInputStream(file),out);
        }
    }
}
