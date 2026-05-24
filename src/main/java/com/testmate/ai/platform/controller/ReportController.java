package com.testmate.ai.platform.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final Path CUCUMBER_REPORT = Path.of("target", "cucumber-report.html");
    private static final Path AI_REPORT_DIR = Path.of("target", "testmate-ai-reports");

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listReports() throws IOException {
        List<Map<String, Object>> reports = new java.util.ArrayList<>();
        addReportIfPresent(reports, "cucumber-report", CUCUMBER_REPORT);

        if (Files.exists(AI_REPORT_DIR)) {
            try (var stream = Files.list(AI_REPORT_DIR)) {
                stream.filter(Files::isRegularFile)
                        .forEach(path -> addReportIfPresent(reports, path.getFileName().toString(), path));
            }
        }

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/cucumber")
    public ResponseEntity<Resource> downloadCucumberReport() {
        if (!Files.exists(CUCUMBER_REPORT)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(CUCUMBER_REPORT);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cucumber-report.html")
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }

    @GetMapping("/ai/{fileName}")
    public ResponseEntity<Resource> downloadAiReport(@PathVariable String fileName) {
        Path reportPath = AI_REPORT_DIR.resolve(fileName).normalize();
        if (!reportPath.startsWith(AI_REPORT_DIR) || !Files.exists(reportPath)) {
            return ResponseEntity.notFound().build();
        }
        Resource resource = new FileSystemResource(reportPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private void addReportIfPresent(List<Map<String, Object>> reports, String name, Path path) {
        try {
            if (Files.exists(path) && Files.isRegularFile(path)) {
                reports.add(Map.of(
                        "name", name,
                        "path", path.toString(),
                        "sizeBytes", Files.size(path)
                ));
            }
        } catch (IOException ignored) {
            // Report discovery should not fail the whole API response.
        }
    }
}
