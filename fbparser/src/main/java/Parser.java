import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by julia on 27.03.2018.
 */
public class Parser {

    private static final String OUTPUT_FILE_PATH = "/Users/julia/src/test/fb/output.csv";
    private static final String INPUT_FOLDER_PATH = "/Users/julia/src/test/fb";

    private String fileFolderPath;
    private ArrayList<String> givenFBMembers;
    private ArrayList<FBMember> fBMembers;
    private Map<String, FBMember> fbMemberMap;
    private OkHttpClient client;
    private int arraySize;

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        Parser parser = new Parser(INPUT_FOLDER_PATH);
        parser.parseFilesFolder();
        parser.parseStringsToFBMember();
        parser.createReport();

        long endTime = System.currentTimeMillis();
        long speed = endTime - startTime;
        System.out.println("Finish with speed: "+speed / 1000);

    }

    public Parser(String fileFolderPath) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.SECONDS);
        client = builder.build();
        this.fileFolderPath = fileFolderPath;
    }

    private void parseFilesFolder() {
        File fileFolder = new File(fileFolderPath);
        if (fileFolder.exists() && fileFolder.isDirectory()) {
            for (final File file : fileFolder.listFiles()) {
                if (file.isFile() && file.getName().startsWith("test")) {
                    if (fBMembers == null) {
                        fBMembers = new ArrayList<FBMember>(arraySize);
                        fbMemberMap = new HashMap<String, FBMember>(arraySize);
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
            givenFBMembers = new ArrayList<String>(50000);
            while ((line = br.readLine()) != null) {
                String trimmedLine = line.trim();
                givenFBMembers.add(trimmedLine);
            }
            long endOfreading = System.currentTimeMillis();
            long speed = (endOfreading-startOfreading)/1000;
            System.out.println("File was read with speed:"+speed+" sec");
            br.close();
            arraySize = givenFBMembers.size();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private String httpGet(String url) {
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
        }
        return "Timeout";
    }

    private void parseStringsToFBMember() throws IOException {
        long startOfreading = System.currentTimeMillis();
        for (int i = 0; i < arraySize; i++) {
            if (i%(arraySize / 100) == 0){
                System.out.printf("\ncurrentProgress: %d%s", Math.round((i / (arraySize - 1f)) * 100), "%");
            }
            parseStringToFBMember(givenFBMembers.get(i));
        }
        long endOfreading = System.currentTimeMillis();
        long speed = (endOfreading-startOfreading)/1000;
        System.out.println("\nData was created with speed:" + speed+" sec");
    }

    private void parseStringToFBMember(String member) throws IOException {
        String fullName = member.split(",")[0];
        String name = fullName.split(" ")[0];
        String surName = fullName.replace(name+" ","");
        String url = member.split(",")[1];
        String id = getIdFromURL(url);

        if (fbMemberMap.containsKey(url)) {
            fBMembers.add(fbMemberMap.get(url));
        } else {
            FBMember fbMember = new FBMember(name,surName,id);
            fBMembers.add(fbMember);
            fbMemberMap.put(url, fbMember);
        }
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
        String content = httpGet(URL);
        String id;
        if (content.contains("Your Facebook Numerical ID is ")) {
            id = content.replace("Your Facebook Numerical ID is ","");
        } else if (content.contains("Timeout")) {
            id = "Timeout";
        } else {
            id = "Id not found";
        }
        return id;
    }

    private void createReport() throws IOException {
        FileWriter writer = new FileWriter(OUTPUT_FILE_PATH);
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
