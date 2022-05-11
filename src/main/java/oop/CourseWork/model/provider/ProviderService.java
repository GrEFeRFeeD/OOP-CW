package oop.CourseWork.model.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    private ProviderRepository providerRepository;

    @Autowired
    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    public Provider getProviderById(Long id) { return providerRepository.getById(id); }
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }
}
