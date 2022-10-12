package com.dubhe.jooq.controller;

import com.dubhe.jooq.model.tables.pojos.Book;
import com.dubhe.jooq.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@Controller
public class BookFileController {
    private static Logger log = LoggerFactory.getLogger(BookFileController.class);

    @PostMapping("/books/upload")
    @ResponseBody
    public String multiUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] names = parameterMap.get("name");
        for (String name : names) {
            log.info("get name: {}.", name);
        }
        String filePath = "/Users/davis/it/projects/Dubhe/jooq/file/";
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                log.info("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                log.error(e.toString(), e);
                return "上传第" + (i++) + "个文件失败";
            }
        }

        return "上传成功";

    }
}
