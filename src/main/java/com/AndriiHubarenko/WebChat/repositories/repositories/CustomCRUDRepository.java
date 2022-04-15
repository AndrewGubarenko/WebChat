package com.AndriiHubarenko.WebChat.repositories.repositories;

/**
 * @author Andrii Hubarenko
 * Custom interface for CRUD repository
 * @param <T> desired object in the database
 * @param <P> parameter for searching object in DB
 */
public interface CustomCRUDRepository<T, P> {
    /**
     * Method for creation Entity in DB
     * @param t object that is necessary to save
     */
    boolean create(T t);

    /**
     * Method for retrieving the entity from DB
     * @param p unique identifier of entity
     * @return found object
     */
    T get(P p);

    /**
     * Method for updating entity
     * @param t object with updated fields
     * @return updated object
     */
    T update(T t);

    /**
     * Method for removing entity from DB
     * @param p unique identifier of entity
     * @return message about status of operation
     */
    String delete(P p);
}
