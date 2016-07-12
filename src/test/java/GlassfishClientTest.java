import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import ru.atconsulting.bigdata.client.RestClient;
import ru.atconsulting.bigdata.configuration.RestClientConfiguration;
import ru.atconsulting.bigdata.configuration.SpringConfiguration;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by NSkovpin on 15.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
@WebAppConfiguration
public class GlassfishClientTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("telegramClient")
    private RestClient restClient;

    private MockRestServiceServer mockServer;

    @Before
    public void setUp(){
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void testGetMessage() {
        mockServer.expect(requestTo("http://jsonplaceholder.typicode.com/posts/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("THIS IS SUCCESS", MediaType.TEXT_PLAIN));

        String resultFromTelegram = restClient.getInformationWithUrl(String.class,
                RestClientConfiguration.TelegramClientPropertyNames.ADDITIONAL_INFO_URL.getValue());

        mockServer.verify();


    }

}
