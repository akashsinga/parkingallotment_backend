package com.example.parkingallotmentsystem.Repositories;

import com.example.parkingallotmentsystem.Models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ParkingLocationRepository extends JpaRepository<Location,Integer>
{
    @Override
    @Query(value="select * from parking_lots where status='active'",nativeQuery = true)
    List<Location> findAll();
    @Override
    @Query(value="select count(*) from parking_lots where status='active'",nativeQuery = true)
    long count();
    @Query(value="select * from parking_lots where area_id=:id and slot=:slot and status='active'",nativeQuery = true)
    Location getByAreaSlot(@Param("id")int id, @Param("slot") int slot);
    @Modifying
    @Transactional
    @Query(value="update parking_lots SET status='inactive' where area_id=:id",nativeQuery = true)
    void setInactive(@Param("id")int id);
}