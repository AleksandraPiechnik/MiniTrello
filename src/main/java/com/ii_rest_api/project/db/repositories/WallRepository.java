package com.ii_rest_api.project.db.repositories;

import com.ii_rest_api.project.db.model.Wall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WallRepository extends JpaRepository<Wall,Integer > {
}