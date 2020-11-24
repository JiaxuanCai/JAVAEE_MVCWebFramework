package test;

//import annotation.GetMapping;
import annotation.*;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


@MyController
@MyRequestMapping("/file")
public class FileController {


    @MyRequestMapping(value = "/upload")
    @ResponseView
    public String upload_page() {
        return "upload";
    }


    @MyRequestMapping(value = "/upload-success")
    @ResponseBody
    public String upload(@MyRequestParam("file") File source) {

        String fileName = source.getName();
        String path = "/Users/patrickdd/Projects/IdeaProjects/MySpringMVC/temp/";
        File dest = new File(path + fileName);
        try {
            FileUtils.copyFile(source, dest);
            return "Success!";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failure!";
    }
}
