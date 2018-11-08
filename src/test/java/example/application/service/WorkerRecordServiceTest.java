package example.application.service;

import example.Application;
import example.application.service.worker.WorkerQueryService;
import example.application.service.worker.WorkerRecordService;
import example.domain.model.worker.*;
import example.infrastructure.datasource.worker.WorkerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class WorkerRecordServiceTest {
    @Autowired
    WorkerRecordService sut;
    @Autowired
    WorkerQueryService query;

    @Test
    void list() {
        Worker worker = query.list().list().stream().filter(
                us -> us.identifier().value().equals(1L)).findFirst().get();
        assertAll(
                () -> assertEquals(worker.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(worker.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(worker.name().toString(), "布川 光義"));
    }

    @Test
    void findById() {
        Worker worker = query.findById(new WorkerIdentifier(1L));
        assertAll(
                () -> assertEquals(worker.mailAddress().toString(), "fukawa_teruyoshi_new@example.com"),
                () -> assertEquals(worker.phoneNumber().toString(), "03-1234-9999"),
                () -> assertEquals(worker.name().toString(), "布川 光義"));
    }

    @Test
    void registerAndDelete() {
        Name name = new Name("Eiji Yamane");
        PhoneNumber phoneNumber = new PhoneNumber("090-6559-1234");
        MailAddress mailAddress = new MailAddress("hogehoge_hogeo@example.com");
        UserCandidate user = new UserCandidate(name, mailAddress, phoneNumber);
        Worker registeredWorker = sut.register(user);
        Worker foundWorker = query.findById(registeredWorker.identifier());
        assertAll(
                () -> assertEquals(foundWorker.name().toString(), user.name().toString()),
                () -> assertEquals(foundWorker.phoneNumber().toString(), user.phoneNumber().toString()),
                () -> assertEquals(foundWorker.mailAddress().toString(), user.mailAddress().toString())
        );
        sut.delete(foundWorker);

        assertThrows(WorkerNotFoundException.class,
                () -> query.findById((foundWorker.identifier())));
    }
}
