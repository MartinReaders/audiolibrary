package com.audiolibrary.web.repository;

import com.audiolibrary.web.model.Album;

import com.audiolibrary.web.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {


Optional <Album> findAlbumByArtist(Long id);



}
