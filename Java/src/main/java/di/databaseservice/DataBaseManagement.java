package di.databaseservice;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class DataBaseManagement {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void clearTables() {
        try {
            entityManager.createNativeQuery("SET session_replication_role = 'replica';").executeUpdate();
            List<String> entityNames = List.of("AbstractBoat", "Boat", "Booking", "Seat", "Trip","User","Telephone");
            entityNames.forEach(entityName -> {
                entityManager.createQuery("DELETE FROM " + entityName).executeUpdate();
            });
            entityManager.createNativeQuery("SET session_replication_role = 'origin';").executeUpdate();
        } catch (Exception e) {
            // Логирование или другие действия по обработке исключения
            System.out.println("Ошибка при очистке таблиц: " + e.getMessage());
        }
    }

}
