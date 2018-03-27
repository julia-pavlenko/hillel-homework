import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by julia on 27.03.2018.
 */
public class Parser {

    private final String REPORT_PATH = "/Users/julia/src/test/fb/output.csv";

    private final String fileFolderPath;
    private ArrayList<String> givenFBMembers;
    private ArrayList<FBMember> fBMembers;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        String fileFolderPath = args[0];
        Parser parser = new Parser(fileFolderPath);
        parser.parseFilesFolder();
        parser.parseStringsToFBMember();
        parser.createReport();

        long endTime = System.currentTimeMillis();
        long speed = endTime - startTime;
        System.out.println("Finish with speed: "+speed/1000);

    }

    public Parser(String fileFolderPath) {
        this.fileFolderPath = fileFolderPath;
    }

    private void parseFilesFolder() {
        File fileFolder = new File(fileFolderPath);
        if (fileFolder.exists() && fileFolder.isDirectory()) {
            for (final File file : fileFolder.listFiles()) {
                if (file.isFile() && file.getName().startsWith("test")) {
                    if (fBMembers == null) {
                        fBMembers = new ArrayList<FBMember>(1001);
                    }
                    parseFile(file);
                }
            }
        } else {
            throw new Error(String.format("Directory \"%s\" not found", fileFolder));
        }
    }

    private void parseFile(File file){
        try {
            long startOfreading = System.currentTimeMillis();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line;
            givenFBMembers = new ArrayList<String>(1001);
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                givenFBMembers.add(trimmedLine);
            }
            long endOfreading = System.currentTimeMillis();
            long speed = (endOfreading-startOfreading)/1000;
            System.out.println("File was read with speed:"+speed+" sec");
            br.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private void parseStringsToFBMember() throws IOException {
        long startOfreading = System.currentTimeMillis();
        for ( String givenMember: givenFBMembers) {
            parseStringToFBMember(givenMember);
        }
        long endOfreading = System.currentTimeMillis();
        long speed = (endOfreading-startOfreading)/1000;
        System.out.println("Data was created with speed:"+speed+" sec");

    }

    private void parseStringToFBMember(String member) throws IOException {
        String fullName = member.split(",")[0];
        String name = fullName.split(" ")[0];
        String surName = fullName.replace(name+" ","");
        String url = member.split(",")[1];
        String id = getIdFromURL(url);

        FBMember fbMember = new FBMember(name,surName,id);
        fBMembers.add(fbMember);
    }

    private String getIdFromURL(String url) throws IOException {
        String id = null;
        if(url.contains("id=")){
            Pattern pattern = Pattern.compile("id=(.*)&");
            Matcher matcher = pattern.matcher(url);
            while (matcher.find()) {
                id = matcher.group(1);
            }
        } else {
            id = getId(url);
        }
        return id;
    }

    private String getId(String givenURL) throws IOException {

        String username = givenURL.replace("https://www.facebook.com/","");
        String URL = "https://zerohacks.com/ads/findmyid/index.php?username="+username+".html";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        String content = EntityUtils.toString(response.getEntity());
        httpClient.close();
        String id;
        if (content.contains("Your Facebook Numerical ID is ")) {
            id = content.replace("Your Facebook Numerical ID is ","");
        } else id="Id not found";
        return id;
    }

    private void createReport() throws IOException {
        FileWriter writer = new FileWriter(REPORT_PATH);
        writer.write("First Name,Last Name,ID\n");
        for ( FBMember fbmember: fBMembers) {
            writer.write(fbmember.name+","+fbmember.surname+","+fbmember.id+"\n");
        }
        writer.close();

    }

    private class FBMember{
        public String name;
        public String surname;
        public String id;

        public FBMember(String name, String surname, String id){
            this.name = name;
            this.surname = surname;
            this.id = id;
        }
    }
}
