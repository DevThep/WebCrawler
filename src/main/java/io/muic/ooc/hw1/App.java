package io.muic.ooc.hw1;

import java.io.File;
import java.net.URL;
import java.util.ArrayDeque;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        HTMLParser parser = new HTMLParser();
        String path = "/Users/DevSingh/Desktop/Javadocs/";
        String webpage = "https://cs.muic.mahidol.ac.th";
        File file = new File(path);
        ArrayDeque<String> stack = new ArrayDeque<String>();
        boolean firstLink = true;
        System.out.println("DOWNLOADING.....");
        stack.push("/courses/ooc/docs/");
        while (!stack.isEmpty()){
            if (firstLink){
                stack.remove();
                parser.getLinks(webpage+"/courses/ooc/docs/",
                        file.getPath() + "/courses/ooc/docs/index.html",stack);
                firstLink = false;
            }else {
                String top = stack.remove();
                parser.getLinks(webpage+top,file.getPath()+top,stack);
                if (parser.getFileCount()%500==0){
                    System.out.println(parser.getFileCount() + " FILES DOWNLOADED\r");
                }
            }
        }
        System.out.println(parser.getFileCount());
        System.out.println("Word count : " + parser.getWordCount());

    }
}
