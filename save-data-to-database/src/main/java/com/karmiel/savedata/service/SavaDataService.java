package com.karmiel.savedata.service;

import com.karmiel.savedata.dto.QuantityDto;

public interface SavaDataService {

    /**
     * Save data to database
     *
     * @param data
     */
    void saveData(QuantityDto data);
}
