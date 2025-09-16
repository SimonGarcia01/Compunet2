package edu.co.icesi.examjpatemplate.repository;

import edu.co.icesi.examjpatemplate.entity.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeoPointRepository extends JpaRepository<GeoPoint,Integer> {
    //Find the buses that attend a specific route
    public List<GeoPoint> findByBus_Route_RouteName(String routeName);

    //find the last location of a bus using the license plate
    public Optional<GeoPoint> findFirstByBus_LicensePlateOrderByTimestampDesc(String licensePlate);
    //ByOrderByTimestampDesc
}
