package vn.kien.event.eventbe.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.Users;

import java.util.List;
import java.util.Optional;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:09
 */
@Repository
public interface IUsersRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor {
    Optional<Users> findByEmail(String email);

    @Override
//    @EntityGraph("users-joined")
    Optional<Users> findById(String usersId);

    boolean existsByEmail(String email);

//    @EntityGraph("users-joined")
    Page<Users> findAll(Specification specification, Pageable pageable);

    List<Users> findAllByRolesIs(String role);
}
