package io.muic.ooc.hw1;


import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadWebPage {
    protected void download(String spec,String path)throws Exception{
        File file = new File(path);
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            return;
        }
        File parentf = new File(file.getParent());
        if (!parentf.exists()) {
            parentf.mkdirs();
        }
        URL url = new URL(spec);
        try{
            FileUtils.copyURLToFile(url,file);
        }catch (IOException e){
            return;
        }
    }
}