import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.OrderApprovalDAO;
import org.nikita.jdbctask.dto.OrderApprovalDTO;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrderApprovalDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static OrderApprovalDAO dao = new OrderApprovalDAO(connection);

    static OrderApprovalDTO testDTO1 = new OrderApprovalDTO(1L, TestOrderDetailDAO.testDTO2);
    static OrderApprovalDTO testDTO2 = new OrderApprovalDTO(2L, TestOrderDetailDAO.testDTO3);
    static OrderApprovalDTO testDTO3 = new OrderApprovalDTO(3L, TestOrderDetailDAO.testDTO1);
    static OrderApprovalDTO testDTO4 = new OrderApprovalDTO(4L, TestOrderDetailDAO.testDTO4);
    static List<OrderApprovalDTO> testDTOs = Arrays.asList(testDTO1, testDTO2, testDTO3, testDTO4);

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
            TestDatabaseConfig.recreateOrderApprovalTable(connection);
            TestProductDAO.recreateDAO();
            TestDatabaseConfig.recreateOrderProductTable(connection);
            dao.create(testDTOs);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllDAOtest() {
        List<OrderApprovalDTO> resultData = dao.getAll();
        assertEquals(resultData.size(), testDTOs.size());
        //assertEquals(testDTOs.toString(), resultData.toString());
    }

    @Test
    public void findByIdDAOtest() {
//        OrderDetailDTO testDTO = dao.findById(2L);
//        assertEquals(testDTO2.toString(), testDTO.toString());
    }

    @Test
    public void updateDAOtest(){
//        List<ProductDTO> updateTestProducts = new ArrayList<>();
//        updateTestProducts.add(TestProductDAO.testDTO2);
//        updateTestProducts.add(TestProductDAO.testDTO4);
//
//        OrderDetailDTO testDetailDTO = new OrderDetailDTO(
//                3L, OrderStatus.PROCESSING,updateTestProducts, BigDecimal.valueOf(666));
//
//        assertNotEquals(testDetailDTO.toString(), dao.findById(3L).toString());
//        dao.update(testDetailDTO);
//        assertEquals(testDetailDTO.toString(), dao.findById(3L).toString());
    }

    @Test
    public void deleteDAOtest() {
//        int currentSize = dao.getAll().size();
//        dao.delete(1L);
//        assertEquals(currentSize - 1, dao.getAll().size());
    }
}
