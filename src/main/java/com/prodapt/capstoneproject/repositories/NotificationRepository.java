package com.prodapt.capstoneproject.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prodapt.capstoneproject.entities.Notification;
@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

}
