package edu.co.icesi.examjpatemplate.repository;

import edu.co.icesi.examjpatemplate.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {
    //Find the buses that attend a particular type
    public List<Bus> findByRoute_Type(String type);
}
