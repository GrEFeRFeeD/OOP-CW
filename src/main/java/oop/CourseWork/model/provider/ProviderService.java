package oop.CourseWork.model.provider;

import oop.CourseWork.model.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private ProviderRepository providerRepository;
    private OrderService orderService;

    @Autowired
    public ProviderService(ProviderRepository providerRepository, OrderService orderService) {
        this.providerRepository = providerRepository;
        this.orderService = orderService;
    }

    public Provider getProviderById(Long id) { return providerRepository.getById(id); }
    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    public void addProvider(Provider provider) { providerRepository.save(provider); }

    public void deleteProvider(Long providerId) {
        Optional<Provider> providerOptional = providerRepository.findById(providerId);
        if (providerOptional.isPresent()) {
            orderService.nullifyProvider(providerOptional.get());
            providerRepository.delete(providerOptional.get());
        }
    }
}
