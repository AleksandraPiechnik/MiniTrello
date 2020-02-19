package com.ii_rest_api.project.db.repositories;

import com.ii_rest_api.project.db.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer > {
}
