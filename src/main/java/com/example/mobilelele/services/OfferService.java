package com.example.mobilelele.services;

import com.example.mobilelele.models.dtos.OfferDTOs.CreateOfferDTO;
import com.example.mobilelele.models.dtos.OfferDTOs.OfferCardListingDTO;
import com.example.mobilelele.models.entities.ModelEntity;
import com.example.mobilelele.models.entities.OfferEntity;
import com.example.mobilelele.models.entities.UserEntity;
import com.example.mobilelele.models.sessions.UserSession;
import com.example.mobilelele.repositories.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private OfferRepository offerRepository;
    private ModelMapper modelMapper;
    private ModelService modelService;
    private UserSession currentUser;
    private UserService userService;

    public OfferService(OfferRepository offerRepository, ModelMapper modelMapper, ModelService modelService, UserSession currentUser, UserService userService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.modelService = modelService;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @Transactional
    public void addNewOffer(CreateOfferDTO offerDTO) {
        OfferEntity newOffer = this.modelMapper.map(offerDTO, OfferEntity.class);

        Optional<ModelEntity> carModel = this.modelService.getModelByName(offerDTO.getModel());
        ModelEntity model;

        if (carModel.isEmpty()) {
           model = this.modelService.createNewModel(offerDTO);
        } else {
            model = carModel.get();
        }

        newOffer.setModel(model);

        UserEntity user = this.userService
                .findUserByUsername(this.currentUser.getUsername())
                .orElseThrow();

        newOffer.setSeller(user);

        this.offerRepository.save(newOffer);
    }

    public List<OfferCardListingDTO> getAllOffers() {
        return this.offerRepository
                .findAll()
                .stream()
                .map(offer -> {
                    OfferCardListingDTO offerDTO = this.modelMapper.map(offer, OfferCardListingDTO.class);
                    offerDTO.setBrand(offer.getModel().getBrand().getBrandName());
                    return offerDTO;
                })
                .toList();
    }
}
