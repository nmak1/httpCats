import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

public class CloseableHttpClient {
    public static void main(String[] args) throws IOException {
        org.apache.http.impl.client.CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        CloseableHttpResponse response = httpClient.execute(request);
        ObjectMapper mapper = new ObjectMapper();
        List<CatsFacts> catsFacts = mapper.readValue(response.getEntity().getContent(),
                new TypeReference<List<CatsFacts>>() {
                });
        //Посмотрим сколько в списке получилось объектов
        out.println("Всего зачитано объектов из json: " + catsFacts.size());

        //выведем как указано в задании только те за которые проголосовали хотя бы один раз
        out.println("Факты за которые проголосовали хотя бы один раз: ");
        catsFacts.stream()
                .filter(value -> value.getUpvotes() != null && value.getUpvotes() > 0)
                .forEach(out::println);
    }
}












