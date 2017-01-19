package io.muic.ooc.hw1;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class HTMLParser {
    private Set<String> webpageLooked = new HashSet<String>();
    private Set<String> htmlLooked = new HashSet<String>();
    private DownloadWebPage dl = new DownloadWebPage();

    public int getFileCount() {
        return fileCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    private int fileCount = 0;
    private int wordCount = 0;
    protected void getLinks(String webpage,String path,ArrayDeque<String> stack) throws Exception {
        ArrayList<String> toDownload = new ArrayList<String>();
        dl.download(webpage,path);
        fileCount +=1;
        Document doc = null;
        Elements links = null;
        try {
            doc = Jsoup.connect(webpage).get();
        }catch (HttpStatusException e){
            return;
        }
        String[] htmlWords = doc.text().split("\\w+");
        wordCount += htmlWords.length;
        try {
            links = doc.select("a[href]");
            links.addAll(doc.select("[src]"));
            links.addAll(doc.select("link[href]"));
        }catch (NullPointerException e){
            return;
        }

        for (Element link : links) {
            String attribute = link.attr("abs:href");
            if (attribute=="") attribute = link.attr("abs:src");
            if (attribute.contains("https://cs.muic.mahidol.ac.th")){
                URL aUrl = new URL(attribute);
                String urlPath = aUrl.getPath();
                if (isHTML(urlPath)){
                    if(!htmlLooked.contains(urlPath)){
                        htmlLooked.add(urlPath);
                        stack.push(urlPath);
                    }
                }else if (!webpageLooked.contains(urlPath)){
                    webpageLooked.add(urlPath);
                    toDownload.add(urlPath);
                }
            }
        }
//        Download from toDownloads list
        for(String down: toDownload){
            dl.download("https://cs.muic.mahidol.ac.th" + down,"/Users/DevSingh/Desktop/Javadocs"+ down);
            fileCount+=1;
        }
 }
    private boolean isHTML(String link){
        String ext = FilenameUtils.getExtension(link);
        if (ext.equals("html")) return true;
        else return false;
    }
    //Check if its the path to be downloaded
    private boolean correctPath(String link){
        try {
            URL url = new URL(link);
            return false;
        }
        catch (MalformedURLException e) {
            return true;
        }
    }
}
