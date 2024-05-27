package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.NasaAnswer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
    public static String getUrlNasa(String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        ObjectMapper mapper = new ObjectMapper();

        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            NasaAnswer answer = mapper.readValue(response.getEntity().getContent(), NasaAnswer.class);
            return answer.getUrl();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Что-то пошло не так тут");
        }
        return null;
    }

    public static String getDataProperties(String param) {
        FileInputStream FileInputStream;
        Properties property = new Properties();

        try {
            FileInputStream = new FileInputStream("src/main/resources/application.properties");
            property.load(FileInputStream);
            return property.getProperty(param);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }
        return null;
    }
}
