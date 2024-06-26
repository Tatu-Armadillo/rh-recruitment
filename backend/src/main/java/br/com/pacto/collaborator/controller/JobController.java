package br.com.pacto.collaborator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pacto.collaborator.configuration.web.response.ResponseBase;
import br.com.pacto.collaborator.configuration.web.response.ResponseBasePagination;
import br.com.pacto.collaborator.records.job.JobRecord;
import br.com.pacto.collaborator.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Tag(name = "Jobs", description = "Endpoints for Managing jobs")
@RestController
@RequestMapping("/job")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Adds a requestJob", description = "Add a requestJob", tags = {
            "Jobs" }, responses = {
                    @ApiResponse(description = "Create", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobRecord.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseBase<JobRecord>> create(@RequestBody final JobRecord record) {
        final var response = this.jobService.create(JobRecord.toEntity(record));
        final var base = ResponseBase.of(JobRecord.toRecord(response));
        return ResponseEntity.ok(base);
    }

    @GetMapping
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Find Jobs", description = "responsible for returning jobs by title filter", tags = {
            "Jobs" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = JobRecord.class))) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseBasePagination<List<JobRecord>>> findJobs(
            @PageableDefault(sort = "title", direction = Direction.ASC) final Pageable pageable,
            @RequestParam(required = false, defaultValue = "") final String title) {
        final var response = this.jobService.findPageJobs(pageable, title);
        final var base = ResponseBasePagination.of(response.map(JobRecord::toRecord));
        return ResponseEntity.ok(base);
    }

}
