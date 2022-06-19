package com.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogDAO extends JpaRepository<Blog,Integer> {

    @Query("SELECT COUNT(b) FROM Blog b WHERE b.title=?1")
    Integer checkBlogTitle(String title);

    List<Blog> findTop3ByOrderBySeenDesc();

}
