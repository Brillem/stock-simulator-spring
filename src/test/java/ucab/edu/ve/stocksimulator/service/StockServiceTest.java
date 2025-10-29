package ucab.edu.ve.stocksimulator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.dto.response.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.model.Stock;
import ucab.edu.ve.stocksimulator.repository.StockRepo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("StockService Unit Tests")
class StockServiceTest {

    @Mock
    private StockRepo stockRepo;

    @InjectMocks
    private StockService stockService;

    private Stock testStock1;
    private Stock testStock2;

    @BeforeEach
    void setUp() {
        // Setup test stock 1
        testStock1 = new Stock();
        testStock1.setId(1L);
        testStock1.setName("Apple Inc.");
        testStock1.setTicker("AAPL");
        testStock1.setDescription("Technology company");

        // Setup test stock 2
        testStock2 = new Stock();
        testStock2.setId(2L);
        testStock2.setName("Microsoft Corporation");
        testStock2.setTicker("MSFT");
        testStock2.setDescription("Software company");
    }

    @Test
    @DisplayName("Should find all stocks and return response DTO")
    void testFindAll() {
        // Arrange
        List<Stock> stockList = Arrays.asList(testStock1, testStock2);
        when(stockRepo.findAll()).thenReturn(stockList);

        // Act
        StockListResponseDTO response = stockService.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(0, response.code);
        assertEquals(2, response.stockDTOList.size());
        assertEquals("AAPL", response.stockDTOList.get(0).ticker);
        assertEquals("MSFT", response.stockDTOList.get(1).ticker);
        verify(stockRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list with code 1 when no stocks exist")
    void testFindAllEmpty() {
        // Arrange
        when(stockRepo.findAll()).thenReturn(Collections.emptyList());

        // Act
        StockListResponseDTO response = stockService.findAll();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.code);
        assertTrue(response.stockDTOList.isEmpty());
        verify(stockRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should find stock by ID")
    void testFindStockById() {
        // Arrange
        when(stockRepo.findById(1L)).thenReturn(Optional.of(testStock1));

        // Act
        Optional<Stock> foundStock = stockService.findStockById(1L);

        // Assert
        assertTrue(foundStock.isPresent());
        assertEquals("AAPL", foundStock.get().getTicker());
        assertEquals("Apple Inc.", foundStock.get().getName());
        verify(stockRepo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty optional when stock not found by ID")
    void testFindStockByIdNotFound() {
        // Arrange
        when(stockRepo.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Stock> foundStock = stockService.findStockById(999L);

        // Assert
        assertFalse(foundStock.isPresent());
        verify(stockRepo, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should save stock")
    void testSaveStock() {
        // Arrange
        when(stockRepo.save(any(Stock.class))).thenReturn(testStock1);

        // Act
        stockService.save(testStock1);

        // Assert
        verify(stockRepo, times(1)).save(testStock1);
    }

    @Test
    @DisplayName("Should map Stock to StockDTO")
    void testMapStocktoDTO() {
        // Act
        StockDTO stockDTO = stockService.mapStocktoDTO(testStock1);

        // Assert
        assertNotNull(stockDTO);
        assertEquals("Apple Inc.", stockDTO.name);
        assertEquals("AAPL", stockDTO.ticker);
        assertEquals("Technology company", stockDTO.description);
    }

    @Test
    @DisplayName("Should map list of Stocks to list of StockDTOs")
    void testMapStockListToStockDTOList() {
        // Arrange
        List<Stock> stockList = Arrays.asList(testStock1, testStock2);

        // Act
        List<StockDTO> stockDTOList = stockService.mapStockListToStockDTOList(stockList);

        // Assert
        assertNotNull(stockDTOList);
        assertEquals(2, stockDTOList.size());
        assertEquals("AAPL", stockDTOList.get(0).ticker);
        assertEquals("MSFT", stockDTOList.get(1).ticker);
    }

    @Test
    @DisplayName("Should return empty list when mapping empty stock list")
    void testMapStockListToStockDTOListEmpty() {
        // Arrange
        List<Stock> emptyList = Collections.emptyList();

        // Act
        List<StockDTO> stockDTOList = stockService.mapStockListToStockDTOList(emptyList);

        // Assert
        assertNotNull(stockDTOList);
        assertTrue(stockDTOList.isEmpty());
    }

    @Test
    @DisplayName("Should map StockDTOList to response with code 0 when list is not empty")
    void testMapStockDTOtoResponseWithData() {
        // Arrange
        StockDTO stockDTO1 = new StockDTO();
        stockDTO1.ticker = "AAPL";
        stockDTO1.name = "Apple Inc.";

        StockDTO stockDTO2 = new StockDTO();
        stockDTO2.ticker = "MSFT";
        stockDTO2.name = "Microsoft Corporation";

        List<StockDTO> stockDTOList = Arrays.asList(stockDTO1, stockDTO2);

        // Act
        StockListResponseDTO response = stockService.mapStockDTOtoResponse(stockDTOList);

        // Assert
        assertNotNull(response);
        assertEquals(0, response.code);
        assertEquals(2, response.stockDTOList.size());
    }

    @Test
    @DisplayName("Should map empty StockDTOList to response with code 1")
    void testMapStockDTOtoResponseEmpty() {
        // Arrange
        List<StockDTO> emptyList = Collections.emptyList();

        // Act
        StockListResponseDTO response = stockService.mapStockDTOtoResponse(emptyList);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.code);
        assertTrue(response.stockDTOList.isEmpty());
    }
}
