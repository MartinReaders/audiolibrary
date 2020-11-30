package com.audiolibrary.web.repository;


import com.audiolibrary.web.model.Album;
import com.audiolibrary.web.model.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ArtistRepository extends CrudRepository <Artist, Long> {

    Artist findByName(String name);
    Page <Artist> findAll(Pageable pageable);

    Page<Artist> findByNameContainingIgnoreCase(String name, Pageable pageable);


   Artist findArtistById(Long id);
    //@Query("select e from Album e where e.artist = (:artist_id)")
   // List<Artist> findByNomOrPrenomAllIgnoreCase(@Param("artist_id") String artist_id);

}
