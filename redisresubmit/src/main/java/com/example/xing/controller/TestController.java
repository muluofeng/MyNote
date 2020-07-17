package com.example.xing.controller;

import com.alibaba.fastjson.JSON;
import com.example.xing.annotation.Resubmit;
import com.example.xing.common.IpUtils;
import com.example.xing.imfiletest.UploadModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiexingxing
 * @Created by 2020-05-13 11:17.
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @PostMapping("save")
    @Resubmit(delaySeconds = 30)
    public Object save(@RequestBody HashMap map) throws InterruptedException {
        System.out.println(map.get("xxx"));
        log.info("保存处理中...");
        Thread.sleep(60*1000);

        return  "保存成功";
    }

    @GetMapping("testip")
    public Object testip(HttpServletRequest request) throws InterruptedException {
        System.out.println("ip:   "+ IpUtils.getIpAdrress(request));
        return  "";
    }


    @PostMapping("upload")
    public Object upload(HttpServletRequest request) throws InterruptedException, IOException {

        int size = request.getContentLength();
        System.out.println(size);


        InputStream is = request.getInputStream();

        byte[] reqBodyBytes = readBytes(is, size);

        if (size > 4) {
            //前4个字节代表json长度

            //  int.available() byteArrayToInt=byteArrayToInt(reqBodyBytes);

            int byteArrayToInt = byteArrayToInt(reqBodyBytes);


            byte[] JsonByte = new byte[byteArrayToInt];
            System.arraycopy(reqBodyBytes, 4, JsonByte, 0, byteArrayToInt);


            byte[] FileByte = new byte[size - byteArrayToInt - 4];
            String jsonDate = new String(JsonByte, "UTF-8");
            System.arraycopy(reqBodyBytes, 4 + byteArrayToInt, FileByte, 0, size - byteArrayToInt - 4);

            UploadModel model = JSON.parseObject(jsonDate, UploadModel.class);


            String Guid = java.util.UUID.randomUUID().toString();
            String saveName = Guid;

            if (model.getFileName() == null || model.getFileName().equals("") || model.getFileName().indexOf(".") < 0) {
                throw new IllegalArgumentException("文件无扩展名！");
            } else {

                //获取后缀名
                String prefix = model.getFileName().substring(model.getFileName().lastIndexOf(".") + 1);

                // boolean isAppend=false;
                String savePath = "";
                byte[] originalByte = null;
                //首次上传
                if (model.getFileUrl() == null || model.getFileUrl() == "") {
                    saveName = saveName + "." + prefix;


                    StringBuffer sbRealPath = new StringBuffer();

                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd/");

                    sbRealPath.append("/attachment/").append(dateformat.format(new Date())).append(model.getUserName());

                    savePath = sbRealPath.toString();

                } else {
                    saveName = model.getFileUrl().substring(model.getFileUrl().lastIndexOf("/") + 1);
                    savePath = model.getFileUrl().substring(0, model.getFileUrl().lastIndexOf("/"));
                    //isAppend=true;
//                    originalByte = userRedisService.getAttachmentRedisBytes(model.getAppName(), saveName);
                }


//                ServletContext sc = request.getSession().getServletContext();
//                String dir = realPath(savePath);    //设定文件保存的目录
//
//                File logoSaveFile = new File(dir);
//                if (!logoSaveFile.exists()) {
//                    logoSaveFile.mkdirs();
//                }
//
//
//                File file = new File(dir, saveName);
                int originalLength = originalByte == null ? 0 : originalByte.length;
                if (originalLength != model.getSrcOffset()) {
                    throw new IllegalArgumentException("文件长度不匹配！");
                }

                //追加文件
//                    FileOutputStream fos = new FileOutputStream(file, isAppend);
//                    fos.write(FileByte, 0, size - byteArrayToInt - 4);
//                    fos.close();
//                    Thread.sleep(1);
                byte[] saveByte = new byte[originalLength + FileByte.length];
                if (originalByte == null) {

                    saveByte = FileByte;
                } else {
                    System.arraycopy(originalByte, 0, saveByte, 0, originalByte.length);
                    System.arraycopy(FileByte, 0, saveByte, originalByte.length, FileByte.length);
                }
            }
        }
        return null;
    }
    /**
     * 数组转换为Int
     *
     * @param
     * @return
     */
    public static int byteArrayToInt(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < 4; i++) {
            intValue += (b[i] & 0xFF) << (8 * i);
        }
        return intValue;
    }


    public static byte[] intToBytes( int value )
    {
        byte[] src = new byte[4];
        src[3] =  (byte) ((value>>24) & 0xFF);
        src[2] =  (byte) ((value>>16) & 0xFF);
        src[1] =  (byte) ((value>>8) & 0xFF);
        src[0] =  (byte) (value & 0xFF);
        return src;
    }


    /**
     * 读取字节
     * @param is
     * @param contentLen
     * @return
     */
    public static byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                // Ignore
                // e.printStackTrace();
            }
        }

        return new byte[]{};
    }




    public static void main(String[] args) throws IOException {

        String fileName = "test.md";
        File file =new File("/Users/xing/Desktop/im-test/"+fileName);

        file.delete();

        if(!file.exists()){
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file);
        UploadModel model = new UploadModel();

        setModelData(model,fileName);

        String s = JSON.toJSONString(model);
        byte[] bytes = s.getBytes("UTF-8");
        int jsonlength = bytes.length;

        byte[] intToBytes = intToBytes(jsonlength);

        String  content  = "12345678";
        byte[] contentBytes = content.getBytes("UTF-8");

//        StringBuilder sn = new StringBuilder();
//        String s1 = sn.append(new String(intToBytes, "UTF-8")).append(new String(bytes, "UTF-8"))
//                .append(new String(contentBytes, "UTF-8")).toString();
//        System.out.println(s1);


        os.write(intToBytes);
        os.write(bytes);
        os.write(contentBytes);

        os.close();



    }

    private static void setModelData(UploadModel model,String fileName) {
        model.setFileName(fileName);
        model.setFileStatus(1);
        model.setFileUrl(null);
        model.setSrcOffset(0);
        model.setTo("8BmEYpm9kQUlNN7m");
        model.setAppToken("verify-code");
        model.setAppName("launchr");
        model.setUserName("ZD1ybAYvAou36nzJ");
        model.setUserToken("c62ed200bb27-072");
    }


}