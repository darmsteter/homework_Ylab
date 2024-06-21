package com.coworking_service.model;

/**
 * Класс, представляющий индивидуальное рабочее место в коворкинг-пространстве.
 */
public class IndividualWorkplace extends Workplace {
    /**
     * Конструктор для создания рабочего места с номером рабочего места.
     *
     * @param workplaceID номер рабочего места
     */
    public IndividualWorkplace(int workplaceID) {
        super(workplaceID);
    }
}
