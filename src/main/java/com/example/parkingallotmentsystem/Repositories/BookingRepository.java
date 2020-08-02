package com.example.parkingallotmentsystem.Repositories;

import com.example.parkingallotmentsystem.Models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer>
{
    @Query(value="select * from bookings where user_id=:id order by booking_date DESC",nativeQuery = true)
    List<Booking> getPreviousBookings(@Param("id")int id);

    @Query(value ="select count(*) from bookings where :fromtime between fromdatetime and todatetime or :totime between fromdatetime and todatetime and location_id=:id ",nativeQuery = true)
    Long isAvailable(@Param("fromtime")LocalDateTime fromdatetime,@Param("totime")LocalDateTime todatetime,@Param("id")int location_id);

    @Query(value ="select * from bookings where location_id=:id and status=:status and :fromtime between fromdatetime and todatetime order by booking_date ASC LIMIT 1",nativeQuery = true)
    Booking getWaitingListReservation(@Param("id")int id,@Param("fromtime")LocalDateTime fromdatetime,@Param("status") String status);
}
