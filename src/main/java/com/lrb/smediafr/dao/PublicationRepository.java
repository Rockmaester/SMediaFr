package com.lrb.smediafr.dao;



import com.lrb.smediafr.entity.Publication;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationRepository extends CrudRepository<Publication, Long> {
    List<Publication> findPublicationByTitle(String tag);
}
