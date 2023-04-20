package vn.kien.event.eventbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.UsersPreference;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 14:34
 */
@Repository
public interface IUsersPreferenceRepository extends JpaRepository<UsersPreference, String> {
    UsersPreference findByUsersId(String usersId);

    UsersPreference findByUsersIdAndCodeAndType(String usersId, String code, String type);
}
