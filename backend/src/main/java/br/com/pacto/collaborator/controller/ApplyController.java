package br.com.pacto.collaborator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pacto.collaborator.configuration.web.response.ResponseBase;
import br.com.pacto.collaborator.configuration.web.response.ResponseBasePagination;
import br.com.pacto.collaborator.records.apply.ApplyNewRecord;
import br.com.pacto.collaborator.records.apply.ApplyRecord;
import br.com.pacto.collaborator.records.apply.ModifyApply;
import br.com.pacto.collaborator.service.ApplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@Tag(name = "Apply", description = "Endpoints for Managing request applies")
@RestController
@RequestMapping("/apply")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('EMPLOYEE')")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Adds a new Apply", description = "Add a new Apply", tags = {
            "Apply" }, responses = {
                    @ApiResponse(description = "Create", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplyRecord.class))),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseBase<ApplyRecord>> create(
            @RequestHeader("Authorization") final String token,
            @RequestBody final ApplyNewRecord record) {
        final var response = this.applyService.create(ApplyNewRecord.toEntity(record), token);
        final var base = ResponseBase.of(ApplyRecord.toRecord(response));
        return ResponseEntity.ok(base);
    }    

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Find Apply", description = "responsible for returning Applys with user", tags = {
            "Apply" }, responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ApplyRecord.class))) }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<ResponseBasePagination<List<ApplyRecord>>> showStatusByApplyJobs(
            @RequestHeader("Authorization") final String token,
            @PageableDefault(sort = "applyedDate", direction = Direction.DESC) final Pageable pageable) {
        final var response = this.applyService.findApplyByUser(pageable, token);
        final var base = ResponseBasePagination.of(response.map(ApplyRecord::toRecord));
        return ResponseEntity.ok(base);
    }

    @PatchMapping("/status")
    @Transactional
    @PreAuthorize("hasRole('ADMINISTRATOR') || hasRole('RESPONSIBLE_RECRUITER')")
    @SecurityRequirement(name = "bearer-key")
    @Operation(summary = "Update status", description = "Update Apply Status ", tags = {
        "Apply" }, responses = {
                @ApiResponse(description = "Create", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApplyRecord.class))),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public ResponseEntity<ResponseBase<ApplyRecord>> modifyApplyStatus(
        @RequestHeader("Authorization") final String token,
        @RequestBody final ModifyApply record
    ) {
        final var response = this.applyService.modifyApplyStatus(record.applyId(), record.status(), record.reason(), token);
        final var base = ResponseBase.of(ApplyRecord.toRecord(response));
        return ResponseEntity.ok(base);
    }


}
