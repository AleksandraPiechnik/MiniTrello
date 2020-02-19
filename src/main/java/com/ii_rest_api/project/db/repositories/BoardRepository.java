package com.ii_rest_api.project.db.repositories;

import com.ii_rest_api.project.db.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer > {
}
