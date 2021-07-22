package com.example.server.entity;

import com.example.server.dto.FileType;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "files")
public class SimpleFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private FileType fileType;

	private String name;

	private long size;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "path_id")
	private Path path;

	@Override
	public String toString() {
		return "SimpleFile{" +
				"fileType=" + fileType +
				", name='" + name + '\'' +
				", size=" + size +
				'}';
	}
}
