package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jpasecurity.jpasecurity.model.dto.CreateExampleDto;
import jpasecurity.jpasecurity.model.dto.ExampleDto;
import jpasecurity.jpasecurity.model.dto.UpdateExampleDto;
import jpasecurity.jpasecurity.model.entity.Example;
import jpasecurity.jpasecurity.service.ExampleService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/example")
@RequiredArgsConstructor
public class ExampleController {
    private final ExampleService exampleService;

    @GetMapping("/{exampleId}")
    @Operation(summary = "Return example's data")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    public ExampleDto getExampleById(@PathVariable Long exampleId) {
        return exampleService.findById(exampleId);
    }

    @PostMapping
    @Operation(summary = "Create new example")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    public ExampleDto createExample(@RequestBody CreateExampleDto createExampleDto) {
        return exampleService.saveNewExample(createExampleDto);
    }

    @PatchMapping("/name/{exampleId}")
    @Operation(summary = "Update example's name")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleName(@RequestBody UpdateExampleDto updateExampleDto, @PathVariable Long exampleId) {
        exampleService.updateExampleName(updateExampleDto, exampleId);
    }

    @PatchMapping("/description/{exampleId}")
    @Operation(summary = "Update example's description")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponse(responseCode = "202")
    public void updateExampleDescription(@RequestBody UpdateExampleDto updateExampleDto, @PathVariable Long exampleId) {
        exampleService.updateExampleDescription(updateExampleDto, exampleId);
    }
}
