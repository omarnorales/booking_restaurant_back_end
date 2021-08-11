package com.odre.bookingrestaurantapi01.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odre.bookingrestaurantapi01.entities.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	Optional<Reservation> findById(Long id);

	Optional<Reservation> findByLocator(String locator);

	@Modifying
	@Transactional
	Optional<Reservation> deleteByLocator(String locator);

	//@Modifying
	@Transactional
	Optional<Reservation> findByTurnAndRestaurantId(String turn, Long restaurantId);
	
	@Transactional
	Optional<Reservation> findByTurnAndRestaurantIdAndDate(String turn, Long restaurantId, Date date);
	
	

//	@Modifying
//	@Transactional
//	List<Reservation> findByRestaurantId(Long restaurantId);
	
	
	@Query(value = "select RESE FROM Reservation RESE WHERE RESE.RESTAURANT_ID = :RESTAURANT_ID", nativeQuery = true)
	public List<Reservation> queryFindByRestaurantId(@Param("RESTAURANT_ID") Long RESTAURANT_ID);
	
	
	
//	@Query(value = "select RESE FROM Reservation RESE "
//			+ "WHERE "
//			+ "RESE.RESTAURANT_ID = :RESTAURANT_ID "
//			+ "AND RESE.TURN = : TURN "
//			+ "AND year(RESE.DATE) = year(:DATE) "
//			+ "AND month(RESE.DATE) = month(:DATE) "
//			+ "AND day(RESE.DATE) = day(:DATE) ", 
//			nativeQuery = true)
//	List<Reservation>  queryFindByTurnAndRestaurantIdAndDate(@Param("TURN") String turn,
//																@Param("RESTAURANT_ID") Long restaurant_id,
//														   		@Param("DATE") Date day);

}
