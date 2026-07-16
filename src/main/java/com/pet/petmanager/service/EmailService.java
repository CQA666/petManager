package com.pet.petmanager.service;

import com.pet.petmanager.entity.User;
import com.pet.petmanager.utils.Result;

public interface EmailService {
    Result emailRegister(User user);

    Result findByEmail(String email);
}
