import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dto.OrderDetailDTO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.nikita.jdbctask.enums.OrderStatus;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestOrderDetailDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static OrderDetailDAO dao = new OrderDetailDAO(connection);

    static OrderDetailDTO testDTO1;
    static OrderDetailDTO testDTO2;
    static OrderDetailDTO testDTO3;
    static OrderDetailDTO testDTO4;
    static List<OrderDetailDTO> testDTOs = new ArrayList<>();

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        List<ProductDTO> testProducts1 = new ArrayList<>();
        List<ProductDTO> testProducts2 = new ArrayList<>();
        List<ProductDTO> testProducts3 = new ArrayList<>();

        testProducts1.add(TestProductDAO.testDTO1);
        testProducts1.add(TestProductDAO.testDTO2);
        testProducts1.add(TestProductDAO.testDTO3);
        testProducts1.add(TestProductDAO.testDTO4);

        testProducts2.add(TestProductDAO.testDTO2);
        testProducts2.add(TestProductDAO.testDTO3);
        testProducts2.add(TestProductDAO.testDTO4);

        testProducts3.add(TestProductDAO.testDTO1);
        testProducts3.add(TestProductDAO.testDTO2);

        testDTO1 = new OrderDetailDTO(1L, OrderStatus.NEW, testProducts1, BigDecimal.valueOf(2));
        testDTO2 = new OrderDetailDTO(2L, OrderStatus.COMPLETE, testProducts3, BigDecimal.valueOf(65));
        testDTO3 = new OrderDetailDTO(3L, OrderStatus.NEW, testProducts3, BigDecimal.valueOf(1985));
        testDTO4 = new OrderDetailDTO(4L, OrderStatus.PROCESSING, testProducts2, BigDecimal.valueOf(1111));
        testDTOs.add(testDTO1);
        testDTOs.add(testDTO2);
        testDTOs.add(testDTO3);
        testDTOs.add(testDTO4);

        postgreSQLTestContainer.start();
        recreateTables();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLTestContainer.stop();
    }

    @AfterEach
    void afterEach(){
        recreateTables();
    }

    static void recreateTables(){
        try {
            TestDatabaseConfig.recreateOrderDetailTable(connection);
            TestProductDAO.recreateDAO();
            TestDatabaseConfig.recreateOrderProductTable(connection);
            dao.create(testDTOs);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
            throw new RuntimeException(e);
        }
    }

    @Test
    void createDAOtest() {
        assertEquals(4, dao.getAll().size());
        assertEquals(5L, dao.create(testDTO3).get(0));
    }

    @Test
    public void getAllDAOtest() {
        List<OrderDetailDTO> resultData = dao.getAll();
        assertEquals(resultData.size(), testDTOs.size());

        assertEquals(testDTOs.toString(), resultData.toString());
    }

    @Test
    public void findByIdDAOtest() {
        OrderDetailDTO testDTO = dao.findById(2L);
        assertEquals(testDTO2.toString(), testDTO.toString());
    }

    @Test
    public void updateDAOtest(){
        List<ProductDTO> updateTestProducts = new ArrayList<>();
        updateTestProducts.add(TestProductDAO.testDTO2);
        updateTestProducts.add(TestProductDAO.testDTO4);

        OrderDetailDTO testDetailDTO = new OrderDetailDTO(
                3L, OrderStatus.PROCESSING,updateTestProducts, BigDecimal.valueOf(666));

        assertNotEquals(testDetailDTO.toString(), dao.findById(3L).toString());
        dao.update(testDetailDTO);
        assertEquals(testDetailDTO.toString(), dao.findById(3L).toString());
    }

    @Test
    public void deleteDAOtest() {
        int currentSize = dao.getAll().size();
        dao.delete(1L);
        assertEquals(currentSize - 1, dao.getAll().size());
    }
}
