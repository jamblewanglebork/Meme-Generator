package com.example.memeapp.controller;

import com.example.memeapp.service.MemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@Controller
public class MemeController {

    @Autowired
    private MemeService memeService;

    @GetMapping("/")
    public String getMemes(Model model) {
        model.addAttribute("memes", memeService.fetchMemes());
        return "index";
    }

    @GetMapping("/edit/{memeId}")
    public String editMeme(@PathVariable String memeId, Model model) {
        model.addAttribute("memeId", memeId);
        return "edit";
    }

    @PostMapping("/generate")
    public String generateMeme(@RequestParam String template_id, @RequestParam String topText,
                               @RequestParam String bottomText, Model model) {
        String memeUrl = memeService.generateMeme(template_id, topText, bottomText);
        model.addAttribute("memeUrl", memeUrl);
        return "edit";
    }
}
