package vn.kien.event.eventbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.kien.event.eventbe.entity.FileData;

/**
 * Le-Hong-Quan
 * Date: 04/03/2023
 * Time: 21:53
 */
public interface IFileDataRepository extends JpaRepository<FileData,Long> {
}
