package com.coworking_service.model;

/**
 * Класс, представляющий конференц-зал в коворкинг-пространстве.
 */
public class ConferenceRoom extends Workplace {
    private final int maximumCapacity;

    /**
     * Конструктор для создания рабочего места с номером рабочего места.
     *
     * @param workplaceID номер рабочего места
     */
    public ConferenceRoom(int workplaceID, int maximumCapacity) {
        super(workplaceID);
        this.maximumCapacity = maximumCapacity;
    }

    /**
     * Получает вместимость конференц-зала.
     *
     * @return максимальную вместимость конференц-зала.
     */
    public int getMaximumCapacity() {
        return maximumCapacity;
    }
}
