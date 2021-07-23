package com.example.server.service;

import com.example.server.dto.FileDto;
import com.example.server.dto.FileType;
import com.example.server.dto.PathDto;
import com.example.server.dto.PathResponse;
import com.example.server.entity.Path;
import com.example.server.entity.SimpleFile;
import com.example.server.repository.PathRepository;
import com.example.server.repository.SimpleFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.server.utils.StringSplitter.compareStrings;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final PathRepository pathRepository;
	private final SimpleFileRepository fileRepository;

	public void savePath(PathDto pathDto) {
		if (pathRepository.findByPath(pathDto.getPath()).isPresent()) return;

		Path path = new Path();
		path.setPath(pathDto.getPath());
		path.setDate_created(pathDto.getDate_created());

		List<PathDto.SimpleFileDto> list = pathDto.getNestedFiles();
		if (list != null) {
			list.forEach(simpleFileDto -> {
				SimpleFile simpleFile = new SimpleFile();
				simpleFile.setFileType(simpleFileDto.getFileType());
				simpleFile.setName(simpleFileDto.getName());
				simpleFile.setSize(simpleFileDto.getSize());
				simpleFile.setPath(path);
				path.addFileToPath(simpleFile);
				log.info("Add file " + simpleFile + " to " + path.getPath());
			});
		}

		pathRepository.save(path);
		log.info("Save: " + path);
	}

	public List<PathResponse> getAllPaths() {
		List<PathResponse> request = new ArrayList<>();
		List<Path> pathRequests = pathRepository.findAll();

		pathRequests.forEach(path -> {
			int numOfDirs = 0;
			int numOfFiles = 0;
			long totalFileSize = 0;
			if (path.getFiles().size() != 0) {
				numOfDirs = fileRepository.countAllByFileTypeAndPathId(FileType.DIRECTORY, path.getId());
				numOfFiles = fileRepository.countAllByFileTypeAndPathId(FileType.FILE, path.getId());
				totalFileSize = fileRepository.sumSizeByPathId(path.getId());
			}
			PathResponse pathResponse = new PathResponse(
					path.getDate_created(), path.getPath(), numOfDirs, numOfFiles, determineSize(totalFileSize)
			);
			request.add(pathResponse);
		});
		return request;
	}

	public List<FileDto> getFilesForPath(String path) {
		return pathRepository.findByPath(path).map(
				path1 -> path1.getFiles().stream()
						.sorted((file1, file2) -> {
							if (file1.getFileType() != file2.getFileType()) {
								return file1.getFileType() == FileType.DIRECTORY ? -1 : 1;
							} else return compareStrings(file1.getName().toLowerCase(), file2.getName().toLowerCase());
						})
						.map(file -> new FileDto(file.getName(), determineSize(file)))
						.collect(Collectors.toList())).orElse(null);
	}

	public String determineSize(SimpleFile file) {
		if (file.getFileType() != FileType.FILE) {
			return "&lt;DIR&gt;";
		}
		return determineSize(file.getSize());
	}

	public String determineSize(long size) {
		if (size >= 1024 * 1024) return getFileSizeMegaBytes(size);
		else if (size >= 1024) return getFileSizeKiloBytes(size);
		else return getFileSizeBytes(size);
	}

	private static String getFileSizeMegaBytes(long size) {
		return String.format("%.2f", (double) size / (1024 * 1024)) + " mb";
	}

	private static String getFileSizeKiloBytes(long size) {
		return String.format("%.2f", (double) size / 1024) + " kb";
	}

	private static String getFileSizeBytes(long size) {
		return size + " bytes";
	}
}
