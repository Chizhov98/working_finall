package com.flexsolution.chyzhov.kmb;

import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@NoRepositoryBean
@Transactional
public abstract class AbstractService<E extends AbstractEntity, REPOSITORY extends JpaRepository> {

    protected REPOSITORY repository;

    public AbstractService(REPOSITORY repository) {
        this.repository = repository;
    }

    public E create(E e) {

        return (E) repository.save(e);

    }

    public E findById(Long id) {
        E e = (E) repository.findById(id).get();
        if (e == null) {
            throw new RestBadRequestException(
                    String.format("%s with id %d does not exist", e.getClass().getName(), id));
        } else {
            return e;
        }
    }

    public E update(E e, Long id) {
        if (repository.existsById(id)) {
            e.setId(id);
            repository.save(e);
        } else
            throw new RestBadRequestException(
                    String.format("%s with id %d is not exist", e.getClass().toString(), e.getId()));

        return e;
    }

    public List<E> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        E e = (E) repository.findById(id).get();
        if (e == null) {
            throw new RestBadRequestException(
                    String.format("%s with id %d does not exist", e.getClass().toString(), id));
        } else {
            {
                repository.delete(e);
            }
        }
    }
}
