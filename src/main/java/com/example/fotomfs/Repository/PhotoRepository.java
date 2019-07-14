package com.example.fotomfs.Repository;

import com.example.fotomfs.Model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {
}
