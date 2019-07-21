package com.example.fotomfs.Repository;

import com.example.fotomfs.Model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
    List<Photo> findAllByUserId(Long id);
    Photo findByFileName(String fileName);
    void deleteAllByUserId(Long id);
}
