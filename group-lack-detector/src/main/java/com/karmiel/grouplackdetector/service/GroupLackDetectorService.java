package com.karmiel.grouplackdetector.service;

import com.karmiel.grouplackdetector.dto.ContainerData;
import com.karmiel.grouplackdetector.entity.Container;
import com.karmiel.grouplackdetector.repo.ContainersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class GroupLackDetectorService {
    @Autowired
    ContainersRepo containersRepo;

    public Container getContainer (ContainerData data){
        System.out.println("Repo started with");
        System.out.println("container id: "+data.spotCoordinates());
        String containerId = data.spotCoordinates();
        return containersRepo.findById(containerId)
                .orElseThrow(()-> new NoSuchElementException(String.format("Container with id %s not exist",containerId)));
    }
    public void saveContainer (Container container){
        System.out.println("Save method started");

        System.out.println(containersRepo.save(container));
    }

}
