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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestOrderDetailDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static OrderDetailDAO dao = new OrderDetailDAO(connection);

    static OrderDetailDTO testDTO1 =
            new OrderDetailDTO(1L,
                    OrderStatus.NEW,
                    Arrays.asList(
                            TestProductDAO.testDTO1,
                            TestProductDAO.testDTO2,
                            TestProductDAO.testDTO3,
                            TestProductDAO.testDTO4
                    ),
                    BigDecimal.valueOf(42));;
    static OrderDetailDTO testDTO2 =
            new OrderDetailDTO(2L,
                    OrderStatus.COMPLETE,
                    Arrays.asList(
                            TestProductDAO.testDTO2,
                            TestProductDAO.testDTO4
                    ),
                    BigDecimal.valueOf(1984));
    static OrderDetailDTO testDTO3 =
            new OrderDetailDTO(3L,
                    OrderStatus.PROCESSING,
                    Arrays.asList(
                            TestProductDAO.testDTO2,
                            TestProductDAO.testDTO3,
                            TestProductDAO.testDTO4
                    ),
                    BigDecimal.valueOf(2));;
    static OrderDetailDTO testDTO4 =
            new OrderDetailDTO(4L,
                    OrderStatus.COMPLETE,
                    Arrays.asList(
                            TestProductDAO.testDTO1,
                            TestProductDAO.testDTO4
                    ),
                    BigDecimal.valueOf(666));
    static List<OrderDetailDTO> testDTOs = Arrays.asList(testDTO1, testDTO2, testDTO3, testDTO4);

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
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
        assertEquals(resultData.toString(), testDTOs.toString());

        assertEquals(testDTOs.toString(), resultData.toString());
    }

    @Test
    public void findByIdDAOtest() {
        OrderDetailDTO testDTO = dao.findById(2L);
        assertEquals(testDTO2.toString(), testDTO.toString());
    }

    @Test
    public void updateDAOtest(){
        List<ProductDTO> updateTestProducts = Arrays.asList(TestProductDAO.testDTO2, TestProductDAO.testDTO4);

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
