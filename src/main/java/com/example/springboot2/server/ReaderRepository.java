package com.example.springboot2.server;

import com.example.springboot2.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * 和BookRepository类似，你无需自己实现ReaderRepository。这是因为它扩展了
 * JpaRepository，Spring Data JPA会在运行时自动创建它的实现。这为你提供了18个操作Reader
 * 实体的方法
 *
 * 持久化"读者"
 */
public interface ReaderRepository extends JpaRepository<Reader, String> {

    UserDetails findOne(String s);
}
