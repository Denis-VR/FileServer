package com.example.server.repository;

import com.example.server.dto.FileType;
import com.example.server.entity.SimpleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SimpleFileRepository extends JpaRepository<SimpleFile, Long> {

	int countAllByFileTypeAndPathId(FileType fileType, Long pathId);

	@Query(value = "SELECT SUM(size) FROM FILES " +
			"WHERE FILES.PATH_ID = :pathId",
			nativeQuery = true)
	long sumSizeByPathId(@Param("pathId") Long pathId);

	@Query(value = "SELECT * FROM FILES " +
			"WHERE FILES.PATH_ID = :pathId " +
			"ORDER BY FILE_TYPE desc, NAME",
			nativeQuery = true)
	List<SimpleFile> getSortedFilesByPath(Long pathId);
}
