import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Rollback(false)
@Transactional(propagation = Propagation.NEVER)
@ContextConfiguration(classes = { TestConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TransactionTest {

    @Inject
    private MyService service;

    @Inject
    private TransTimeBean transTimeBean;

    @Before
    public void verifyTransactionNotActive() {
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
   //    transTimeBean.getTime();
    }

    @Test
    public void testPropagationDefault() throws Exception{
        transTimeBean.getTime();

        LocalDateTime date1 = service.getTransactionTime();
        Thread.sleep(10);
        LocalDateTime date2 = service.getTransactionTime();
        assertTrue(date2.isAfter(date1));
    }

    @Test
    public void testPropagationNew() throws Exception{
        LocalDateTime date1 = service.getTransactionTimeNew();
        Thread.sleep(10);
        LocalDateTime date2 = service.getTransactionTimeNew();
        assertTrue(date2.isAfter(date1));
    }
}
