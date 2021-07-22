package com.example.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathResponse {

	private String date_created;
	private String path;
	private int numOfDirs;
	private int numOfFiles;
	private String totalFileSize;
}
