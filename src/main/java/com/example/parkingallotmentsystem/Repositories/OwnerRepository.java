package com.example.parkingallotmentsystem.Repositories;

import com.example.parkingallotmentsystem.Models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnerRepository extends JpaRepository<Owner,Integer> {
    @Query(value="select * from parking_owners where email=:email",nativeQuery = true)
    Owner getByEmail(@Param("email")String owner_email);
}
