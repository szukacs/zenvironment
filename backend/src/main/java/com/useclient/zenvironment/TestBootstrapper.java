package com.useclient.zenvironment;

import com.useclient.zenvironment.model.dao.PlantType;
import com.useclient.zenvironment.repository.PlantTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestBootstrapper {
    private final PlantTypeRepository plantTypeRepository;

    @Bean
    public String bootstrapTestData() {
        plantTypeRepository.deleteAll();

        plantTypeRepository.save(new PlantType(null, "Tomato", "https://images.unsplash.com/photo-1592841200221-a6898f307baa", 4.1, 2.0, "kg"));

        return "hello!";
    }
}
