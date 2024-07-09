import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.enums.OrderStatus;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrderDetailDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static OrderDetailDAO dao = new OrderDetailDAO(connection);

    static OrderDetailDTO testDTO1 = new OrderDetailDTO(1L, OrderStatus.NEW, new ArrayList<>(), BigDecimal.valueOf(2D));
    static OrderDetailDTO testDTO2 = new OrderDetailDTO(2L, OrderStatus.NEW, new ArrayList<>(), BigDecimal.valueOf(65));
    static OrderDetailDTO testDTO3 = new OrderDetailDTO(3L,OrderStatus.NEW, new ArrayList<>(), BigDecimal.valueOf(1985));
    static OrderDetailDTO testDTO4 = new OrderDetailDTO(4L,OrderStatus.NEW, new ArrayList<>(), BigDecimal.valueOf(1111));

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgreSQLTestContainer.start();
        recreateTable();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLTestContainer.stop();
    }

    @AfterEach
    void afterEach(){
        recreateTable();
    }

    static void recreateTable(){
        TestDatabaseConfig.recreateOrderDetailTable(connection);
        dao.create(testDTO1);
        dao.create(testDTO2);
        dao.create(testDTO4);
    }

    @Test
    void createDAOtest(){
        assertEquals(dao.returnIds(dao.create(testDTO3)).get(0), 3L);
        assertEquals(4, dao.getAll().size());
    }

    @Test
    public void getAllDAOtest(){
        List<OrderDetailDTO> testData = new ArrayList<>();
        List<OrderDetailDTO> resultData = dao.getAll();
        testData.add(testDTO1);
        testData.add(testDTO2);
        testData.add(testDTO4);

        for (int i = 0; i<testData.size();i++)
            isEqual(testData.get(i), resultData.get(i));
    }

    @Test
    public void findByIdDAOtest(){
        OrderDetailDTO testDTO = dao.findById(testDTO2.getId());
        isEqual(testDTO2, testDTO);
    }

    @Test
    public void deleteDAOtest(){
        int currentSize = dao.getAll().size();
        dao.delete(1L);
        assertEquals(currentSize - 1, dao.getAll().size());
    }

    private void isEqual(OrderDetailDTO dto1, OrderDetailDTO dto2){
        assertEquals(dto1.getId(), dto2.getId());
        assertEquals(dto1.getOrderStatus(), dto2.getOrderStatus());
        assertEquals(dto1.getTotalAmount(), dto2.getTotalAmount());
        assertEquals(dto1.getProducts(), dto2.getProducts());
    }
}
