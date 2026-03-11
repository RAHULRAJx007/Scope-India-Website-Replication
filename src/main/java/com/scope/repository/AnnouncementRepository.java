package com.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.model.Announcement;

@Repository
public interface AnnouncementRepository
		extends JpaRepository<Announcement, Long> {

}
