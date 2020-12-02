package com.audiolibrary.web.controllerThymeleaf;


import com.audiolibrary.web.model.Artist;
import com.audiolibrary.web.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping("/artists")
public class ArtisteController {
    @Autowired
    private ArtistRepository artistRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public String getArtistById(@PathVariable Long id, final ModelMap model){

        Optional<Artist> artistOptional = artistRepository.findById(id);
        if(artistOptional.isEmpty()){
            throw new EntityNotFoundException("Artist d'identifiant " + id + " n'a pas été trouvé !");
        }

        model.put("artiste", artistOptional.get());
        //model.put("nombreEmployes", employeRepository.count());

        return "detailArtist";
    }


}
