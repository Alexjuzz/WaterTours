package di.databaseservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class DataBaseController {
    @Autowired
    private DataBaseManagement service;

    @PostMapping("/c/all")
    public ResponseEntity<?> clearTables() {
        service.clearTables();
        return ResponseEntity.ok().body("All tables have been cleared.");
    }
}
