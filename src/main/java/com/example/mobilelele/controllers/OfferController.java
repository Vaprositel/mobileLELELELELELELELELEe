package com.example.mobilelele.controllers;

import com.example.mobilelele.models.dtos.OfferDTOs.CreateOfferDTO;
import com.example.mobilelele.models.dtos.OfferDTOs.OfferCardListingDTO;
import com.example.mobilelele.services.OfferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class OfferController {
    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @ModelAttribute(name = "addOfferModel")
    public CreateOfferDTO initOfferModel() {
        return new CreateOfferDTO();
    }

    @GetMapping("/offers/all")
    public String getAllOffers(Model model) {
        List<OfferCardListingDTO> allOffers = this.offerService.getAllOffers();

        model.addAttribute("offers", allOffers);

        return "offers";
    }

    @GetMapping("/offers/add")
    public String getAddOfferInput() {
        return "offer-add";
    }

    @PostMapping("/offers/add")
    public String addOffer(@Valid CreateOfferDTO offerDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes)
    {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addOfferModel", offerDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferModel",
                    bindingResult);

            return "redirect:/offers/add";
        }
        this.offerService.addNewOffer(offerDTO);
        return "redirect:/offers/all";
    }
}
