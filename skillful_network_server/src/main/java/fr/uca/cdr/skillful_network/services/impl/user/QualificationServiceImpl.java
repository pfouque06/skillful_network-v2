package fr.uca.cdr.skillful_network.services.impl.user;

import fr.uca.cdr.skillful_network.entities.user.Qualification;
import fr.uca.cdr.skillful_network.repositories.user.QualificationRepository;
import fr.uca.cdr.skillful_network.services.user.QualificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class QualificationServiceImpl extends PerkServiceImpl<Qualification> implements QualificationService {

    public QualificationServiceImpl(QualificationRepository repository) {
        super(repository, "qualification");
    }

    @Override
    public List<Qualification> getCandidates(String keyword) {
        return ((QualificationRepository) this.repository).search(keyword).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None skill could be found with the keyword %s", keyword)));
    }

    @Override
    public Qualification getByName(String name) {
        return ((QualificationRepository) this.repository).findByName(name).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("None skill could be found with the name %s", name)));
    }
}
