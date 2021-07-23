package com.example.server.controller;

import com.example.server.dto.FileDto;
import com.example.server.dto.PathDto;
import com.example.server.dto.PathResponse;
import com.example.server.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	@PostMapping("/path")
	public ResponseEntity<?> addPath(@RequestBody @Valid PathDto pathDto) {
		fileService.savePath(pathDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/path")
	public ResponseEntity<List<PathResponse>> getAllPaths() {
		return new ResponseEntity<>(fileService.getAllPaths(), HttpStatus.OK);
	}

	@GetMapping("/file")
	public ResponseEntity<List<FileDto>> getFilesForPath(String path) {
		return new ResponseEntity<>(fileService.getFilesForPath(path), HttpStatus.OK);
	}

	@GetMapping("/fileExist")
	public ResponseEntity<Boolean> checkFileExist(String path) {
		return new ResponseEntity<>(fileService.pathExist(path), HttpStatus.OK);
	}
}
