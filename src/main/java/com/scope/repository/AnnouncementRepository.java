package com.scope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.model.Announcement;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByOrderByPublishedAtDesc();

}
