package app.service;

import mymvc.annotation.MyService;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
@MyService
public class AppService {


    public boolean uploadFile(FileItem source, String path) {
        String fileName = source.getName();
        File dest = new File(path + fileName);
        try {
            source.write(dest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
