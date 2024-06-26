package com.santanna.pixconsumidor.consumidor;

import com.santanna.pixconsumidor.domain.dto.PixDTO;
import com.santanna.pixconsumidor.domain.dto.PixStatus;
import com.santanna.pixconsumidor.domain.model.Key;
import com.santanna.pixconsumidor.domain.model.Pix;
import com.santanna.pixconsumidor.repository.KeyRepository;
import com.santanna.pixconsumidor.repository.PixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PixValidator {

    @Autowired
    private KeyRepository keyRepository;

    @Autowired
    private PixRepository pixRepository;

    @KafkaListener(topics = "pix-topic", groupId = "grupo")
    public void processaPix(PixDTO pixDTO) {
        System.out.println("Pix recebido: " + pixDTO.getIdentifier());

        Pix pix = pixRepository.findByIdentifier(pixDTO.getIdentifier());

        Key origem = keyRepository.findByChave(pixDTO.getChaveOrigem());

        Key destino =keyRepository.findByChave(pixDTO.getChaveDestino());

        if (origem == null || destino ==null){
            pix.setStatus(PixStatus.ERRO);
        }else {
            pix.setStatus(PixStatus.PROCESSADO);
        }

        pixRepository.save(pix);
    }
}
