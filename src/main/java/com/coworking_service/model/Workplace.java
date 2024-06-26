package com.coworking_service.model;

/**
 * Абстрактный класс, представляющий рабочее место в коворкинг-пространстве.
 */
public abstract class Workplace {
    final int workplaceID;

    /**
     * Конструктор для создания рабочего места с номером рабочего места.
     *
     * @param workplaceID номер рабочего места
     */
    public Workplace(int workplaceID) {
        this.workplaceID = workplaceID;
    }

    /**
     * Получает номер рабочего места.
     *
     * @return номер рабочего места
     */
    public int getWorkplaceID() {
        return workplaceID;
    }
}
