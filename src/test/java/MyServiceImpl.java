import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class MyServiceImpl implements MyService{

    @Inject
    private TransTimeBean transTimeBean;

    @Override
    @Transactional
    public LocalDateTime getTransactionTime() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
	return transTimeBean.getTime();
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public LocalDateTime getTransactionTimeNew() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
	return transTimeBean.getTime();
    }
}
