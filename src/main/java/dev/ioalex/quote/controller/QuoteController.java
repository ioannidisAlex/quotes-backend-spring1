package dev.ioalex.quote.controller;


import dev.ioalex.quote.dto.PaginationDTO;
import dev.ioalex.quote.dto.QuoteDTO;
import dev.ioalex.quote.exception.QuoteServiceException;
import dev.ioalex.quote.service.QuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService service;

    public QuoteController(QuoteService service) {
        this.service = service;
    }

    // GET http://localhost:8080/quotes?page=2&pageSize=10
    @GetMapping
    public ResponseEntity<List<QuoteDTO>> findAll(@RequestParam int page, @RequestParam int pageSize) {
        try {
            List<QuoteDTO> quotes = service.findAll(new PaginationDTO(page, pageSize));
            return ResponseEntity.ok(quotes);
        } catch (QuoteServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    //GET http://localhost:8080/quotes/search?text=`eat,`&page=0&pageSize=10
    @GetMapping(value = "/search", params = {"page", "pageSize"})
    public List<QuoteDTO> findAllByTextLike(@RequestParam("text") String text, @RequestParam int page, @RequestParam int pageSize) {
        return service.findAllByTextLike(text, new PaginationDTO(page, pageSize));
    }

    // GET http://localhost:8080/quotes/random
    @GetMapping("/random")
    public QuoteDTO getRandomQuote() {
        return service.getRandomQuote();
    }

    // GET http://localhost:8080/quotes/1111
    @GetMapping("/{id}")
    public QuoteDTO findById(@PathVariable long id) {
        return service.findById(id);
    }

    // POST http://localhost:8080/quotes
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public QuoteDTO create(@RequestBody QuoteDTO quoteDTO) {
        return service.create(quoteDTO);
    }

    // PUT http://localhost:8080/quotes/3
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public QuoteDTO update(@PathVariable long id, @RequestBody QuoteDTO quoteDTO) {
        return service.update(id, quoteDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
