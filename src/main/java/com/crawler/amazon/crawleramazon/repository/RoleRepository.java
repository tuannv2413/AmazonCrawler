package com.crawler.amazon.crawleramazon.repository;

import com.crawler.amazon.crawleramazon.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
