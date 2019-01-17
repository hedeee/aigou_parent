package org.hedee.aigou.controller;

import org.hedee.aigou.util.AjaxResult;
import org.hedee.aigou.util.FastDfsApiOpr;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FastDfsController {
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public AjaxResult upload(@RequestParam(value = "file",required = true)MultipartFile file){
        try {
            //拿到名称
            String originalFilename = file.getOriginalFilename();
            //截取后缀
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //生成上传文件
            String filePath = FastDfsApiOpr.upload(file.getBytes(), extName);
            //返回ajax数据
            return AjaxResult.getAjaxResult().setResultObj(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.getAjaxResult().setSuccess(false).setMessage("上传失败"+e.getMessage());
        }
    }
    //删除logo
    @RequestMapping(value = "/del",method = RequestMethod.DELETE)
    public AjaxResult del(@RequestParam(value = "filePath",required = true)String filePath){
        //Storage 返回的路径最前方有斜杠，去掉
        String basicPath = filePath.substring(1);
        //拿到前面的groupName,从头开始取值直到第一个斜杠
        String groupName = filePath.substring(0, basicPath.indexOf("/"));
        //再拿到fileName,从第一个斜杠之后开始往后取
        String fileName = basicPath.substring(basicPath.indexOf("/")+1);
        //调用工具类方法
        FastDfsApiOpr.delete(groupName, fileName);

        return AjaxResult.getAjaxResult();
    }
}
