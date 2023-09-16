package com.lrb.smediafr.service;

import com.lrb.smediafr.dao.PublicationRepository;
import com.lrb.smediafr.entity.Publication;
import com.lrb.smediafr.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    public Page<Publication> getPublicationsByUser(Pageable pageable, User author) {
        return publicationRepository.findByUser(pageable, author);
    }
}
