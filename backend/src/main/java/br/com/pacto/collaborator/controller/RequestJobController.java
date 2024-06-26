package br.com.pacto.collaborator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import br.com.pacto.collaborator.configuration.web.response.ResponseBase;
import br.com.pacto.collaborator.configuration.web.response.ResponseBasePagination;
import br.com.pacto.collaborator.records.job.JobRecord;
import br.com.pacto.collaborator.records.requestjob.RequestJobRecord;
import br.com.pacto.collaborator.records.requestjob.RequestNewJobRecord;
import br.com.pacto.collaborator.service.RequestJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Tag(name = "Request Jobs", description = "Endpoints for Managing request Jobs")
@RestController
@RequestMapping("/request_job")
public class RequestJobController {

        private final RequestJobService requestJobService;

        @Autowired
        public RequestJobController(final RequestJobService requestJobService) {
                this.requestJobService = requestJobService;
        }

        @GetMapping
        @SecurityRequirement(name = "bearer-key")
        @Operation(summary = "Find Request Jobs", description = "responsible for returning Request jobs by title filter", tags = {
                        "Request Jobs" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = {
                                                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RequestJobRecord.class))) }),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public ResponseEntity<ResponseBasePagination<List<RequestJobRecord>>> findJobs(
                        @PageableDefault(sort = "createDate", direction = Direction.DESC) final Pageable pageable,
                        @RequestParam(required = false, defaultValue = "") final String title) {
                final var response = this.requestJobService.findPageJobs(pageable, title);
                final var base = ResponseBasePagination.of(response.map(RequestJobRecord::toRecord));
                return ResponseEntity.ok(base);
        }

        @PostMapping
        @Transactional
        @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
        @SecurityRequirement(name = "bearer-key")
        @Operation(summary = "Adds a new job", description = "Add a new Job", tags = {
                        "Request Jobs" }, responses = {
                                        @ApiResponse(description = "Create", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JobRecord.class))),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public ResponseEntity<ResponseBase<RequestJobRecord>> create(
                        @RequestHeader("Authorization") final String token,
                        @RequestBody final RequestNewJobRecord record) {
                final var response = this.requestJobService.create(RequestNewJobRecord.toEntity(record), token);
                final var base = ResponseBase.of(RequestJobRecord.toRecord(response));
                return ResponseEntity.ok(base);
        }

        @PutMapping("/{idRequestJob}")
        @Transactional
        @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
        @SecurityRequirement(name = "bearer-key")
        @Operation(summary = "Update request Job", description = "responsible for updated Request Jobs", tags = {
                        "Request Jobs" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestJobRecord.class))),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public ResponseEntity<ResponseBase<RequestJobRecord>> update(
                        @RequestHeader("Authorization") final String token,
                        @PathVariable final Long idRequestJob,
                        @RequestBody final RequestNewJobRecord requestJobRecord) {
                final var response = this.requestJobService
                                .update(idRequestJob, RequestNewJobRecord.toEntity(requestJobRecord), token);
                final var base = ResponseBase.of(RequestJobRecord.toRecord(response));
                return ResponseEntity.ok(base);
        }

        @DeleteMapping
        @Transactional
        @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
        @SecurityRequirement(name = "bearer-key")
        @Operation(summary = "delete Request Jobs", description = "responsible for deleting Request Jobs", tags = {
                        "Request Jobs" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public ResponseEntity<ResponseBase<String>> delete(
                        @RequestHeader("Authorization") final String token,
                        @RequestBody final Long idRequestJob) {
                this.requestJobService.delete(idRequestJob, token);
                return ResponseEntity.ok(ResponseBase.of("Success delete Request Job: " + idRequestJob));
        }

        @PatchMapping("/closed/{idRequestJob}")
        @Transactional
        @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
        @SecurityRequirement(name = "bearer-key")
        @Operation(summary = "Closed a request Job", description = "responsible for closed a request job (logic delete)", tags = {
                        "Request Jobs" }, responses = {
                                        @ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequestJobRecord.class))),
                                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                        @ApiResponse(description = "Unauthorized", responseCode = "403", content = @Content),
                                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        })
        public ResponseEntity<ResponseBase<RequestJobRecord>> closedRequestJob(
                        @RequestHeader("Authorization") final String token,
                        @PathVariable final Long idRequestJob) {
                final var response = this.requestJobService.closed(idRequestJob, token);
                final var base = ResponseBase.of(RequestJobRecord.toRecord(response));
                return ResponseEntity.ok(base);
        }

}
