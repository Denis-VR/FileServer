package com.example.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paths", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Path {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String date_created;

	private String path;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "path")
	private List<SimpleFile> files;

	public void addFileToPath(SimpleFile file) {
		if (files == null) {
			files = new ArrayList<>();
		}
		files.add(file);
		file.setPath(this);
	}

	@Override
	public String toString() {
		return "Path{" +
				"date='" + date_created + '\'' +
				", path='" + path + '\'' +
				'}';
	}
}
