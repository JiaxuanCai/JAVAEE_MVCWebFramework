package test;

//import annotation.GetMapping;
import annotation.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


@MyController
@MyRequestMapping("/test")
public class FileController {

    @MyRequestMapping(value = "/get")
    @ResponseBody
    public String get(@MyRequestParam("p1") String p1, @MyRequestParam("p2") String p2){
        return "p1="+p1 + ",p2="+p2;
    }


    @MyRequestMapping(value = "/upload")
    @ResponseView
    public String upload_page() {
        return "upload";
    }


    @MyRequestMapping(value = "/upload-success")
    @ResponseBody
    public String upload(@MyRequestParam("file") FileItem source) {

        String fileName = source.getName();
        String path = "/Users/patrickdd/Projects/IdeaProjects/MySpringMVC/temp/";
        File dest = new File(path + fileName);
        try {
            source.write(dest);
//            FileUtils.copyFile(source, dest);
            return "Success!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Failure!";
    }
}