
package com.estore.ecomerce.service;

import com.estore.ecomerce.domain.Base;
import com.estore.ecomerce.repository.BaseRepository;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseServiceImpl <E extends Base, ID extends Serializable> implements BaseService<E, ID> {
    protected BaseRepository<E,ID> baseRepository;
    public BaseServiceImpl(BaseRepository<E,ID> baseRepository){
        this.baseRepository=baseRepository;
    }
    @Override
    @Transactional
    public List<E> findAll() throws Exception {
        try {
            List<E> entities=baseRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
  
    @Override
    @Transactional
    public E findById(ID id) throws Exception {
        try {
            Optional<E> entityId=baseRepository.findById(id);
            return entityId.get();
            
        } catch (Exception e) {
             throw new Exception(e.getMessage());

        }
           }

    @Override
    @Transactional
    public E save(E entity) throws Exception {
        try {
            entity=baseRepository.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception {
        try {
            Optional<E> entityById=baseRepository.findById(id); //Buscar y si existe
            E entityUpdate= entityById.get();//creo categoria
            entityUpdate=baseRepository.save(entity); //la salvo
            return entityUpdate;
        } catch (Exception e) {
             throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
         try {
             if (baseRepository.existsById(id)){
                  Optional<E> entityById=baseRepository.findById(id);
                  E entity= entityById.get();
                  entity.setStatus(Boolean.FALSE);
                  entity=baseRepository.save(entity); 
                   return true;
             }else{
             throw new Exception();
             }           
        } catch (Exception e) {
             throw new Exception(e.getMessage());
        }
    }
}

