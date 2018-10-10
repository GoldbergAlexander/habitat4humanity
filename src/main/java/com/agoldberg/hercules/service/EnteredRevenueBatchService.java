package com.agoldberg.hercules.service;

import com.agoldberg.hercules.dto.EnteredRevenueBatchDTO;
import com.agoldberg.hercules.dto.EnteredRevenueDTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EnteredRevenueBatchService {
    public EnteredRevenueBatchDTO processBatchData(EnteredRevenueBatchDTO dto){
        String data = dto.getData();
        String[] lines = data.split("\\s*\\r?\\n\\s*");

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        for(String line : lines){
            String[] object = line.split(",");
            try {
                Date date = simpleDateFormat.parse(object[0]);

                Double value = Double.parseDouble(object[1]);

                EnteredRevenueDTO enteredRevenueDTO = new EnteredRevenueDTO();
                enteredRevenueDTO.setLocationId(dto.getLocationId());
                enteredRevenueDTO.setDate(date);
                enteredRevenueDTO.setCashCount(value);
                dto.getEnteredRevenueDTOList().add(enteredRevenueDTO);
            }catch (ParseException e){

            }


        }

        return dto;
    }
}
