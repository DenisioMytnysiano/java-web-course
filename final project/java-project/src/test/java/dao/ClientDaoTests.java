package dao;

import com.denisio.app.model.dao.ClientDao;
import com.denisio.app.model.entity.Client;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ClientDaoTests {

    private ClientDao clientDao;

    @Before
    public void init(){
        clientDao = new ClientDao();
    }

    @Test
    public void testAddClient(){
        Client client = new Client();
        client.setClientName("Stepan");
        client.setSurname("Igorov");
        client.setEmail("stepan@gmail.com");
        client.setPhoneNumber("+38(099)4532646");
        client.setPassword("12345");
        clientDao.addClient(client);

        Optional<Client> extracted = clientDao.getClientByEmail(client.getEmail());
        assertTrue(extracted.isPresent());
        assertEquals(extracted.get().getEmail(), client.getEmail());
    }

    @Test
    public void testUpdateClient(){
        Optional<Client> extracted = clientDao.getClientByEmail("stepan@gmail.com");
        assertTrue(extracted.isPresent());
        Client client = extracted.get();
        client.setSurname("Petrov");
        clientDao.updateClient(client);
        extracted = clientDao.getClientByEmail("stepan@gmail.com");
        assertTrue(extracted.isPresent());
        assertEquals(extracted.get().getSurname(), client.getSurname());
    }
}
