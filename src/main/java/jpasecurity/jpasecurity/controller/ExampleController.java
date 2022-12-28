package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jpasecurity.jpasecurity.model.dto.CreateExampleDto;
import jpasecurity.jpasecurity.model.dto.UpdateExampleDto;
import jpasecurity.jpasecurity.model.entity.Example;
import jpasecurity.jpasecurity.service.ExampleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/example")
public class ExampleController {

  private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Return example's data")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    public Example getExampleById(@PathVariable("id") Long id) {
        return exampleService.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create new example")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    public Example createExample(@RequestBody CreateExampleDto createExampleDto) {
        return exampleService.saveNewExample(createExampleDto);
    }

    @PutMapping("/name/{id}")
    @Operation(summary = "Update example's name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleName(@RequestBody UpdateExampleDto updateExampleDto, @PathVariable("id") Long id){
        exampleService.updateExampleName(updateExampleDto, id);
    }
    @PutMapping("/description/{id}")
    @Operation(summary = "Update example's description")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleDescription(@RequestBody UpdateExampleDto updateExampleDto, @PathVariable("id") Long id){
        exampleService.updateExampleDescription(updateExampleDto, id);
    }
}
