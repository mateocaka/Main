package com.example.demo.models;
import org.springframework.data.repository.CrudRepository;
public interface TaskRepository extends CrudRepository<Task,Long> {

}
