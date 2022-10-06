package com.epam.esm.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Mapper to transform model and dto types.
 */
public abstract class Mapper<T, I> {

    /**
     * Transform dto object to model type.
     *
     * @param dto object to transform into model
     * @return model transformed dto
     */
    abstract T toModel(I dto);

    /**
     * Transform model object to dto type.
     *
     * @param model object to transform into dto
     * @return dto transformed model
     */
    abstract I toDTO(T model);

    /**
     * Transform {@link LocalDateTime} object to {@link String} type in ISO 8601 format.
     *
     * @param dateTime objects to transform into {@link String}
     * @return dateTimeString   transformed dateTime
     */
    public static String constructISOFormatDate(LocalDateTime dateTime) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return dateTime.format(formatter);
    }
}
