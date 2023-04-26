package vn.kien.event.eventbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;
import vn.kien.event.eventbe.entity.SequenceValueItem;

/**
 * Le-Hong-Quan
 * Date: 19/02/2023
 * Time: 11:39
 */
@Repository
public interface ISequenceValueItemRepository extends JpaRepository<SequenceValueItem, String> {
    SequenceValueItem findBySeqName(String sequenceName);
}
