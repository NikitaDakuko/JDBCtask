import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.OrderDetailDAO;
import org.nikita.jdbctask.dao.ProductDAO;
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

        ///testProducts1.add(TestProductDAO.testDTO1);
        testProducts1.add(TestProductDAO.testDTO2);
        //testProducts1.add(TestProductDAO.testDTO3);
        testProducts1.add(TestProductDAO.testDTO4);

        testProducts2.add(TestProductDAO.testDTO2);
        //testProducts2.add(TestProductDAO.testDTO3);
        testProducts2.add(TestProductDAO.testDTO4);

        //testProducts3.add(TestProductDAO.testDTO1);
        testProducts3.add(TestProductDAO.testDTO2);

        testDTO1 = new OrderDetailDTO(OrderStatus.NEW, testProducts1, BigDecimal.valueOf(2));
        testDTO2 = new OrderDetailDTO(OrderStatus.COMPLETE, testProducts3, BigDecimal.valueOf(65));
        testDTO3 = new OrderDetailDTO(OrderStatus.NEW, testProducts1, BigDecimal.valueOf(1985));
        testDTO4 = new OrderDetailDTO(OrderStatus.PROCESSING, testProducts2, BigDecimal.valueOf(1111));
        testDTOs.add(testDTO1);
        testDTOs.add(testDTO2);
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
    void createDAOtest() throws SQLException{
        assertEquals(4L, dao.create(testDTO3).get(0));
        assertEquals(4, dao.getAll().size());
    }

    @Test
    public void getAllDAOtest() throws SQLException{
        List<OrderDetailDTO> resultData = dao.getAll();

        System.out.println(new ProductDAO(connection).getAll());

        assertEquals(resultData.size(), testDTOs.size());

        for (int i = 0; i<testDTOs.size();i++)
            isEqual(testDTOs.get(i), resultData.get(i));
    }

    @Test
    public void findByIdDAOtest() throws SQLException {
        OrderDetailDTO testDTO = dao.findById(2L);
        isEqual(testDTO2, testDTO);
    }

    @Test
    public void deleteDAOtest() throws SQLException{
        int currentSize = dao.getAll().size();

        try {
            dao.delete(1L);
        } catch (SQLException e){
            System.out.println("SQLException: " + e);
        }

        assertEquals(currentSize - 1, dao.getAll().size());
    }

    private void isEqual(OrderDetailDTO dto1, OrderDetailDTO dto2){
        assertEquals(dto1.getOrderStatus(), dto2.getOrderStatus());
        assertEquals(dto1.getTotalAmount(), dto2.getTotalAmount());
        List<ProductDTO> products1 = dto1.getProducts();
        List<ProductDTO> products2 = dto2.getProducts();

        assertEquals(products1.size(), products2.size());
        for (int i = 0; i < products1.size(); i++) {
            assertEquals(products1.get(i).getId(), products2.get(i).getId());
        }
    }
}
