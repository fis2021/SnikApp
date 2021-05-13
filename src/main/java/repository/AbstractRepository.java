package repository;

import domain.BaseEntity;
import domain.exception.CustomException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository<ID, T extends BaseEntity<ID>> implements Repository<T, ID> {
    protected Map<ID,T> elems;

    public AbstractRepository() {
        elems = new HashMap<>();
    }

    @Override
    public void add(T el) throws CustomException{
        if (elems.containsKey((el.getId())) ) {
            throw new CustomException("The element is already in the repo.");
        } else {
            elems.put(el.getId(), el);
        }
    }


    @Override
    public void delete(ID id) throws CustomException{
        if (elems.remove(id) == null) throw new CustomException("Element not in repo.");
    }

    @Override
    public void update(T el) throws CustomException{
        if (elems.containsKey(el.getId()))
            elems.put(el.getId(), el);
        else
            throw new CustomException("Element not updated.");
    }

    @Override
    public Collection<T> getAll() {
        return elems.values();
    }

    @Override
    public T findById(ID id) throws CustomException{
        if (elems.containsKey(id))
            return elems.get(id);
        else
            throw new CustomException("Element not in repo.");
    }
}