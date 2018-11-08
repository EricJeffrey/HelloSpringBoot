package com.ericjeffrey.HelloSpringBoot.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication.WORD_DIR_PATH;

@RestController
public class FileController {
    private static final String UPLOADED_FILE_PATH = WORD_DIR_PATH + "uploadedFiles/";
    private static final String DELETED_FILE_PATH = WORD_DIR_PATH + "deletedFiles/";

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 上传成功或失败信息
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        if (file.isEmpty()) {
            try {
                response.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "文件为空";
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            try {
                response.sendRedirect("/index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "文件名为空";
        }

//        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        File destination = new File(UPLOADED_FILE_PATH + fileName);
        if (!destination.getParentFile().exists()) {
            if (!destination.getParentFile().mkdirs()) {
                try {
                    response.sendRedirect("/index");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "文件创建失败";
            }
        }

        try {
            file.transferTo(destination);
            response.sendRedirect("/index");
            return "上传成功";
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    /**
     * 列出所有文件及其最后修改时间
     *
     * @return 文件名以及最后修改时间
     */
    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFiles() {
        File dir = new File(UPLOADED_FILE_PATH);
        if (!dir.isDirectory())
            return "文件夹打开错误";
        File[] allFiles = dir.listFiles();
        if (allFiles == null)
            return "文件打开错误";
        if (allFiles.length <= 0)
            return null;
        StringBuilder resBuilder = new StringBuilder("[");
        for (int i = 0; i < allFiles.length; i++) {
            File tmp = allFiles[i];
            resBuilder.append("{");
            resBuilder.append(String.format("\"name\": \"%s\", \"time\":\"%s\" ",
                    tmp.getName(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(tmp.lastModified()))));
            resBuilder.append("}");
            if (i < allFiles.length - 1)
                resBuilder.append(",");
        }
        resBuilder.append("]");
        return resBuilder.toString();
    }

    /**
     * 下载文件
     *
     * @param name     文件名
     * @param response 响应体
     * @return 文件未找到或下载成功
     */
    @RequestMapping(value = "fileDetail", method = RequestMethod.GET)
    public String downloadFile(@RequestParam(name = "name") @RequestBody String name, HttpServletResponse response) {
        File file = new File(UPLOADED_FILE_PATH, name);
        if (file.exists()) {
            downloadTransfer(file, response);
            return "下载成功";
        }
        return "文件未找到";
    }

    /**
     * 下载文件 - 传输文件内容
     * @param file 文件
     * @param response 响应体
     */
    public static void downloadTransfer(File file, HttpServletResponse response) {
        response.setContentType("application/force-download");
        try {
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "UTF-8"));
            response.setContentLengthLong(file.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null)
                    bis.close();
                if (fis != null)
                    fis.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除一个文件
     *
     * @param name     文件名
     * @param response 响应体
     * @return 文件删除成功或失败
     */
    @RequestMapping(value = "deleteFile", method = RequestMethod.GET)
    public String deleteFile(@RequestParam(name = "name") @RequestBody String name, HttpServletResponse response) {
        File file = new File(UPLOADED_FILE_PATH, name);
        if (file.exists()) {
            try {
                Files.move(file.toPath(), new File(DELETED_FILE_PATH + name).toPath(), StandardCopyOption.REPLACE_EXISTING);
                response.sendRedirect("index");
                return "删除成功";
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                response.sendRedirect("index");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "删除失败";
        }
        try {
            response.sendRedirect("index");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "文件不存在";
    }
}
