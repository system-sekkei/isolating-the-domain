package example.application.service;

import example.domain.model.worker.*;
import org.springframework.stereotype.Service;

@Service
public class WorkerService {

    WorkerRepository workerRepository;

    public Worker findById(WorkerIdentifier id) {
        return workerRepository.findBy(id);
    }

    public Workers list() {
        return workerRepository.list();
    }

    public Worker register(UserCandidate user) {
        return workerRepository.register(user);
    }

    public void updateName(WorkerIdentifier identifier, Name name) {
        workerRepository.updateName(identifier, name);
    }

    public void updateMailAddress(WorkerIdentifier identifier, MailAddress mailAddress) {
        workerRepository.updateMailAddress(identifier, mailAddress);
    }

    public void updatePhoneNumber(WorkerIdentifier identifier, PhoneNumber phoneNumber) {
        workerRepository.updatePhoneNumber(identifier, phoneNumber);
    }

    public void delete(Worker worker) {
        workerRepository.delete(worker);
    }

    WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

}
