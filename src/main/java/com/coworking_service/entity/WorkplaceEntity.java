package com.coworking_service.entity;

/**
 * Представляет запись о рабочем месте.
 *
 * @param workplaceId     уникальный идентификатор рабочего места.
 * @param maximumCapacity максимальная вместимость рабочего места.
 * @param workplaceType   тип рабочего места.
 */
public record WorkplaceEntity(Integer workplaceId,
                              Integer maximumCapacity,
                              String workplaceType)
        implements Entity<Integer> {

    /**
     * Возвращает уникальный идентификатор рабочего места.
     *
     * @return уникальный идентификатор рабочего места.
     */
    @Override
    public Integer getPK() {
        return workplaceId;
    }
}
