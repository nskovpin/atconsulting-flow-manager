import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import ru.atconsulting.bigdata.configuration.SpringConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by NSkovpin on 10.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
@WebAppConfiguration
public class SearchWorkflowRestControllerTest {

    private static final Logger logger = Logger.getLogger(SearchWorkflowRestControllerTest.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void shutdownTest() throws Exception {
        logger.info("Starting testing controllers");
        System.out.println("TEST");
        int telegramId = 1;
        mockMvc.perform(get("/rest/workflow/shutdown?telegramId="+telegramId).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andExpect(content().string("{\"telegramId\":\"1\",\"status\":\"NOT FOUND\",\"info\":\"Didn't find thread with telegramId:1\"}"));
    }

    @Test
    public void workflowSearchTest() throws Exception {

        mockMvc.perform(get("/rest/workflow/search?telegramId=123&flowId=33&subscriberNo=12123").accept(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().string("{\"userInfo\":{\"telegramId\":\"123\",\"subscriberNo\":\"12123\",\"name\":null,\"surname\":null,\"links\":[]},\"workflow\":{\"flowId\":\"33\",\"flowName\":null,\"flowAdditionalInfo\":null,\"links\":[]},\"stepAnswerList\":null,\"errorAnswer\":null,\"status\":\"Processing\",\"links\":[{\"rel\":\"self\",\"href\":\"http://localhost/rest/workflow/search?telegramId=123&subscriberNo=12123&flowId=33\"}]}"));

        System.out.println("ad");

    }

}

