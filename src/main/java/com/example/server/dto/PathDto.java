package com.example.server.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PathDto {

	@NotNull
	String date_created;

	@NotBlank
	String path;

	List<SimpleFileDto> nestedFiles;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SimpleFileDto {

		FileType fileType;
		String name;
		long size;
	}
}
