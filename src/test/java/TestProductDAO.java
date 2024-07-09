import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nikita.jdbctask.dao.ProductDAO;
import org.nikita.jdbctask.dto.ProductDTO;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestProductDAO {
    static Connection connection = TestDatabaseConfig.getConnection();
    static ProductDAO productDAO = new ProductDAO(connection);

    static ProductDTO testDTO1 = new ProductDTO(1L,"test product", BigDecimal.valueOf(2D), 1, true);
    static ProductDTO testDTO2 = new ProductDTO(2L,"test product2", BigDecimal.valueOf(1D), 2, false);
    static ProductDTO testDTO3 = new ProductDTO(3L,"test product3", BigDecimal.valueOf(1984D), 50, false);
    static ProductDTO testDTO4 = new ProductDTO(4L,"test product4", BigDecimal.valueOf(20D), 900, true);

    static PostgreSQLContainer postgreSQLTestContainer = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgreSQLTestContainer.start();
        recreateDAO();
    }

    @AfterAll
    static void afterAll() {
        postgreSQLTestContainer.stop();
    }

    @AfterEach
    void afterEach(){
        recreateDAO();
    }

    static void recreateDAO(){
        TestDatabaseConfig.recreateProductsTable(connection);
        productDAO.create(testDTO1);
        productDAO.create(testDTO2);
        productDAO.create(testDTO3);
        productDAO.create(testDTO4);
    }

    @Test
    void createDAOtest(){
        assertEquals(4, productDAO.getAll().size());
    }

    @Test
    public void getAllDAOtest(){
        List<ProductDTO> testData = new ArrayList<>();
        List<ProductDTO> resultData = productDAO.getAll();
        System.out.println(resultData);
        testData.add(testDTO1);
        testData.add(testDTO2);
        testData.add(testDTO3);
        testData.add(testDTO4);

        for (int i = 0; i<testData.size();i++)
            isEqual(testData.get(i), resultData.get(i));
    }

    @Test
    public void findByIdDAOtest(){
        ProductDTO testDTO = productDAO.findById(testDTO3.getId());
        isEqual(testDTO3, testDTO);
    }

    @Test
    public void getMultipleDAOtest(){
        List<ProductDTO> testData = new ArrayList<>();
        testData.add(testDTO2);
        testData.add(testDTO3);
        List<Long> idArray = new ArrayList<>();
        idArray.add(testDTO2.getId());
        idArray.add(testDTO3.getId());

        List<ProductDTO> resultData = productDAO.getMultiple(idArray);

        for (int i = 0; i<testData.size();i++)
            isEqual(testData.get(i), resultData.get(i));
    }

    @Test
    public void deleteDAOtest(){
        int currentSize = productDAO.getAll().size();
        productDAO.delete(1L);
        assertEquals(currentSize - 1, productDAO.getAll().size());
    }

    private void isEqual(ProductDTO dto1, ProductDTO dto2){
        assertEquals(dto1.getId(), dto2.getId());
        assertEquals(dto1.getName(), dto2.getName());
        assertEquals(dto1.getPrice(), dto2.getPrice());
        assertEquals(dto1.getQuantity(), dto2.getQuantity());
        assertEquals(dto1.getAvailability(),dto2.getAvailability());
    }
}
