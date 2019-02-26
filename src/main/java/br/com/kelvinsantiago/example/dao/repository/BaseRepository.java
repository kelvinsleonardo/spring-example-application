package br.com.kelvinsantiago.example.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    @Query("SELECT t FROM #{#entityName} t WHERE t.id IN :ids")
    List<T> findByIdsIn(@Param("ids") List<ID> ids);

}
