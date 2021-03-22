package Test;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class TestMain {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/java/game/levels/Levels.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String stringIterator;
            int objCount = Integer.parseInt(jsonObject.get("objectCount").toString());

            for (int i = 1; i < objCount + 1; i++) {
                stringIterator = String.valueOf(i);
                JSONObject jsonObject1 = (JSONObject) jsonObject.get(stringIterator);
                int objCount1 = Integer.parseInt(jsonObject1.get("objectCount").toString());
                System.out.println(jsonObject1);
                System.out.println(objCount1);
            }
            System.out.println(obj);
            System.out.println(jsonObject);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
