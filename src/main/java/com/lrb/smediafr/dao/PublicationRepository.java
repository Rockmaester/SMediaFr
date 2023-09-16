package com.lrb.smediafr.dao;



import com.lrb.smediafr.entity.Publication;
import com.lrb.smediafr.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Long> {
    Page<Publication> findPublicationByTitle(String tag, Pageable pageable);
    Page<Publication> findAll (Pageable pageable);
    @Query("from Publication p where p.author =:author")
    Page<Publication> findByUser(Pageable pageable, User author);
}
