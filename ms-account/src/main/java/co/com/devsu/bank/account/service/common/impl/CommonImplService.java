package co.com.devsu.bank.account.service.common.impl;

import co.com.devsu.bank.account.service.common.ICommonService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommonImplService<R extends JpaRepository<E, I>, E, I> implements ICommonService<R, E, I> {

    private final R repository;

    public CommonImplService(R repository) {
        this.repository = repository;
    }


    @Override
    public Optional<E> save(E entity) {
        Optional<I> id = this.getId(entity);
        if (id.isPresent()) {
            Optional<E> source = repository.findById(id.get());
            if (source.isPresent())
                entity = merge(source.get(), entity);
        }
        return Optional.of(repository.save(entity));
    }

    @Override
    public Boolean delete(I id) {
        Optional<E> m = repository.findById(id);
        if (m.isPresent())
            this.repository.deleteById(id);
        return m.isPresent();
    }

    @Override
    public Optional<E> getById(I id) {
        return this.repository.findById(id);
    }

    @Override
    public List<E> getAll() {
        return this.repository.findAll();
    }
}
