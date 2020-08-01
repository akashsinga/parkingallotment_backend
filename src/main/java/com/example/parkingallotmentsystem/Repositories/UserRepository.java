package com.example.parkingallotmentsystem.Repositories;

import com.example.parkingallotmentsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>
{
    Optional<User> findByUsername(String username);
    User getByUsername(String username);
    @Query(value="Select count(*) from users where type='user'",nativeQuery = true)
    int getUserCount();
    @Query(value="select * from users where id=:id",nativeQuery = true)
    User getById(@Param("id") int user_id);
}
