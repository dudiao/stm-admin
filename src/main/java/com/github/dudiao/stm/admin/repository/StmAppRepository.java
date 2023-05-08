package com.github.dudiao.stm.admin.repository;

import com.github.dudiao.stm.admin.model.StmApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author songyinyin
 * @since 2023/5/8 14:45
 */
@Repository
public interface StmAppRepository extends JpaRepository<StmApp, String> {

    StmApp findByName(String name);
}
