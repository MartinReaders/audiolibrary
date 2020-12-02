package com.audiolibrary.web.controllerThymeleaf;

import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AcceuilController {
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String CountIndex(final ModelMap c){

        c.put("count", artistRepository.count());
        return  "accueil";
    }

}
