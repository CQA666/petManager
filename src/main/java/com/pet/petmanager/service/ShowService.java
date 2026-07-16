package com.pet.petmanager.service;

import com.pet.petmanager.utils.Result;

public interface ShowService {
    Result getAdoptTrend();

    Result getStatistics();

    Result getAnimalStatus();

    Result getAnimalBreed();
}