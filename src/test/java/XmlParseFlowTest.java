import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.atconsulting.bigdata.configuration.SpringConfiguration;
import ru.atconsulting.bigdata.workflow.actions.GlassfishAction;
import ru.atconsulting.bigdata.workflow.xml.business.Flow;
import ru.atconsulting.bigdata.workflow.xml.technical.Instructions;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Created by NSkovpin on 15.06.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
@WebAppConfiguration
public class XmlParseFlowTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void parseTechnicalFlow() throws JAXBException {

        GlassfishAction glassfishAction = applicationContext.getBean(GlassfishAction.class,null,null,null);

        JAXBContext jaxbContext = JAXBContext.newInstance(Flow.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        Instructions instructions = (Instructions) jaxbUnmarshaller.unmarshal(new File(getClass().getResource("flow\\flow-instructions.xml").getPath()));
        assert instructions != null;
        assert instructions.getInstruction() != null;
        assert instructions.getInstruction().size() > 0;
    }

}
