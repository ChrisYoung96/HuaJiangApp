package com.chrisyoung.huajiangapp.uitils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class JsonUtil {
    /**
     * 序列化对象到Json文件
     * @param obj 将实例化的对象
     * @param filePath Json文件路径
     * @throws IOException File Access Exception
     */
    public static void SerializeObj(Object obj, Class type, String filePath, boolean appendFile) throws IOException {
        File file = new File(filePath);
        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) throw new IOException("Can't create folder!");
        Gson serializer = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String json = serializer.toJson(obj, type) + '\n';
        if (!file.exists() && !file.createNewFile()) throw new IOException("Can't create file!");
        if (!file.canWrite()) throw new IOException("Can't write file!");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, appendFile), "UTF-8"));
        writer.write(json);
        writer.close();
    }

    /**
     * 反序列化Json文件到对象
     * @param filePath Json文件路径
     * @return T类型对象
     * @throws IOException File Access Exception
     */
    public static Object DeserializeObj(String filePath, Class type) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) throw new IOException("File is NOT Exist!");
        if (!file.canRead()) throw new IOException("File access denied!");
        FileInputStream input = new FileInputStream(file);
        byte [] buffer = new byte[(int) file.length()];
        input.read(buffer);
        input.close();
        String json = new String(buffer, "UTF-8");
        Gson deserializer = new Gson();
        return deserializer.fromJson(json, type);
    }
}
