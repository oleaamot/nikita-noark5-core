package no.arkivlab.hioa.nikita.webapp.web.controller.api.impl;

import io.swagger.annotations.Api;
import nikita.config.N5ResourceMappings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = N5ResourceMappings.FONDS)
@Api(value = "FondsCreator", description = "CRUD operations on fondsCreato")
public class FondsCreatorController {


    // It should not be possible to set name or ID to whitespaces
    // Throw a whitespace only Exception
    // Only admin can change after status is finalised. How do we pick up this?
}
