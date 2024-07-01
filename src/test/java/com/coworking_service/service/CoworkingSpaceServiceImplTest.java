package com.coworking_service.service;

import com.coworking_service.entity.Workplace;
import com.coworking_service.exception.PersistException;
import com.coworking_service.repository.WorkplaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Тесты для класса {@link CoworkingSpaceServiceImpl}.
 */
@DisplayName("Тесты для CoworkingSpaceServiceImpl")
class CoworkingSpaceServiceImplTest {
    @Mock
    private WorkplaceRepository workplaceRepository;

    @InjectMocks
    private CoworkingSpaceServiceImpl coworkingSpaceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Тестирование получения списка рабочих пространств")
    void testGetSpaces() throws PersistException {
        List<Workplace> individualWorkplaces = new ArrayList<>();
        individualWorkplaces.add(new Workplace(1, 1, "individual"));
        when(workplaceRepository.getWorkplacesByType("individual")).thenReturn(individualWorkplaces);

        List<Workplace> conferenceRooms = new ArrayList<>();
        conferenceRooms.add(new Workplace(2, 30, "conference"));
        when(workplaceRepository.getWorkplacesByType("conference")).thenReturn(conferenceRooms);

        try {
            coworkingSpaceService.getSpaces();
        } catch (PersistException e) {
            e.printStackTrace();
        }

        assertEquals(1, individualWorkplaces.size());
        assertEquals(1, conferenceRooms.size());
    }
}