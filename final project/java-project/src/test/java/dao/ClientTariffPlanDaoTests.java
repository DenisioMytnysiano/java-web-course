package dao;

import com.denisio.app.model.dao.ClientTariffPlanDao;
import com.denisio.app.model.entity.ClientTariffPlan;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ClientTariffPlanDaoTests {

    private ClientTariffPlanDao clientTariffPlanDao;

    @Before
    public void init() {
        clientTariffPlanDao = new ClientTariffPlanDao();
    }

    @Test
    public void getTariffPlansByEmail() {
        String email = "petroh@gmail.com";
        List<ClientTariffPlan> clientTariffPlans = clientTariffPlanDao.getClientTariffPlansByEmail(email);
        for (ClientTariffPlan clientTariffPlan: clientTariffPlans) {
            assertEquals(clientTariffPlan.getClient().getEmail(), email);
        }
    }
}
