package com.example.parkingallotmentsystem.Repositories;

import com.example.parkingallotmentsystem.Models.PasswordResets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResets,Integer> {
    @Query(value = "select * from password_resets where user_id=:id and status='code_sent' order by created_date_time DESC LIMIT 1",nativeQuery = true)
    PasswordResets getByUserID(@Param("id")int id);
}
