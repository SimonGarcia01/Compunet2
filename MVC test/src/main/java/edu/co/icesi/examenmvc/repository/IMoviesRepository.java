package edu.co.icesi.examenmvc.repository;

import edu.co.icesi.examenmvc.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMoviesRepository extends JpaRepository<Movie, Long>{
    
}
