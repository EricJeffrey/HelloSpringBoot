package com.ericjeffrey.HelloSpringBoot.controller.xlproject;

import com.ericjeffrey.HelloSpringBoot.controller.FileController;
import com.ericjeffrey.HelloSpringBoot.model.FileInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ericjeffrey.HelloSpringBoot.HelloSpringBootApplication.XL_DIR;

@RestController
public class XLFileController {
    private static final Integer ERROR_CODE = -1;
    private static final Integer SUCCESS_CODE = 1;

    /**
     * 上传文件FileInfo
     *
     * @param file 文件
     * @param id   社团 ID
     * @return 上传成功或失败信息
     */
    @PostMapping(value = "/uploadFileXL")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String id) {
        String fileName = file.getOriginalFilename();
        File destination = new File(XL_DIR + id + "/" + fileName);
        if (!destination.getParentFile().exists()) {
            if (!destination.getParentFile().mkdirs()) {
                return ERROR_CODE.toString();
            }
        }
        try {
            file.transferTo(destination);
            return SUCCESS_CODE.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_CODE.toString();
    }

    /**
     * 下载文件
     *
     * @param name     文件名
     * @param response 响应体
     * @param id       社团 id
     * @return 文件未找到或下载成功
     */
    @RequestMapping(value = "/fileDetailXL", method = RequestMethod.GET)
    public String downloadFile(@RequestParam(name = "name") @RequestBody String name, @RequestParam(name = "id") @RequestBody String id, HttpServletResponse response) {
        File file = new File(XL_DIR + id, name);
        if (file.exists()) {
            FileController.downloadTransfer(file, response);
            return SUCCESS_CODE.toString();
        }
        return ERROR_CODE.toString();
    }

    @RequestMapping(value = "/deleteFileXL")
    public String deleteFile(@RequestParam(name = "id") @RequestBody String id, @RequestParam(name = "name") @RequestBody String name) {
        File file = new File(XL_DIR + id + "/" + name);
        if (!file.exists()) {
            return SUCCESS_CODE.toString();
        }
        if (file.delete())
            return SUCCESS_CODE.toString();
        return ERROR_CODE.toString();
    }

    /**
     * 列出所有文件及其最后修改时间
     *
     * @return 文件名以及最后修改时间
     */
    @RequestMapping(value = "/getFileListXL", method = RequestMethod.GET)
    public String getFiles(@RequestParam(name = "id") @RequestBody String id) throws JsonProcessingException {
        File dir = new File(XL_DIR + id);
        if (!dir.exists())
            if (!dir.mkdirs()){
                loge("目录不存在");
                return null;
            }
        if (!dir.isDirectory()) {
            loge("文件夹打开错误");
            return "文件夹打开错误";
        }
        File[] allFiles = dir.listFiles();
        if (allFiles == null) {
            loge("文件打开错误");
            return "文件打开错误";
        }
        List<FileInfo> fileInfos = new ArrayList<>();
        for (File tmp : allFiles) {
            FileInfo info = new FileInfo(tmp.getName(), tmp.length(), tmp.lastModified());
            fileInfos.add(info);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(fileInfos);
    }

    // todo delete
    private void loge(String info) {
        System.err.print(info);
    }
}
