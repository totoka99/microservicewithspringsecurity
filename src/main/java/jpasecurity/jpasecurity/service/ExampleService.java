package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.expcetion.ExampleNotFoundException;
import jpasecurity.jpasecurity.model.ModelMapper;
import jpasecurity.jpasecurity.model.dto.ExampleDto;
import jpasecurity.jpasecurity.model.entity.Example;
import jpasecurity.jpasecurity.model.dto.CreateExampleDto;
import jpasecurity.jpasecurity.model.dto.UpdateExampleDto;
import jpasecurity.jpasecurity.repository.ExampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExampleService {
    private final ExampleRepository exampleRepository;
    private final ModelMapper mapper;

    public ExampleDto findById(Long id) {
        return mapper.toExampleDto(exampleRepository.findById(id).orElseThrow(() -> new ExampleNotFoundException(id)));
    }

    public ExampleDto saveNewExample(CreateExampleDto createExampleDto) {
        Example example = new Example();
        example.setDescription(createExampleDto.getDescription());
        example.setName(createExampleDto.getName());
        return mapper.toExampleDto(exampleRepository.save(example));
    }

    @Transactional
    public void updateExampleName(UpdateExampleDto updateExampleDto, Long id) {
        Example example = exampleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found, id: " + id));
        example.setName(updateExampleDto.getName());
    }

    @Transactional
    public void updateExampleDescription(UpdateExampleDto updateExampleDto, Long id) {
        Example example = exampleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found, id: " + id));
        example.setDescription(updateExampleDto.getDescription());
    }
}
