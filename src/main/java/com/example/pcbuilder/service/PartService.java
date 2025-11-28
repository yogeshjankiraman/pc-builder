package com.example.pcbuilder.service;

import com.example.pcbuilder.model.Part;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartService {
    private final List<Part> parts = new ArrayList<>();

    public PartService() {
        // CPUs
        parts.add(new Part("cpu1", "CPU", "Intel Core i5-12400F", 12000));
        parts.add(new Part("cpu2", "CPU", "AMD Ryzen 5 5600X", 15000));
        parts.add(new Part("cpu3", "CPU", "Intel Core i7-12700K", 28000));
        parts.add(new Part("cpu4", "CPU", "AMD Ryzen 7 5800X", 26000));

        // Motherboards
        parts.add(new Part("mb1", "Motherboard", "ASUS Prime B550M", 9000));
        parts.add(new Part("mb2", "Motherboard", "MSI MAG B660M", 11000));

        // GPUs
        parts.add(new Part("gpu1", "GPU", "NVIDIA GTX 1660 Super", 22000));
        parts.add(new Part("gpu2", "GPU", "NVIDIA RTX 3060", 38000));
        parts.add(new Part("gpu3", "GPU", "AMD Radeon RX 6600 XT", 32000));

        // RAM
        parts.add(new Part("ram1", "RAM", "Corsair Vengeance 16GB (2x8)", 4500));
        parts.add(new Part("ram2", "RAM", "G.Skill Trident Z 32GB (2x16)", 9000));
        parts.add(new Part("ram3", "RAM", "Kingston Fury 8GB", 2200));

        // Storage
        parts.add(new Part("ssd1", "Storage", "Samsung 970 EVO 500GB", 6000));
        parts.add(new Part("hdd1", "Storage", "Seagate 2TB HDD", 4000));

        // PSU, Case
        parts.add(new Part("psu1", "PSU", "Corsair 650W 80+ Bronze", 5000));
        parts.add(new Part("case1", "Case", "NZXT H510", 4500));
    }

    public List<Part> getAll() {
        return new ArrayList<>(parts);
    }

    public Optional<Part> findById(String id) {
        return parts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Part> findByCategory(String category) {
        return parts.stream().filter(p -> p.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }
}
