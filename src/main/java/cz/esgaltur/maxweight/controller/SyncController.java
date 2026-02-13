package cz.esgaltur.maxweight.controller;

import cz.esgaltur.maxweight.api.SyncApi;
import cz.esgaltur.maxweight.api.model.SaveProgramRequest;
import cz.esgaltur.maxweight.api.model.SavedProgramResponse;
import cz.esgaltur.maxweight.service.SyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * REST controller for sync endpoints.
 */
@RestController
public class SyncController implements SyncApi {
    
    private static final Logger logger = LoggerFactory.getLogger(SyncController.class);
    
    @Autowired
    private SyncService syncService;
    
    @Override
    public ResponseEntity<List<SavedProgramResponse>> getSavedPrograms(OffsetDateTime since) {
        logger.info("Getting saved programs, since: {}", since);
        List<SavedProgramResponse> programs = syncService.getSavedPrograms(since);
        return ResponseEntity.ok(programs);
    }
    
    @Override
    public ResponseEntity<SavedProgramResponse> saveProgram(SaveProgramRequest saveProgramRequest) {
        logger.info("Saving program for week: {}", saveProgramRequest.getWeekNumber());
        SavedProgramResponse response = syncService.saveProgram(saveProgramRequest);
        return ResponseEntity.ok(response);
    }
    
    @Override
    public ResponseEntity<Void> deleteProgram(Integer weekNumber) {
        logger.info("Deleting program for week: {}", weekNumber);
        syncService.deleteProgram(weekNumber);
        return ResponseEntity.noContent().build();
    }
}