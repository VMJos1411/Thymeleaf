package com.example.dev1.controllers;

import com.example.dev1.domain.BellyMesurement;
import com.example.dev1.domain.BloodPressureMesurement;
import com.example.dev1.services.HTMLIntEdit;
import com.example.dev1.services.HTMLTemplate;
import com.example.dev1.services.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")// Beter niks zetten want anders word css en js niet gevonden
public class HTMLController {
    @Autowired
    MeasurementService measurementService;

    // Controller thymeleaf experiments
    @GetMapping("/healthmeasurements/thymeleaf")
    public ModelAndView getThyme(ModelMap modelMap) { // ModelMap is like a Hashmap spring will automatically initialize this for you.
        modelMap.addAttribute("collist", "azerty");
        return new ModelAndView("thymeleaf", modelMap);
    }

    // Controllers for Belly measurements
    @GetMapping("/healthmeasurements/getAllGenBellyMs")
    public ModelAndView getAllGenBellyMs(ModelMap modelMap) { // ModelMap is like a Hashmap spring will automatically initialize this for you.
        modelMap.addAttribute("collist", measurementService.fillIntDisplayWBelly(measurementService.getAllBellyMs()));
        return new ModelAndView("measurementDisplay", modelMap);
    }

    @GetMapping("/healthmeasurements/addGenBellyM")
    public ModelAndView addGenBellyM(ModelMap modelMap) {
        modelMap.addAttribute("GenMap", measurementService.fillIntEditWBelly(new BellyMesurement(), "add"));
        return new ModelAndView("measurementEdit", modelMap);
    }

    @GetMapping("/healthmeasurements/{id}/editGenBellyM") // <---- Creates url in the form of localhost:port/healthmesurements/{id}/edit
    public ModelAndView editGenBellyM(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("GenMap", measurementService.fillIntEditWBelly(measurementService.findById(id), "edit"));
        return new ModelAndView("measurementEdit", modelMap);
    }

    @GetMapping("/healthmeasurements/{id}/deleteGenBellyM")
    public ModelAndView deleteGenBellyM(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        measurementService.deleteById(id);
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBellyMs");
    }

    @PostMapping("/healthmeasurements/editGenBellyM")
    public ModelAndView editPostGenBellyM(@ModelAttribute HTMLIntEdit template) {
        measurementService.update(measurementService.moveIntEditInBelly(template));
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBellyMs");
    }

    @PostMapping("/healthmeasurements/addGenBellyM")
    public ModelAndView addPostGenBellyM(@ModelAttribute HTMLIntEdit template) {
        measurementService.addBellyM(measurementService.moveIntEditInBelly(template));
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBellyMs");
    }

    // ================================================================================================================
    // Controllers for Blood pressure measurements ====================================================================
    // ================================================================================================================

    @GetMapping("/healthmeasurements/getAllGenBPresMs")
    public ModelAndView getAllGenBPresMs(ModelMap modelMap) { // ModelMap is like a Hashmap spring will automatically initialize this for you.
        modelMap.addAttribute("collist", measurementService.fillIntDisplayWBPres(measurementService.getAllBPresMs()));
        return new ModelAndView("measurementDisplay", modelMap);
    }

    @GetMapping("/healthmeasurements/addGenBPresM")
    public ModelAndView addGenBPresM(ModelMap modelMap) {
        modelMap.addAttribute("GenMap", measurementService.fillIntEditWBPres(new BloodPressureMesurement(), "add"));
        return new ModelAndView("measurementEdit", modelMap);
    }

    @PostMapping("/healthmeasurements/addGenBPresM")
    public ModelAndView addPostGenBPresM(@ModelAttribute HTMLIntEdit template) {
        measurementService.addBPresM(measurementService.moveIntEditInBPres(template));
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBPresMs");
    }

    @GetMapping("/healthmeasurements/{id}/editGenBPresM") // <---- Creates url in the form of localhost:port/healthmesurements/{id}/edit
    public ModelAndView editGenBPresM(@PathVariable("id") int id, ModelMap modelMap) {
        modelMap.addAttribute("GenMap", measurementService.fillIntEditWBPres(measurementService.findBPresById(id), "edit"));
        return new ModelAndView("measurementEdit", modelMap);
    }

    @GetMapping("/healthmeasurements/{id}/deleteGenBPresM")
    public ModelAndView deleteGenBPresM(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        measurementService.deleteBPresById(id);
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBPresMs");
    }

    @PostMapping("/healthmeasurements/editGenBPresM")
    public ModelAndView editPostGenBPresM(@ModelAttribute HTMLIntEdit template) {
        measurementService.updateBPres(measurementService.moveIntEditInBPres(template));
        return new ModelAndView("redirect:/healthmeasurements/getAllGenBPresMs");
    }
}
